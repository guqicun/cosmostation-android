package wannabit.io.cosmostaion.ui.tx.step

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.cosmos.bank.v1beta1.TxProto.MsgSend
import com.cosmos.base.abci.v1beta1.AbciProto
import com.cosmos.base.v1beta1.CoinProto
import com.cosmos.tx.v1beta1.TxProto
import com.cosmwasm.wasm.v1.TxProto.MsgExecuteContract
import com.google.gson.Gson
import com.google.protobuf.ByteString
import com.google.zxing.client.android.Intents
import com.google.zxing.integration.android.IntentIntegrator
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import wannabit.io.cosmostaion.R
import wannabit.io.cosmostaion.chain.CosmosLine
import wannabit.io.cosmostaion.chain.allCosmosLines
import wannabit.io.cosmostaion.common.BaseConstant
import wannabit.io.cosmostaion.common.BaseData
import wannabit.io.cosmostaion.common.BaseUtils
import wannabit.io.cosmostaion.common.amountHandlerLeft
import wannabit.io.cosmostaion.common.dpToPx
import wannabit.io.cosmostaion.common.formatAmount
import wannabit.io.cosmostaion.common.formatAssetValue
import wannabit.io.cosmostaion.common.getChannel
import wannabit.io.cosmostaion.common.makeToast
import wannabit.io.cosmostaion.common.setTokenImg
import wannabit.io.cosmostaion.common.showToast
import wannabit.io.cosmostaion.common.updateButtonView
import wannabit.io.cosmostaion.common.visibleOrGone
import wannabit.io.cosmostaion.data.model.req.WasmIbcSendMsg
import wannabit.io.cosmostaion.data.model.req.WasmIbcSendReq
import wannabit.io.cosmostaion.data.model.req.WasmSendReq
import wannabit.io.cosmostaion.data.model.res.Asset
import wannabit.io.cosmostaion.data.model.res.AssetPath
import wannabit.io.cosmostaion.data.model.res.FeeInfo
import wannabit.io.cosmostaion.data.model.res.Token
import wannabit.io.cosmostaion.database.model.AddressBook
import wannabit.io.cosmostaion.database.model.RefAddress
import wannabit.io.cosmostaion.databinding.FragmentTransferBinding
import wannabit.io.cosmostaion.databinding.ItemSegmentedFeeBinding
import wannabit.io.cosmostaion.ui.dialog.qr.QrCodeActivity
import wannabit.io.cosmostaion.ui.dialog.tx.AmountSelectListener
import wannabit.io.cosmostaion.ui.dialog.tx.AssetFragment
import wannabit.io.cosmostaion.ui.dialog.tx.AssetSelectListener
import wannabit.io.cosmostaion.ui.dialog.tx.ChainFragment
import wannabit.io.cosmostaion.ui.dialog.tx.ChainListType
import wannabit.io.cosmostaion.ui.dialog.tx.ChainSelectListener
import wannabit.io.cosmostaion.ui.dialog.tx.InsertAmountFragment
import wannabit.io.cosmostaion.ui.dialog.tx.MemoFragment
import wannabit.io.cosmostaion.ui.dialog.tx.MemoListener
import wannabit.io.cosmostaion.ui.dialog.tx.address.AddressFragment
import wannabit.io.cosmostaion.ui.dialog.tx.address.AddressListener
import wannabit.io.cosmostaion.ui.dialog.tx.address.AddressType
import wannabit.io.cosmostaion.ui.main.chain.TxType
import wannabit.io.cosmostaion.ui.password.PasswordCheckActivity
import wannabit.io.cosmostaion.ui.tx.TxResultActivity
import java.math.BigDecimal
import java.math.RoundingMode
import java.nio.charset.StandardCharsets

