package wannabit.io.cosmostaion.ui.tx.step

import android.app.Activity
import android.content.Intent
import android.os.Build
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
import com.cosmos.base.abci.v1beta1.AbciProto
import com.cosmos.base.v1beta1.CoinProto
import com.cosmos.staking.v1beta1.TxProto.MsgCancelUnbondingDelegation
import com.cosmos.tx.v1beta1.TxProto
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import wannabit.io.cosmostaion.R
import wannabit.io.cosmostaion.chain.BaseChain
import wannabit.io.cosmostaion.common.BaseData
import wannabit.io.cosmostaion.common.amountHandlerLeft
import wannabit.io.cosmostaion.common.dpToPx
import wannabit.io.cosmostaion.common.formatAmount
import wannabit.io.cosmostaion.common.formatAssetValue
import wannabit.io.cosmostaion.common.getChannel
import wannabit.io.cosmostaion.common.getdAmount
import wannabit.io.cosmostaion.common.makeToast
import wannabit.io.cosmostaion.common.setTokenImg
import wannabit.io.cosmostaion.common.showToast
import wannabit.io.cosmostaion.common.updateButtonView
import wannabit.io.cosmostaion.cosmos.Signer
import wannabit.io.cosmostaion.data.model.res.FeeInfo
import wannabit.io.cosmostaion.databinding.FragmentCancelUnBondingBinding
import wannabit.io.cosmostaion.databinding.ItemSegmentedFeeBinding
import wannabit.io.cosmostaion.ui.option.tx.general.AssetFragment
import wannabit.io.cosmostaion.ui.option.tx.general.AssetSelectListener
import wannabit.io.cosmostaion.ui.option.tx.general.BaseFeeAssetFragment
import wannabit.io.cosmostaion.ui.option.tx.general.BaseFeeAssetSelectListener
import wannabit.io.cosmostaion.ui.option.tx.general.MemoFragment
import wannabit.io.cosmostaion.ui.option.tx.general.MemoListener
import wannabit.io.cosmostaion.ui.password.PasswordCheckActivity
import wannabit.io.cosmostaion.ui.tx.TxResultActivity
import wannabit.io.cosmostaion.ui.tx.info.UnBondingEntry
import java.math.BigDecimal
import java.math.RoundingMode

class CancelUnBondingFragment : BaseTxFragment() {

    private var _binding: FragmentCancelUnBondingBinding? = null
    private val binding get() = _binding!!

    private lateinit var selectedChain: BaseChain
    private lateinit var unBondingEntry: UnBondingEntry

    private var feeInfos: MutableList<FeeInfo> = mutableListOf()
    private var selectedFeeInfo = 0
    private var txFee: TxProto.Fee? = null
    private var txTip: TxProto.Tip? = null
    private var txMemo = ""

    private var isClickable = true

