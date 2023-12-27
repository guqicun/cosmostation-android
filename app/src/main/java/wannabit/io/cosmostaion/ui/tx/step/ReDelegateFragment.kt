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
import com.cosmos.base.abci.v1beta1.AbciProto
import com.cosmos.base.v1beta1.CoinProto
import com.cosmos.staking.v1beta1.StakingProto
import com.cosmos.staking.v1beta1.TxProto.MsgBeginRedelegate
import com.cosmos.tx.v1beta1.TxProto
import wannabit.io.cosmostaion.R
import wannabit.io.cosmostaion.chain.CosmosLine
import wannabit.io.cosmostaion.common.BaseConstant
import wannabit.io.cosmostaion.common.BaseData
import wannabit.io.cosmostaion.common.amountHandlerLeft
import wannabit.io.cosmostaion.common.dpToPx
import wannabit.io.cosmostaion.common.formatAmount
import wannabit.io.cosmostaion.common.formatAssetValue
import wannabit.io.cosmostaion.common.formatString
import wannabit.io.cosmostaion.common.getChannel
import wannabit.io.cosmostaion.common.setMonikerImg
import wannabit.io.cosmostaion.common.setTokenImg
import wannabit.io.cosmostaion.common.showToast
import wannabit.io.cosmostaion.common.updateButtonView
import wannabit.io.cosmostaion.common.visibleOrGone
import wannabit.io.cosmostaion.data.model.res.FeeInfo
import wannabit.io.cosmostaion.databinding.FragmentRedelegateBinding
import wannabit.io.cosmostaion.databinding.ItemSegmentedFeeBinding
import wannabit.io.cosmostaion.ui.dialog.tx.AmountSelectListener
import wannabit.io.cosmostaion.ui.dialog.tx.AssetFragment
import wannabit.io.cosmostaion.ui.dialog.tx.AssetSelectListener
import wannabit.io.cosmostaion.ui.dialog.tx.InsertAmountFragment
import wannabit.io.cosmostaion.ui.dialog.tx.MemoFragment
import wannabit.io.cosmostaion.ui.dialog.tx.MemoListener
import wannabit.io.cosmostaion.ui.dialog.tx.validator.ValidatorDefaultFragment
import wannabit.io.cosmostaion.ui.dialog.tx.validator.ValidatorDefaultListener
import wannabit.io.cosmostaion.ui.dialog.tx.validator.ValidatorFragment
import wannabit.io.cosmostaion.ui.dialog.tx.validator.ValidatorListener
import wannabit.io.cosmostaion.ui.main.chain.TxType
import wannabit.io.cosmostaion.ui.password.PasswordCheckActivity
import wannabit.io.cosmostaion.ui.tx.TxResultActivity
import java.math.BigDecimal
import java.math.RoundingMode