class TransferFragment(
    private val selectedChain: CosmosLine, private val toSendDenom: String
) : BaseTxFragment() {

    private var _binding: FragmentTransferBinding? = null
    private val binding get() = _binding!!

    private var recipientAbleChains: MutableList<CosmosLine> = mutableListOf()
    private var transferAssetType: TransferAssetType? = null
    private var selectedAsset: Asset? = null
    private var selectedToken: Token? = null
    private var selectedRecipientChain: CosmosLine? = null

    private var feeInfos: MutableList<FeeInfo> = mutableListOf()
    private var selectedFeeInfo = 0
    private var txFee: TxProto.Fee? = null

    private var toSendAmount = ""
    private var existedAddress = ""
    private var txMemo = ""
    private var assetPath: AssetPath? = null

    private var availableAmount = BigDecimal.ZERO

    private var isClickable = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransferBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setupFeeView()
        initData()
        updateFeeView()
        setUpClickAction()
        setUpSimulate()
        setUpBroadcast()
    }

    private fun initView() {
        binding.apply {
            listOf(
                recipientChainView, sendAssetView, addressView, memoView, feeView
            ).forEach { it.setBackgroundResource(R.drawable.cell_bg) }

            chainImg.setImageResource(selectedChain.logo)
            chainName.text = selectedChain.name.uppercase()

            BaseData.getAsset(selectedChain.apiName, toSendDenom)?.let { asset ->
                tokenImg.setTokenImg(asset)
                tokenName.text = asset.symbol?.uppercase()
            }
        }
    }

    private fun setupFeeView() {
        binding.apply {
            feeInfos = selectedChain.getFeeInfos(requireContext())
            feeSegment.apply {
                setSelectedBackground(
                    ContextCompat.getColor(
                        requireContext(), R.color.color_accent_purple
                    )
                )
                setRipple(
                    ContextCompat.getColor(
                        requireContext(), R.color.color_accent_purple
                    )
                )
            }

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

    private fun initData() {
        binding.apply {
            BaseData.assets?.firstOrNull { it.denom?.lowercase() == toSendDenom.lowercase() }
                ?.let { asset ->
                    selectedAsset = asset
                    transferAssetType = TransferAssetType.COIN_TRANSFER

                } ?: run {
                selectedChain.tokens.firstOrNull { it.address == toSendDenom }.let { token ->
                    selectedToken = token
                    transferAssetType = TransferAssetType.CW20_TRANSFER
                }
            }

            recipientAbleChains.add(selectedChain)
            BaseData.assets?.forEach { asset ->
                if (transferAssetType == TransferAssetType.COIN_TRANSFER) {
                    if (asset.chain == selectedChain.apiName && asset.denom?.lowercase() == toSendDenom.lowercase()) {
                        addRecipientChainIfNotExists(asset.beforeChain(selectedChain.apiName))

                    } else if (asset.counterParty?.denom?.lowercase() == toSendDenom.lowercase()) {
                        addRecipientChainIfNotExists(asset.chain)
                    }
                } else {
                    if (asset.counterParty?.denom?.lowercase() == toSendDenom.lowercase()) {
                        addRecipientChainIfNotExists(asset.chain)
                    }
                }
            }
            recipientAbleChains.sortWith { o1, o2 ->
                when {
                    o1.name == selectedChain.name -> -1
                    o2.name == selectedChain.name -> 1
                    o1.name == "Cosmos" -> -1
                    o2.name == "Cosmos" -> 1
                    else -> 0
                }
            }
            selectedRecipientChain = recipientAbleChains[0]

            if (transferAssetType == TransferAssetType.COIN_TRANSFER) {
                BaseData.getAsset(selectedChain.apiName, toSendDenom)?.let { asset ->
                    tokenImg.setTokenImg(asset)
                    tokenName.text = asset.symbol
                }
            } else {
                selectedChain.tokens.firstOrNull { it.address == toSendDenom }.let { token ->
                    token?.let {
                        tokenImg.setTokenImg(it.assetImg())
                        tokenName.text = it.symbol
                    }
                }
            }
        }
    }

    private fun addRecipientChainIfNotExists(apiName: String?) {
        allCosmosLines().firstOrNull { it.apiName == apiName }?.let { sendAble ->
            if (recipientAbleChains.none { it.apiName == sendAble.apiName }) {
                recipientAbleChains.add(sendAble)
            }
        }
    }

    private fun updateRecipientChainView() {
        binding.apply {
            selectedRecipientChain?.let { recipientChain ->
                chainImg.setImageResource(recipientChain.logo)
                chainName.text = recipientChain.name.uppercase()
            }

            sendTitle.text = if (selectedChain.chainId == selectedRecipientChain?.chainId) {
                getString(R.string.title_coin_send)
            } else {
                getString(R.string.title_ibc_send)
            }
        }
    }

    private fun updateAmountView(toAmount: String) {
        binding.apply {
            tabMsgTxt.visibility = View.GONE
            amountLayout.visibility = View.VISIBLE
            toSendAmount = toAmount

            val dpAmount: BigDecimal
            val price: BigDecimal
            val value: BigDecimal

            if (transferAssetType == TransferAssetType.COIN_TRANSFER) {
                dpAmount = toAmount.toBigDecimal().amountHandlerLeft(selectedAsset?.decimals ?: 6)
                price = BaseData.getPrice(selectedAsset?.coinGeckoId)
                value = price.multiply(dpAmount)

                sendAmount.text =
                    formatAmount(dpAmount.toPlainString(), selectedAsset?.decimals ?: 6)
                sendValue.text = formatAssetValue(value)

            } else {
                selectedToken?.let { token ->
                    dpAmount = toAmount.toBigDecimal().amountHandlerLeft(token.decimals)
                    price = BaseData.getPrice(selectedToken?.coinGeckoId)
                    value = price.multiply(dpAmount)

                    sendAmount.text = formatAmount(dpAmount.toPlainString(), token.decimals)
                    sendValue.text = formatAssetValue(value)
                }
            }
        }
        txSimulate()
    }

    private fun updateRecipientAddressView(address: String) {
        binding.apply {
            existedAddress = address
            if (address.isEmpty()) {
                recipientAddressMsg.text = getString(R.string.str_tap_for_add_address_msg)
            } else {
                recipientAddress.text = address
            }
            recipientAddressMsg.visibleOrGone(address.isEmpty())
            recipientAddress.visibleOrGone(address.isNotEmpty())
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

            availableAmount = if (transferAssetType == TransferAssetType.COIN_TRANSFER) {
                val balanceAmount = selectedChain.balanceAmount(toSendDenom)
                if (txFee?.getAmount(0)?.denom == toSendDenom) {
                    val feeAmount = BigDecimal(txFee?.getAmount(0)?.amount)
                    if (feeAmount > balanceAmount) {
                        BigDecimal.ZERO
                    } else {
                        balanceAmount.subtract(feeAmount)
                    }
                } else {
                    balanceAmount
                }

            } else {
                selectedToken?.amount?.toBigDecimal()
            }
        }
    }

    private fun updateMemoView(memo: String) {
        binding.apply {
            txMemo = memo
            val message = txMemo.ifEmpty {
                getString(R.string.str_tap_for_add_memo_msg)
            }
            tabMemoMsg.text = message
            tabMemoMsg.setTextColor(
                ContextCompat.getColorStateList(
                    requireContext(),
                    if (txMemo.isEmpty()) R.color.color_base03 else R.color.color_base01
                )
            )
        }
        txSimulate()
    }

    private fun setUpClickAction() {
        binding.apply {
            btnQr.setOnClickListener {
                val integrator = IntentIntegrator.forSupportFragment(this@TransferFragment)
                integrator.setOrientationLocked(true)
                integrator.captureActivity = QrCodeActivity::class.java
                qrCodeResultLauncher.launch(integrator.createScanIntent())
            }

            recipientChainView.setOnClickListener {
                ChainFragment(recipientAbleChains,
                    ChainListType.SELECT_TRANSFER,
                    object : ChainSelectListener {
                        override fun select(chainId: String) {
                            if (selectedRecipientChain?.chainId != chainId) {
                                selectedRecipientChain =
                                    recipientAbleChains.firstOrNull { it.chainId == chainId }
                                updateRecipientChainView()
                                updateRecipientAddressView("")
                            }
                        }
                    }).show(
                    requireActivity().supportFragmentManager, ChainFragment::class.java.name
                )
                setClickableOnce(isClickable)
            }

            sendAssetView.setOnClickListener {
                InsertAmountFragment(TxType.TRANSFER,
                    transferAssetType,
                    availableAmount,
                    toSendAmount,
                    selectedAsset,
                    selectedToken,
                    object : AmountSelectListener {
                        override fun select(toAmount: String) {
                            updateAmountView(toAmount)
                        }
                    }).show(
                    requireActivity().supportFragmentManager, InsertAmountFragment::class.java.name
                )
                setClickableOnce(isClickable)
            }

            addressView.setOnClickListener {
                AddressFragment(selectedChain,
                    selectedRecipientChain,
                    existedAddress,
                    AddressType.DEFAULT_TRANSFER,
                    object : AddressListener {
                        override fun selectAddress(
                            refAddress: RefAddress?, addressBook: AddressBook?
                        ) {
                            refAddress?.dpAddress?.let {
                                updateRecipientAddressView(it)
                                updateMemoView("")

                            } ?: run {
                                addressBook?.let {
                                    updateRecipientAddressView(it.address)
                                    updateMemoView(it.memo)
                                }
                            }
                        }
                    }).show(
                    requireActivity().supportFragmentManager, AddressFragment::class.java.name
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

            btnSend.setOnClickListener {
                Intent(requireContext(), PasswordCheckActivity::class.java).apply {
                    sendResultLauncher.launch(this)
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

    private val qrCodeResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.getStringExtra(Intents.Scan.RESULT)?.trim()?.let { qrData ->
                    val scanString = qrData.split(" (MEMO) ")
                    var addressScan = ""
                    var memoScan = ""

                    if (scanString.size == 2) {
                        addressScan = scanString[0]
                        memoScan = scanString[1]
                    } else {
                        addressScan = scanString[0]
                    }
                    if (addressScan.isEmpty() || addressScan.length < 5) {
                        requireContext().makeToast(R.string.error_invalid_address)
                        return@let
                    }
                    if (addressScan == selectedChain.address) {
                        requireContext().makeToast(R.string.error_self_sending)
                        return@let
                    }

                    if (BaseUtils.isValidChainAddress(selectedChain, addressScan)) {
                        updateRecipientAddressView(addressScan.trim())
                        if (scanString.size > 1) {
                            updateMemoView(memoScan.trim())
                        }

                    } else {
                        requireContext().makeToast(R.string.error_invalid_address)
                        return@let
                    }
                }
            }
        }

    private val sendResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && isAdded) {
                binding.backdropLayout.visibility = View.VISIBLE
                if (selectedChain.chainId == selectedRecipientChain?.chainId) {
                    if (transferAssetType == TransferAssetType.COIN_TRANSFER) {
                        txViewModel.broadcastSend(
                            getChannel(selectedChain),
                            selectedChain.address,
                            onBindSend(),
                            txFee,
                            txMemo,
                            selectedChain
                        )

                    } else if (transferAssetType == TransferAssetType.CW20_TRANSFER) {
                        txViewModel.broadcastWasm(
                            getChannel(selectedChain),
                            onBindWasmSend(),
                            txFee,
                            txMemo,
                            selectedChain
                        )
                    }

                } else {
                    if (transferAssetType == TransferAssetType.COIN_TRANSFER) {
                        txViewModel.broadcastIbcSend(
                            getChannel(selectedChain),
                            getRecipientChannel(),
                            existedAddress,
                            assetPath,
                            toSendDenom,
                            toSendAmount,
                            txFee,
                            txMemo,
                            selectedChain
                        )

                    } else if (transferAssetType == TransferAssetType.CW20_TRANSFER) {
                        txViewModel.broadcastWasm(
                            getChannel(selectedChain),
                            onBindWasmIbcSend(),
                            txFee,
                            txMemo,
                            selectedChain
                        )
                    }
                }
            }
        }

    private fun txSimulate() {
        binding.apply {
            if (toSendAmount.isEmpty() || existedAddress.isEmpty()) {
                return
            }
            if (toSendAmount.toBigDecimal() == BigDecimal.ZERO) {
                return
            }
            btnSend.updateButtonView(false)
            backdropLayout.visibility = View.VISIBLE

            if (selectedChain.chainId == selectedRecipientChain?.chainId) {
                if (transferAssetType == TransferAssetType.COIN_TRANSFER) {
                    txViewModel.simulateSend(
                        getChannel(selectedChain),
                        selectedChain.address,
                        onBindSend(),
                        txFee,
                        txMemo
                    )

                } else if (transferAssetType == TransferAssetType.CW20_TRANSFER) {
                    txViewModel.simulateWasm(
                        getChannel(selectedChain),
                        selectedChain.address,
                        onBindWasmSend(),
                        txFee,
                        txMemo
                    )
                }

            } else {
                selectedRecipientChain?.let { toChain ->
                    assetPath = assetPath(selectedChain, toChain, toSendDenom)
                    when (transferAssetType) {
                        TransferAssetType.COIN_TRANSFER -> {
                            txViewModel.simulateIbcSend(
                                getChannel(selectedChain),
                                getRecipientChannel(),
                                selectedChain.address,
                                existedAddress,
                                assetPath,
                                toSendDenom,
                                toSendAmount,
                                txFee,
                                txMemo
                            )
                        }

                        TransferAssetType.CW20_TRANSFER -> {
                            txViewModel.simulateWasm(
                                getChannel(selectedChain),
                                selectedChain.address,
                                onBindWasmIbcSend(),
                                txFee,
                                txMemo
                            )
                        }

                        else -> {}
                    }
                }
            }
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
        binding.btnSend.updateButtonView(isSuccess)
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

    private fun getRecipientChannel(): ManagedChannel? {
        return selectedRecipientChain?.run {
            ManagedChannelBuilder.forAddress(grpcHost, grpcPort).useTransportSecurity().build()
        }
    }

    private fun onBindSend(): MsgSend {
        val sendCoin =
            CoinProto.Coin.newBuilder().setAmount(toSendAmount).setDenom(toSendDenom).build()
        return MsgSend.newBuilder().setFromAddress(selectedChain.address)
            .setToAddress(existedAddress).addAmount(sendCoin).build()
    }

    private fun onBindWasmSend(): MutableList<MsgExecuteContract?>? {
        val result: MutableList<MsgExecuteContract?> = mutableListOf()
        val wasmSendReq = WasmSendReq(existedAddress, toSendAmount)
        val jsonData = Gson().toJson(wasmSendReq)
        val msg = ByteString.copyFromUtf8(jsonData)
        result.add(
            MsgExecuteContract.newBuilder().setSender(selectedChain.address)
                .setContract(selectedToken?.address).setMsg(msg).build()
        )
        return result
    }

    private fun onBindWasmIbcSend(): MutableList<MsgExecuteContract?> {
        val result: MutableList<MsgExecuteContract?> = mutableListOf()
        val wasmIbcReq = WasmIbcSendMsg(assetPath?.channel, existedAddress, 900)
        val msgData = Gson().toJson(wasmIbcReq).toByteArray(StandardCharsets.UTF_8)
        val encodeMsg = Base64.encodeToString(msgData, Base64.NO_WRAP)

        val wasmIbcSendReq = WasmIbcSendReq(assetPath?.ibcContract(), toSendAmount, encodeMsg)
        val jsonData = Gson().toJson(wasmIbcSendReq)
        val msg = ByteString.copyFromUtf8(jsonData)
        result.add(
            MsgExecuteContract.newBuilder().setSender(selectedChain.address)
                .setContract(selectedToken?.address).setMsg(msg).build()
        )
        return result
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

enum class TransferAssetType { COIN_TRANSFER, CW20_TRANSFER, ERC20_TRANSFER }

fun assetPath(fromChain: CosmosLine, toChain: CosmosLine, denom: String): AssetPath? {
    val msAsset = BaseData.assets?.firstOrNull { it.denom?.lowercase() == denom.lowercase() }
    val msToken = fromChain.tokens.firstOrNull { it.address == denom }

    BaseData.assets?.forEach { asset ->
        if (msAsset != null) {
            if (asset.chain == fromChain.apiName &&
                asset.beforeChain(fromChain.apiName) == toChain.apiName &&
                asset.denom.equals(denom, true)
            ) {
                return AssetPath(asset.channel, asset.port)
            }
            if (asset.chain == toChain.apiName &&
                asset.beforeChain(toChain.apiName) == fromChain.apiName &&
                asset.counterParty?.denom?.equals(denom, true) == true
            ) {
                return AssetPath(asset.counterParty.channel, asset.counterParty.port)
            }
        } else {
            if (msToken != null &&
                asset.chain == toChain.apiName &&
                asset.beforeChain(toChain.apiName) == fromChain.apiName &&
                asset.counterParty?.denom.equals(msToken.address, true)
            ) {
                return AssetPath(asset.counterParty?.channel, asset.counterParty?.port)
            }
        }
    }
    return null
}