package wannabit.io.cosmostaion.ui.tx.step

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.cosmos.base.abci.v1beta1.AbciProto
import com.cosmos.base.v1beta1.CoinProto
import com.cosmos.gov.v1beta1.GovProto
import com.cosmos.gov.v1beta1.TxProto.MsgVote
import com.cosmos.tx.v1beta1.TxProto
import wannabit.io.cosmostaion.R
import wannabit.io.cosmostaion.chain.CosmosLine
import wannabit.io.cosmostaion.common.BaseConstant
import wannabit.io.cosmostaion.common.BaseData
import wannabit.io.cosmostaion.common.amountHandlerLeft
import wannabit.io.cosmostaion.common.dpToPx
import wannabit.io.cosmostaion.common.formatAmount
import wannabit.io.cosmostaion.common.formatAssetValue
import wannabit.io.cosmostaion.common.getChannel
import wannabit.io.cosmostaion.common.setTokenImg
import wannabit.io.cosmostaion.common.showToast
import wannabit.io.cosmostaion.common.updateButtonView
import wannabit.io.cosmostaion.data.model.res.CosmosProposal
import wannabit.io.cosmostaion.data.model.res.FeeInfo
import wannabit.io.cosmostaion.databinding.FragmentVoteBinding
import wannabit.io.cosmostaion.databinding.ItemSegmentedFeeBinding
import wannabit.io.cosmostaion.ui.dialog.tx.AssetFragment
import wannabit.io.cosmostaion.ui.dialog.tx.AssetSelectListener
import wannabit.io.cosmostaion.ui.dialog.tx.MemoFragment
import wannabit.io.cosmostaion.ui.dialog.tx.MemoListener
import wannabit.io.cosmostaion.ui.password.PasswordCheckActivity
import wannabit.io.cosmostaion.ui.tx.TxResultActivity
import java.math.RoundingMode