class ReDelegateFragment(
    val selectedChain: CosmosLine, private var fromValidator: StakingProto.Validator?
) : BaseTxFragment() {

    private var _binding: FragmentRedelegateBinding? = null
    private val binding get() = _binding!!

    private var toValidator: StakingProto.Validator? = null

    private var feeInfos: MutableList<FeeInfo> = mutableListOf()
    private var selectedFeeInfo = 0
    private var txFee: TxProto.Fee? = null

    private var toCoin: CoinProto.Coin? = null
    private var txMemo = ""

    private var availableAmount = BigDecimal.ZERO

    private var isClickable = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRedelegateBinding.inflate(layoutInflater, container, false)
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
            listOf(fromValidatorView, toValidatorView, amountView, memoView, feeView).forEach {
                it.setBackgroundResource(
                    R.drawable.cell_bg
                )
            }

            if (fromValidator != null) {
                selectedChain.cosmosValidators.firstOrNull { it.operatorAddress == selectedChain.cosmosDelegations[0].delegation.validatorAddress }
            }

            val cosmostation =
                selectedChain.cosmosValidators.firstOrNull { it.description.moniker == "Cosmostation" }
            toValidator = if (fromValidator?.operatorAddress == cosmostation?.operatorAddress) {
                selectedChain.cosmosValidators.firstOrNull { it.operatorAddress != cosmostation?.operatorAddress }
            } else {
                selectedChain.cosmosValidators.firstOrNull { it.operatorAddress != fromValidator?.operatorAddress }
            }
            updateFromValidatorView()
            updateToValidatorView()
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

    private fun updateFromValidatorView() {
        binding.apply {
            fromValidator?.let { fromValidator ->
                fromMonikerImg.setMonikerImg(selectedChain, fromValidator.operatorAddress)
                fromMonikerName.text = fromValidator.description?.moniker
                fromJailedImg.visibleOrGone(fromValidator.jailed)
            }
            selectedChain.stakeDenom?.let { denom ->
                BaseData.getAsset(selectedChain.apiName, denom)?.let { asset ->
                    asset.decimals?.let { decimal ->
                        val staked =
                            selectedChain.cosmosDelegations.firstOrNull { it.delegation.validatorAddress == fromValidator?.operatorAddress }?.balance?.amount
                        staked?.toBigDecimal()?.movePointLeft(decimal)?.let {
                            stakedAmount.text = formatAmount(it.toPlainString(), decimal)
                        }
                    }
                }
            }
        }
        txSimulate()
    }

    private fun updateToValidatorView() {
        binding.apply {
            toValidator?.let { toValidator ->
                toMonikerImg.setMonikerImg(selectedChain, toValidator.operatorAddress)
                toMonikerName.text = toValidator.description?.moniker
                toJailedImg.visibleOrGone(toValidator.jailed)

                toValidator.commission.commissionRates.rate.toBigDecimal().movePointLeft(16)
                    .setScale(2, RoundingMode.DOWN).let {
                        commission.text = formatString("$it%", 3)
                        txSimulate()
                    }
            }
        }
    }

    private fun updateAmountView(toAmount: String) {
        binding.apply {
            toCoin =
                CoinProto.Coin.newBuilder().setAmount(toAmount).setDenom(selectedChain.stakeDenom)
                    .build()

            selectedChain.stakeDenom?.let { denom ->
                BaseData.getAsset(selectedChain.apiName, denom)?.let { asset ->
                    asset.decimals?.let { decimal ->
                        val dpAmount = BigDecimal(toAmount).movePointLeft(decimal)
                            .setScale(decimal, RoundingMode.DOWN)
                        redelegateAmountMsg.visibility = View.GONE
                        redelegateAmount.text = formatAmount(dpAmount.toPlainString(), decimal)
                        redelegateAmount.setTextColor(
                            ContextCompat.getColor(
                                requireContext(), R.color.color_base01
                            )
                        )
                        redelegateDenom.visibility = View.VISIBLE
                        redelegateDenom.text = asset.symbol
                    }
                }
            }
            txSimulate()
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

                selectedChain.cosmosDelegations.firstOrNull { it.delegation.validatorAddress == fromValidator?.operatorAddress }
                    ?.let {
                        availableAmount = it.balance.amount.toBigDecimal()
                    }
            }
        }
    }

    private fun setUpClickAction() {
        binding.apply {
            fromValidatorView.setOnClickListener {
                ValidatorFragment(selectedChain, object : ValidatorListener {
                    override fun select(validatorAddress: String) {
                        if (fromValidator?.operatorAddress != validatorAddress) {
                            fromValidator =
                                selectedChain.cosmosValidators.firstOrNull { it.operatorAddress == validatorAddress }
                            updateFeeView()
                            updateFromValidatorView()
                        }
                    }
                }).show(
                    requireActivity().supportFragmentManager, ValidatorFragment::class.java.name
                )
                setClickableOnce(isClickable)
            }

            toValidatorView.setOnClickListener {
                ValidatorDefaultFragment(selectedChain, object : ValidatorDefaultListener {
                    override fun select(validatorAddress: String) {
                        toValidator =
                            selectedChain.cosmosValidators.firstOrNull { it.operatorAddress == validatorAddress }
                        updateToValidatorView()
                    }
                }).show(
                    requireActivity().supportFragmentManager,
                    ValidatorDefaultFragment::class.java.name
                )
                setClickableOnce(isClickable)
            }

            amountView.setOnClickListener {
                InsertAmountFragment(TxType.RE_DELEGATE,
                    null,
                    availableAmount,
                    toCoin?.amount,
                    selectedChain.stakeDenom?.let {
                        BaseData.getAsset(
                            selectedChain.apiName, it
                        )
                    },
                    null,
                    object : AmountSelectListener {
                        override fun select(toAmount: String) {
                            updateAmountView(toAmount)
                        }

                    }).show(
                    requireActivity().supportFragmentManager, InsertAmountFragment::class.java.name
                )
                setClickableOnce(isClickable)
            }

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

            btnRedelegate.setOnClickListener {
                Intent(requireContext(), PasswordCheckActivity::class.java).apply {
                    relegateResultLauncher.launch(this)
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

    private val relegateResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && isAdded) {
                binding.backdropLayout.visibility = View.VISIBLE
                txViewModel.broadReDelegate(
                    getChannel(selectedChain),
                    selectedChain.address,
                    onBindReDelegate(),
                    txFee,
                    txMemo,
                    selectedChain
                )
            }
        }

    private fun txSimulate() {
        binding.apply {
            if (toCoin == null) {
                return
            }
            btnRedelegate.updateButtonView(false)
            backdropLayout.visibility = View.VISIBLE
            txViewModel.simulateReDelegate(
                getChannel(selectedChain), selectedChain.address, onBindReDelegate(), txFee, txMemo
            )
        }
    }

    private fun setUpSimulate() {
        txViewModel.simulate.observe(viewLifecycleOwner) { gasInfo ->
            isBroadCastTx(true)
            updateFeeViewWithSimul(gasInfo)
        }

        txViewModel.errorMessage.observe(viewLifecycleOwner) { response ->
            isBroadCastTx(false)
            requireContext().showToast(view, response, true)
            return@observe
        }
    }

    private fun updateFeeViewWithSimul(gasInfo: AbciProto.GasInfo) {
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
        binding.btnRedelegate.updateButtonView(isSuccess)
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

    private fun onBindReDelegate(): MsgBeginRedelegate {
        return MsgBeginRedelegate.newBuilder().setDelegatorAddress(selectedChain.address)
            .setValidatorSrcAddress(fromValidator?.operatorAddress)
            .setValidatorDstAddress(toValidator?.operatorAddress).setAmount(toCoin).build();
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}