    companion object {
        @JvmStatic
        fun newInstance(
            selectedChain: BaseChain, unBondingEntry: UnBondingEntry?
        ): CancelUnBondingFragment {
            val args = Bundle().apply {
                putParcelable("selectedChain", selectedChain)
                putParcelable("unBondingEntry", unBondingEntry)
            }
            val fragment = CancelUnBondingFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCancelUnBondingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initFee()
        txSimulate()
        setUpClickAction()
        setUpSimulate()
        setUpBroadcast()
    }

    private fun initView() {
        binding.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.apply {
                    getParcelable("selectedChain", BaseChain::class.java)?.let {
                        selectedChain = it
                    }
                    getParcelable("unBondingEntry", UnBondingEntry::class.java)?.let {
                        unBondingEntry = it
                    }
                }

            } else {
                arguments?.apply {
                    (getParcelable("selectedChain") as? BaseChain)?.let {
                        selectedChain = it
                    }
                    (getParcelable("unBondingEntry") as? UnBondingEntry)?.let {
                        unBondingEntry = it
                    }
                }
            }

            listOf(
                cancelUnstakingView, memoView, feeView
            ).forEach { it.setBackgroundResource(R.drawable.cell_bg) }
            segmentView.setBackgroundResource(R.drawable.segment_fee_bg)

            selectedChain.cosmosFetcher?.cosmosValidators?.firstOrNull { it.operatorAddress == unBondingEntry.validatorAddress }
                ?.let { validator ->
                    validatorName.text = validator.description.moniker
                }

            BaseData.getAsset(selectedChain.apiName, selectedChain.stakeDenom)?.let { asset ->
                val unBondingAmount = unBondingEntry.entry?.balance?.toBigDecimal()
                    ?.movePointLeft(asset.decimals ?: 6) ?: BigDecimal.ZERO
                cancelAmount.text =
                    formatAmount(unBondingAmount.toPlainString(), asset.decimals ?: 6)
                cancelDenom.text = asset.symbol
                cancelDenom.setTextColor(asset.assetColor())
            }
        }
    }

    private fun initFee() {
        binding.apply {
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

            if (selectedChain.cosmosFetcher?.cosmosBaseFees?.isNotEmpty() == true) {
                val tipTitle = listOf(
                    "No Tip", "20% Tip", "50% Tip", "100% Tip"
                )
                for (i in tipTitle.indices) {
                    val segmentView = ItemSegmentedFeeBinding.inflate(layoutInflater)
                    feeSegment.addView(
                        segmentView.root,
                        i,
                        LinearLayout.LayoutParams(0, dpToPx(requireContext(), 32), 1f)
                    )
                    segmentView.btnTitle.text = tipTitle[i]
                }
                feeSegment.setPosition(selectedFeeInfo, false)
                val baseFee = selectedChain.cosmosFetcher?.cosmosBaseFees?.get(0)
                val gasAmount = selectedChain.getFeeBaseGasAmount().toBigDecimal()
                val feeDenom = baseFee?.denom
                val feeAmount =
                    baseFee?.getdAmount()?.multiply(gasAmount)?.setScale(0, RoundingMode.DOWN)
                txFee = TxProto.Fee.newBuilder().setGasLimit(gasAmount.toLong()).addAmount(
                    CoinProto.Coin.newBuilder().setDenom(feeDenom).setAmount(feeAmount.toString())
                        .build()
                ).build()

            } else {
                feeInfos = selectedChain.getFeeInfos(requireContext())
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
        updateFeeView()
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
                    feeValue.text = formatAssetValue(value)
                }
            }
        }
    }

    private fun setUpClickAction() {
        binding.apply {
            memoView.setOnClickListener {
                handleOneClickWithDelay(
                    MemoFragment.newInstance(txMemo, object : MemoListener {
                        override fun memo(memo: String) {
                            updateMemoView(memo)
                        }
                    })
                )
            }

            feeTokenLayout.setOnClickListener {
                txFee?.let { fee ->
                    if (selectedChain.cosmosFetcher?.cosmosBaseFees?.isNotEmpty() == true) {
                        handleOneClickWithDelay(
                            BaseFeeAssetFragment(selectedChain,
                                selectedChain.cosmosFetcher?.cosmosBaseFees,
                                object : BaseFeeAssetSelectListener {
                                    override fun select(denom: String) {
                                        selectedChain.cosmosFetcher?.cosmosBaseFees?.firstOrNull { it.denom == denom }
                                            ?.let { baseFee ->
                                                val feeAmount = baseFee.getdAmount()
                                                    .multiply(fee.gasLimit.toBigDecimal())
                                                    ?.setScale(0, RoundingMode.DOWN)
                                                val updateFeeCoin =
                                                    CoinProto.Coin.newBuilder().setDenom(denom)
                                                        .setAmount(feeAmount.toString()).build()
                                                txFee = TxProto.Fee.newBuilder()
                                                    .setGasLimit(fee.gasLimit)
                                                    .addAmount(updateFeeCoin).build()
                                                txFee = Signer.setFee(selectedFeeInfo, txFee)

                                                updateFeeView()
                                                txSimulate()
                                            }
                                    }
                                })
                        )

                    } else {
                        handleOneClickWithDelay(
                            AssetFragment.newInstance(selectedChain,
                                feeInfos[selectedFeeInfo].feeDatas.toMutableList(),
                                object : AssetSelectListener {
                                    override fun select(denom: String) {
                                        feeInfos[selectedFeeInfo].feeDatas.firstOrNull { it.denom == denom }
                                            ?.let { feeCoin ->
                                                val gasAmount = selectedChain.getFeeBaseGasAmount()
                                                    .toBigDecimal()
                                                val updateFeeCoin =
                                                    CoinProto.Coin.newBuilder().setDenom(denom)
                                                        .setAmount(
                                                            feeCoin.gasRate?.multiply(
                                                                gasAmount
                                                            )?.setScale(0, RoundingMode.UP)
                                                                .toString()
                                                        ).build()

                                                txFee = TxProto.Fee.newBuilder().setGasLimit(
                                                    selectedChain.getFeeBaseGasAmount()
                                                ).addAmount(updateFeeCoin).build()

                                                updateFeeView()
                                                txSimulate()
                                            }
                                    }
                                })
                        )
                    }
                }
            }

            feeSegment.setOnPositionChangedListener { position ->
                selectedFeeInfo = position
                txFee = if (selectedChain.cosmosFetcher?.cosmosBaseFees?.isNotEmpty() == true) {
                    val baseFee = selectedChain.cosmosFetcher?.cosmosBaseFees?.firstOrNull {
                        it.denom == txFee?.getAmount(0)?.denom
                    }
                    val gasAmount = selectedChain.getFeeBaseGasAmount().toBigDecimal()
                    val feeDenom = baseFee?.denom
                    val feeAmount =
                        baseFee?.getdAmount()?.multiply(gasAmount)?.setScale(0, RoundingMode.DOWN)
                    txFee = TxProto.Fee.newBuilder().setGasLimit(gasAmount.toLong()).addAmount(
                        CoinProto.Coin.newBuilder().setDenom(feeDenom)
                            .setAmount(feeAmount.toString()).build()
                    ).build()
                    Signer.setFee(selectedFeeInfo, txFee)

                } else {
                    selectedChain.getBaseFee(
                        requireContext(), selectedFeeInfo, txFee?.getAmount(0)?.denom
                    )
                }
                updateFeeView()
                txSimulate()
            }

            btnCancelUnstake.setOnClickListener {
                Intent(requireContext(), PasswordCheckActivity::class.java).apply {
                    cancelUnBondingResultLauncher.launch(this)
                    requireActivity().overridePendingTransition(
                        R.anim.anim_slide_in_bottom, R.anim.anim_fade_out
                    )
                }
            }
        }
    }

    private fun handleOneClickWithDelay(bottomSheetDialogFragment: BottomSheetDialogFragment) {
        if (isClickable) {
            isClickable = false

            bottomSheetDialogFragment.show(
                requireActivity().supportFragmentManager, bottomSheetDialogFragment::class.java.name
            )

            Handler(Looper.getMainLooper()).postDelayed({
                isClickable = true
            }, 300)
        }
    }

    private val cancelUnBondingResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && isAdded) {
                binding.backdropLayout.visibility = View.VISIBLE
                txViewModel.broadCancelUnBonding(
                    getChannel(selectedChain),
                    selectedChain.address,
                    onBindCancelUnBonding(),
                    txFee,
                    txTip,
                    txMemo,
                    selectedChain
                )
            }
        }

    private fun txSimulate() {
        binding.apply {
            if (!selectedChain.isGasSimulable()) {
                return updateFeeViewWithSimulate(null)
            }
            btnCancelUnstake.updateButtonView(false)
            backdropLayout.visibility = View.VISIBLE
            txViewModel.simulateCancelUnBonding(
                getChannel(selectedChain),
                selectedChain.address,
                onBindCancelUnBonding(),
                txFee,
                txTip,
                txMemo,
                selectedChain
            )
        }
    }

    private fun setUpSimulate() {
        txViewModel.simulate.observe(viewLifecycleOwner) { gasInfo ->
            // updateFeeViewWithSimulate(gasInfo)
        }

        txViewModel.errorMessage.observe(viewLifecycleOwner) { response ->
            isBroadCastTx(false)
            if (response.contains("unable to resolve type")) {
                requireContext().makeToast(R.string.error_not_support_cancel_unbonding)
            } else {
                requireContext().showToast(view, response, true)
            }
            return@observe
        }
    }

    private fun updateFeeViewWithSimulate(gasInfo: AbciProto.GasInfo?) {
        txFee?.let { fee ->
            gasInfo?.let { info ->
                val gasLimit =
                    (info.gasUsed.toDouble() * selectedChain.gasMultiply()).toLong().toBigDecimal()
                if (selectedChain.cosmosFetcher?.cosmosBaseFees?.isNotEmpty() == true) {
                    selectedChain.cosmosFetcher?.cosmosBaseFees?.firstOrNull {
                        it.denom == fee.getAmount(
                            0
                        ).denom
                    }?.let { baseFee ->
                        val feeCoinAmount =
                            baseFee.getdAmount().multiply(gasLimit).setScale(0, RoundingMode.UP)
                        val feeCoin = CoinProto.Coin.newBuilder().setDenom(fee.getAmount(0).denom)
                            .setAmount(feeCoinAmount.toString()).build()
                        txFee = TxProto.Fee.newBuilder().setGasLimit(gasLimit.toLong())
                            .addAmount(feeCoin).build()
                        txFee = Signer.setFee(selectedFeeInfo, txFee)
                    }

                } else {
                    val selectedFeeData =
                        feeInfos[selectedFeeInfo].feeDatas.firstOrNull { it.denom == fee.getAmount(0).denom }
                    val gasRate = selectedFeeData?.gasRate

                    val feeCoinAmount = gasRate?.multiply(gasLimit)?.setScale(0, RoundingMode.UP)
                    val feeCoin = CoinProto.Coin.newBuilder().setDenom(fee.getAmount(0).denom)
                        .setAmount(feeCoinAmount.toString()).build()
                    txFee =
                        TxProto.Fee.newBuilder().setGasLimit(gasLimit.toLong()).addAmount(feeCoin)
                            .build()
                }
            }
        }
        updateFeeView()
        isBroadCastTx(true)
    }

    private fun isBroadCastTx(isSuccess: Boolean) {
        binding.backdropLayout.visibility = View.GONE
        binding.btnCancelUnstake.updateButtonView(isSuccess)
    }

    private fun onBindCancelUnBonding(): MsgCancelUnbondingDelegation? {
        val toCoin = CoinProto.Coin.newBuilder().setDenom(selectedChain.stakeDenom)
            .setAmount(unBondingEntry.entry?.balance).build()
        return MsgCancelUnbondingDelegation.newBuilder().setDelegatorAddress(selectedChain.address)
            .setValidatorAddress(unBondingEntry.validatorAddress)
            .setCreationHeight(unBondingEntry.entry!!.creationHeight).setAmount(toCoin).build()
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
            dismiss()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}