class VoteFragment(
    val selectedChain: CosmosLine, val proposals: MutableList<CosmosProposal>?
) : BaseTxFragment() {

    private var _binding: FragmentVoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var voteAdapter: VoteAdapter

    private var feeInfos: MutableList<FeeInfo> = mutableListOf()
    private var selectedFeeInfo = 0
    private var txFee: TxProto.Fee? = null

    private var txMemo = ""

    private var toVotes: MutableList<MsgVote?>? = mutableListOf()

    private var isClickable = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVoteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initFee()
        updateFeeView()
        setUpClickAction()
        setUpSimulate()
        setUpBroadcast()
    }

    private fun initView() {
        binding.apply {
            listOf(
                memoView, feeView
            ).forEach { it.setBackgroundResource(R.drawable.cell_bg) }

            voteAdapter = VoteAdapter(listener = selectOption)
            recycler.setHasFixedSize(true)
            recycler.layoutManager = LinearLayoutManager(requireContext())
            recycler.adapter = voteAdapter
            voteAdapter.submitList(proposals)
        }
    }

    private val selectOption = object : VoteAdapter.ClickListener {
        override fun selectOption(position: Int, tag: Int) {
            when (tag) {
                0 -> {
                    proposals?.get(position)?.toVoteOption = GovProto.VoteOption.VOTE_OPTION_YES
                }

                1 -> {
                    proposals?.get(position)?.toVoteOption = GovProto.VoteOption.VOTE_OPTION_NO
                }

                2 -> {
                    proposals?.get(position)?.toVoteOption =
                        GovProto.VoteOption.VOTE_OPTION_NO_WITH_VETO
                }

                3 -> {
                    proposals?.get(position)?.toVoteOption = GovProto.VoteOption.VOTE_OPTION_ABSTAIN
                }

                else -> {
                    proposals?.get(position)?.toVoteOption = null
                }
            }
            voteAdapter.notifyDataSetChanged()
            txSimulate()
        }
    }

    private fun initFee() {
        binding.apply {
            feeInfos = selectedChain.getFeeInfos(requireContext())
            feeSegment.setSelectedBackground(
                ContextCompat.getColor(
                    requireContext(), R.color.color_accent_purple
                )
            )
            feeSegment.setRipple(
                ContextCompat.getColor(
                    requireContext(), R.color.color_accent_purple
                )
            )

            for (i in feeInfos.indices) {
                val segmentView = ItemSegmentedFeeBinding.inflate(layoutInflater)
                feeSegment.addView(
                    segmentView.root,
                    i,
                    LinearLayout.LayoutParams(0, dpToPx(requireContext(), 32), 1f)
                )
                segmentView.btnTitle.text = feeInfos[i].title
            }
            feeSegment.setPosition(selectedChain.getFeeBasePosition(), false)
            selectedFeeInfo = selectedChain.getFeeBasePosition()
            txFee = selectedChain.getInitFee(requireContext())
        }
    }

    private fun updateMemoView(memo: String) {
        binding.apply {
            txMemo = memo
            if (txMemo.isEmpty()) {
                tabMemoMsg.text = getString(R.string.str_tap_for_add_memo_msg)
                tabMemoMsg.setTextColor(
                    ContextCompat.getColorStateList(
                        requireContext(), R.color.color_base03
                    )
                )
            } else {
                tabMemoMsg.text = txMemo
                tabMemoMsg.setTextColor(
                    ContextCompat.getColorStateList(
                        requireContext(), R.color.color_base01
                    )
                )
            }
        }
        txSimulate()
    }

    private fun updateFeeView() {
        binding.apply {
            txFee?.getAmount(0)?.let { fee ->
                BaseData.getAsset(selectedChain.apiName, fee.denom)?.let { asset ->
                    feeTokenImg.setTokenImg(asset)
                    feeToken.text = asset.symbol

                    val amount = fee.amount.toBigDecimal().amountHandlerLeft(asset.decimals ?: 6)
                    val price = BaseData.getPrice(asset.coinGeckoId)
                    val value = price.multiply(amount)

                    feeAmount.text = formatAmount(amount.toPlainString(), asset.decimals ?: 6)
                    feeDenom.text = asset.symbol
                    feeValue.text = formatAssetValue(value)
                }
            }
        }
    }

    private fun setUpClickAction() {
        binding.apply {
            memoView.setOnClickListener {
                MemoFragment(txMemo, object : MemoListener {
                    override fun memo(memo: String) {
                        updateMemoView(memo)
                    }

                }).show(
                    requireActivity().supportFragmentManager, MemoFragment::class.java.name
                )
                setClickableOnce(isClickable)
            }

            feeTokenLayout.setOnClickListener {
                AssetFragment(selectedChain,
                    feeInfos[selectedFeeInfo].feeDatas,
                    object : AssetSelectListener {
                        override fun select(denom: String) {
                            selectedChain.getDefaultFeeCoins(requireContext())
                                .firstOrNull { it.denom == denom }?.let { feeCoin ->
                                    val updateFeeCoin = CoinProto.Coin.newBuilder().setDenom(denom)
                                        .setAmount(feeCoin.amount).build()

                                    val updateTxFee = TxProto.Fee.newBuilder()
                                        .setGasLimit(BaseConstant.BASE_GAS_AMOUNT.toLong())
                                        .addAmount(updateFeeCoin).build()

                                    txFee = updateTxFee
                                    updateFeeView()
                                    txSimulate()
                                }
                        }

                    }).show(
                    requireActivity().supportFragmentManager, AssetFragment::class.java.name
                )
                setClickableOnce(isClickable)
            }

            feeSegment.setOnPositionChangedListener { position ->
                selectedFeeInfo = position
                txFee = selectedChain.getBaseFee(
                    requireContext(), selectedFeeInfo, txFee?.getAmount(0)?.denom
                )
                updateFeeView()
                txSimulate()
            }

            btnVote.setOnClickListener {
                Intent(requireContext(), PasswordCheckActivity::class.java).apply {
                    voteResultLauncher.launch(this)
                    requireActivity().overridePendingTransition(
                        R.anim.anim_slide_in_bottom, R.anim.anim_fade_out
                    )
                }
            }
        }
    }

    private fun setClickableOnce(clickable: Boolean) {
        if (clickable) {
            isClickable = false

            Handler(Looper.getMainLooper()).postDelayed({
                isClickable = true
            }, 1000)
        }
    }

    private val voteResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && isAdded) {
                binding.backdropLayout.visibility = View.VISIBLE
                txViewModel.broadVote(
                    getChannel(selectedChain),
                    selectedChain.address,
                    toVotes,
                    txFee,
                    txMemo,
                    selectedChain
                )
            }
        }

    private fun txSimulate() {
        if (proposals?.any { it.toVoteOption == null } == true) {
            return
        }
        binding.apply {
            btnVote.updateButtonView(false)
            backdropLayout.visibility = View.VISIBLE

            toVotes?.clear()
            proposals?.forEach { proposal ->
                val voteMsg = proposal.id?.let { id ->
                    MsgVote.newBuilder().setVoter(selectedChain.address).setProposalId(id.toLong())
                        .setOption(proposal.toVoteOption).build()
                }
                toVotes?.add(voteMsg)
            }
            txViewModel.simulateVote(
                getChannel(selectedChain), selectedChain.address, toVotes, txFee, txMemo
            )
        }
    }

    private fun setUpSimulate() {
        txViewModel.simulate.observe(viewLifecycleOwner) { gasInfo ->
            isBroadCastTx(true)
            updateFeeViewWithSimulate(gasInfo)
        }

        txViewModel.errorMessage.observe(viewLifecycleOwner) { response ->
            isBroadCastTx(false)
            requireContext().showToast(view, response, true)
            return@observe
        }
    }

    private fun updateFeeViewWithSimulate(gasInfo: AbciProto.GasInfo) {
        txFee?.let { fee ->
            feeInfos[selectedFeeInfo].feeDatas.firstOrNull { it.denom == fee.getAmount(0).denom }
                ?.let { gasRate ->
                    val gasLimit =
                        (gasInfo.gasUsed.toDouble() * selectedChain.gasMultiply()).toLong()
                            .toBigDecimal()
                    val feeCoinAmount =
                        gasRate.gasRate?.multiply(gasLimit)?.setScale(0, RoundingMode.UP)
                    val feeCoin = CoinProto.Coin.newBuilder().setDenom(fee.getAmount(0).denom)
                        .setAmount(feeCoinAmount.toString()).build()
                    txFee =
                        TxProto.Fee.newBuilder().setGasLimit(gasLimit.toLong()).addAmount(feeCoin)
                            .build()
                }
        }
        updateFeeView()
    }

    private fun isBroadCastTx(isSuccess: Boolean) {
        binding.backdropLayout.visibility = View.GONE
        binding.btnVote.updateButtonView(isSuccess)
    }

    private fun setUpBroadcast() {
        txViewModel.broadcastTx.observe(viewLifecycleOwner) { txResponse ->
            Intent(requireContext(), TxResultActivity::class.java).apply {
                if (txResponse.code > 0) {
                    putExtra("isSuccess", false)
                } else {
                    putExtra("isSuccess", true)
                }
                putExtra("errorMsg", txResponse.rawLog)
                putExtra("selectedChain", selectedChain.tag)
                val hash = txResponse.txhash
                if (!TextUtils.isEmpty(hash)) putExtra("txHash", hash)
                startActivity(this)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}