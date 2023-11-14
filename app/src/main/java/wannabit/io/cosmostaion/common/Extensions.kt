package wannabit.io.cosmostaion.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.cosmos.base.v1beta1.CoinProto
import com.kava.cdp.v1beta1.GenesisProto.CollateralParam
import com.kava.cdp.v1beta1.QueryProto.CDPResponse
import com.kava.incentive.v1beta1.QueryProto
import com.kava.pricefeed.v1beta1.QueryProto.QueryPricesResponse
import com.squareup.picasso.Picasso
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import wannabit.io.cosmostaion.R
import wannabit.io.cosmostaion.chain.CosmosLine
import wannabit.io.cosmostaion.common.BaseConstant.CONSTANT_D
import wannabit.io.cosmostaion.data.model.res.Asset
import wannabit.io.cosmostaion.data.model.res.NetworkResult
import wannabit.io.cosmostaion.database.Prefs
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

fun formatString(input: String, point: Int): SpannableString {
    val spannableString = SpannableString(input)
    spannableString.setSpan(RelativeSizeSpan(0.8f), spannableString.length - point, spannableString.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
    return spannableString
}

fun formatAmount(input: String, decimal: Int): SpannableString {
    val spannableString = SpannableString(getDecimalFormat(decimal).format(input.toBigDecimal()))
    spannableString.setSpan(RelativeSizeSpan(0.8f), spannableString.length - decimal, spannableString.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
    return spannableString
}

fun assetValue(value: BigDecimal): SpannableString {
    val formatted = BaseData.currencySymbol() + " " + getDecimalFormat(3).format(value)
    return formatString(formatted, 3)
}

fun formatAssetValue(value: BigDecimal): SpannableString {
    val spannableString = assetValue(value)
    spannableString.setSpan(RelativeSizeSpan(0.8f), 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
    return spannableString
}

fun priceChangeStatus(lastUpDown: BigDecimal): SpannableString {
    return if (BigDecimal.ZERO > lastUpDown) {
        formatString("- " + lastUpDown.toString().replace("-", "") + "%", 3)
    } else {
        formatString("+ $lastUpDown%", 3)
    }
}

fun TextView.priceChangeStatusColor(lastUpDown: BigDecimal) {
    if (BigDecimal.ZERO > lastUpDown) {
        if (Prefs.priceStyle == 0) {
            setTextColor(ContextCompat.getColorStateList(context, R.color.color_accent_red))
        } else {
            setTextColor(ContextCompat.getColorStateList(context, R.color.color_accent_green))
        }

    } else {
        if (Prefs.priceStyle == 0) {
            setTextColor(ContextCompat.getColorStateList(context, R.color.color_accent_green))
        } else {
            setTextColor(ContextCompat.getColorStateList(context, R.color.color_accent_red))
        }
    }
}

fun Activity.toMoveAnimation() {
    this.overridePendingTransition(
        R.anim.anim_slide_in_left,
        R.anim.anim_slide_out_right)
}

fun Activity.toMoveBack() {
    this.overridePendingTransition(
        R.anim.anim_slide_in_right,
        R.anim.anim_slide_out_left)
}

fun FragmentActivity.toMoveFragment(currentFragment: Fragment, moveFragment: Fragment, stackName: String) {
    this.supportFragmentManager.beginTransaction()
        .setCustomAnimations(
            R.animator.to_right,
            R.animator.from_right,
            R.animator.to_left,
            R.animator.from_left
        )
        .add(R.id.fragment_container, moveFragment)
        .hide(currentFragment)
        .setReorderingAllowed(true)
        .addToBackStack(stackName)
        .commitAllowingStateLoss()
}

fun ImageView.setTokenImg(asset: Asset) {
    Picasso.get().load(CosmostationConstants.CHAIN_BASE_URL + asset.image).error(R.drawable.token_default).into(this)
}

fun ImageView.setTokenImg(tokenImg: String) {
    Picasso.get().load(tokenImg).error(R.drawable.token_default).into(this)
}

fun ImageView.setImg(resourceId: Int) {
    Picasso.get().load(resourceId).into(this)
}

fun ImageView.setMonikerImg(line: CosmosLine, opAddress: String?) {
    Picasso.get().load(line.monikerImg(opAddress)).error(R.drawable.icon_default_vaildator).into(this)
}

fun AppCompatActivity.makeToast(id: Int) {
    Toast.makeText(this, this.getString(id), Toast.LENGTH_SHORT).show()
}

fun Context.makeToast(id: Int) {
    Toast.makeText(this, this.getString(id), Toast.LENGTH_SHORT).show()
}

fun Context.makeToast(msg: String?) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun formatCurrentTimeToYear(): String {
    val locale = Locale.getDefault()
    val date = Calendar.getInstance()
    val dateFormat = SimpleDateFormat(
        if (locale == Locale.US) "MMMM dd, yyyy" else "yyyy.M.d",
        locale
    )
    return dateFormat.format(date.time)
}

fun formatGrpcTxTimeToYear(context: Context, timeString: String): String {
    val locale = Locale.getDefault()
    val inputFormat = SimpleDateFormat(context.getString(R.string.str_tx_time_grpc_format))
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date = inputFormat.parse(timeString)

    val outputFormat = SimpleDateFormat(
        if (locale == Locale.US) "MMMM dd, yyyy" else "yyyy.M.d",
        locale
    )
    outputFormat.timeZone = TimeZone.getDefault()
    return outputFormat.format(date)
}

fun formatTxTimeToYear(context: Context, timeString: String): String {
    val locale = Locale.getDefault()
    val inputFormat = SimpleDateFormat(context.getString(R.string.str_tx_time_format))
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date = inputFormat.parse(timeString)

    val outputFormat = SimpleDateFormat(
        if (locale == Locale.US) "MMMM dd, yyyy" else "yyyy.M.d",
        locale
    )
    outputFormat.timeZone = TimeZone.getDefault()
    return outputFormat.format(date)
}

fun formatTxTimeToHour(context: Context, timeString: String): String {
    val inputFormat = SimpleDateFormat(context.getString(R.string.str_tx_time_format))
    val outputFormat = SimpleDateFormat(context.getString(R.string.str_dp_time_format2))
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
    return outputFormat.format(inputFormat.parse(timeString))
}

fun dateToLong(format: String, rawValue: String?): Long {
    val simpleDateFormat = SimpleDateFormat(format)
    simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
    return simpleDateFormat.parse(rawValue).time
}

fun dpTime(time: Long): String {
    val locale = Locale.getDefault()
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = time

    val outputFormat = SimpleDateFormat(
        if (locale == Locale.ENGLISH) "MMM dd, yyyy (HH:mm:ss)" else "yyyy-MM-dd HH:mm:ss",
        locale
    )
    return outputFormat.format(calendar.timeInMillis)
}

fun voteDpTime(time: Long): String {
    val locale = Locale.getDefault()
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = time

    val outputFormat = SimpleDateFormat(
        if (locale == Locale.ENGLISH) "MMM dd, yyyy HH:mm:ss" else "yyyy-MM-dd HH:mm:ss",
        locale
    )
    return outputFormat.format(calendar.timeInMillis)
}

fun gapTime(finishTime: Long): String {
    var result = "??"
    val now = Calendar.getInstance().timeInMillis
    val left = finishTime - now

    result = if (left >= CONSTANT_D) {
        "D-" + left / CONSTANT_D
    } else if (left >= BaseConstant.CONSTANT_H) {
        (left / BaseConstant.CONSTANT_H).toString() + " hours ago"
    } else if (left >= BaseConstant.CONSTANT_M) {
        (left / BaseConstant.CONSTANT_M).toString() + " minutes ago"
    } else {
        "0 days"
    }
    return result
}

fun View.visibleOrGone(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.goneOrVisible(visible: Boolean) {
    visibility = if (visible) View.GONE else View.VISIBLE
}

fun View.visibleOrInvisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

fun Button.updateButtonView(isBtnEnabled: Boolean) {
    if (isBtnEnabled) {
        isEnabled = true
        setTextColor(ContextCompat.getColorStateList(context, R.color.color_base01))
        setBackgroundResource(R.drawable.button_common_bg)
    } else {
        isEnabled = false
        setTextColor(ContextCompat.getColorStateList(context, R.color.color_base03))
        setBackgroundResource(R.drawable.button_disable_bg)
    }
}

fun Activity.historyToMintscan(selectedChain: CosmosLine?, txHash: String?) {
    val historyUrl = CosmostationConstants.EXPLORER_BASE_URL + "/" + selectedChain?.apiName +  "/transactions/" + txHash
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(historyUrl)))
}

fun BigDecimal.handlerLeft(decimal: Int, scale: Int): BigDecimal {
    return this.movePointLeft(decimal).setScale(scale, RoundingMode.HALF_UP)
}

fun BigDecimal.handlerRight(decimal: Int, scale: Int): BigDecimal {
    return this.movePointRight(decimal).setScale(scale, RoundingMode.HALF_UP)
}

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): NetworkResult<T> {
    return withContext(dispatcher) {
        try {
            val response = apiCall.invoke()

            response?.let {
                NetworkResult.Success(it)
            } ?: run {
                NetworkResult.Error("Response Empty", "No Response")
            }

        } catch (e: Exception) {
            NetworkResult.Error("Unknown Error", e.message ?: "Unknown error occurred.")
        }
    }
}

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): NetworkResult<T> {
    return try {
        val response = apiCall.invoke()

        response?.let {
            NetworkResult.Success(it)
        } ?: run {
            NetworkResult.Error("Response Empty", "No Response")
        }

    } catch (e: Exception) {
        NetworkResult.Error("Unknown Error", e.message ?: "Unknown error occurred.")
    }
}

fun getDecimalFormat(cnt: Int): DecimalFormat {
    val formatter = NumberFormat.getNumberInstance(Locale.US)
    val decimalFormat = formatter as DecimalFormat
    decimalFormat.roundingMode = RoundingMode.DOWN
    when (cnt) {
        0 -> decimalFormat.applyLocalizedPattern("###,###,###,###,###,###,###,##0")
        1 -> decimalFormat.applyLocalizedPattern("###,###,###,###,###,###,###,##0.0")
        2 -> decimalFormat.applyLocalizedPattern("###,###,###,###,###,###,###,##0.00")
        3 -> decimalFormat.applyLocalizedPattern("###,###,###,###,###,###,###,##0.000")
        4 -> decimalFormat.applyLocalizedPattern("###,###,###,###,###,###,###,##0.0000")
        5 -> decimalFormat.applyLocalizedPattern("###,###,###,###,###,###,###,##0.00000")
        6 -> decimalFormat.applyLocalizedPattern("###,###,###,###,###,###,###,##0.000000")
        7 -> decimalFormat.applyLocalizedPattern("###,###,###,###,###,###,###,##0.0000000")
        8 -> decimalFormat.applyLocalizedPattern("###,###,###,###,###,###,###,##0.00000000")
        9 -> decimalFormat.applyLocalizedPattern("###,###,###,###,###,###,###,##0.000000000")
        10 -> decimalFormat.applyLocalizedPattern("###,###,###,###,###,###,###,##0.0000000000")
        11 -> decimalFormat.applyLocalizedPattern("###,###,###,###,###,###,###,##0.00000000000")
        12 -> decimalFormat.applyLocalizedPattern("###,###,###,###,###,###,###,##0.000000000000")
        13 -> decimalFormat.applyLocalizedPattern("###,###,###,###,###,###,###,##0.0000000000000")
        14 -> decimalFormat.applyLocalizedPattern("###,###,###,###,###,###,###,##0.00000000000000")
        15 -> decimalFormat.applyLocalizedPattern("###,###,###,###,###,###,###,##0.000000000000000")
        16 -> decimalFormat.applyLocalizedPattern("###,###,###,###,###,###,###,##0.0000000000000000")
        17 -> decimalFormat.applyLocalizedPattern("###,###,###,###,###,###,###,##0.00000000000000000")
        18 -> decimalFormat.applyLocalizedPattern("###,###,###,###,###,###,###,##0.000000000000000000")
        else -> decimalFormat.applyLocalizedPattern("###,###,###,###,###,###,###,##0.000000")
    }
    return decimalFormat
}

fun dpToPx(context: Context, dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}

fun getChannel(selectedChain: CosmosLine): ManagedChannel {
    return ManagedChannelBuilder.forAddress(selectedChain.grpcHost, selectedChain.grpcPort).useTransportSecurity().build()
}

// kava
fun QueryProto.QueryRewardsResponse.allIncentiveCoins(): MutableList<CoinProto.Coin> {
    val result: MutableList<CoinProto.Coin> = mutableListOf()

    usdxMintingClaimsList.forEach { claim ->
        if (claim.baseClaim.hasReward()) {
            val usdxReward = claim.baseClaim.reward
            val amount = usdxReward.amount.toBigDecimal()
            if (amount > BigDecimal.ZERO) {
                result.indexOfFirst { it.denom == usdxReward.denom }.let { already ->
                    if (already != -1) {
                        val sumReward = result[already].amount.toBigDecimal().add(amount)
                        result[already] = CoinProto.Coin.newBuilder().setDenom(usdxReward.denom).setAmount(sumReward.toPlainString()).build()
                    } else {
                        result.add(usdxReward)
                    }
                }
            }
        }
    }

    hardLiquidityProviderClaimsList.forEach { hardIncen ->
        hardIncen.baseClaim.rewardList.forEach { hardReward ->
            val amount = hardReward.amount.toBigDecimal()
            if (amount > BigDecimal.ZERO) {
                result.indexOfFirst { it.denom == hardReward.denom }.let { already ->
                    if (already != -1) {
                        val sumReward = result[already].amount.toBigDecimal().add(amount)
                        result[already] = CoinProto.Coin.newBuilder().setDenom(hardReward.denom).setAmount(sumReward.toPlainString()).build()
                    } else {
                        result.add(hardReward)
                    }
                }
            }
        }
    }

    delegatorClaimsList.forEach { claim ->
        claim.baseClaim.rewardList.forEach { deleClaim ->
            val amount = deleClaim.amount.toBigDecimal()
            if (amount > BigDecimal.ZERO) {
                result.indexOfFirst { it.denom == deleClaim.denom }.let { already ->
                    if (already != -1) {
                        val sumReward = result[already].amount.toBigDecimal().add(amount)
                        result[already] = CoinProto.Coin.newBuilder().setDenom(deleClaim.denom).setAmount(sumReward.toPlainString()).build()
                    } else {
                        result.add(deleClaim)
                    }
                }
            }
        }
    }

    swapClaimsList.forEach { claim ->
        claim.baseClaim.rewardList.forEach { swapClaim ->
            val amount = swapClaim.amount.toBigDecimal()
            if (amount > BigDecimal.ZERO) {
                result.indexOfFirst { it.denom == swapClaim.denom }.let { already ->
                    if (already != -1) {
                        val sumReward = result[already].amount.toBigDecimal().add(amount)
                        result[already] = CoinProto.Coin.newBuilder().setDenom(swapClaim.denom).setAmount(sumReward.toPlainString()).build()
                    } else {
                        result.add(swapClaim)
                    }
                }
            }
        }
    }

    earnClaimsList.forEach { claim ->
        claim.baseClaim.rewardList.forEach { earnClaim ->
            val amount = earnClaim.amount.toBigDecimal()
            if (amount > BigDecimal.ZERO) {
                result.indexOfFirst { it.denom == earnClaim.denom }.let { already ->
                    if (already != -1) {
                        val sumReward = result[already].amount.toBigDecimal().add(amount)
                        result[already] = CoinProto.Coin.newBuilder().setDenom(earnClaim.denom).setAmount(sumReward.toPlainString()).build()
                    } else {
                        result.add(earnClaim)
                    }
                }
            }
        }
    }
    return result
}

fun QueryProto.QueryRewardsResponse.hasUsdxMinting(): Boolean {
    if (usdxMintingClaimsCount > 0 && usdxMintingClaimsList[0].hasBaseClaim()
        && usdxMintingClaimsList[0].baseClaim.hasReward() && usdxMintingClaimsList[0].baseClaim.reward.amount != "0") {
        return true
    }
    return false
}

fun QueryProto.QueryRewardsResponse.hardRewardDenoms(): MutableList<String> {
    val result = mutableListOf<String>()
    hardLiquidityProviderClaimsList.forEach { hardClaim ->
        hardClaim.baseClaim.rewardList.forEach { coin ->
            if (!result.contains(coin.denom)) {
                result.add(coin.denom)
            }
        }
    }
    return result
}

fun QueryProto.QueryRewardsResponse.delegatorRewardDenoms(): MutableList<String> {
    val result = mutableListOf<String>()
    delegatorClaimsList.forEach { delegClaim ->
        delegClaim.baseClaim.rewardList.forEach { coin ->
            if (!result.contains(coin.denom)) {
                result.add(coin.denom)
            }
        }
    }
    return result
}

fun QueryProto.QueryRewardsResponse.swapRewardDenoms(): MutableList<String> {
    val result = mutableListOf<String>()
    swapClaimsList.forEach { swapClaim ->
        swapClaim.baseClaim.rewardList.forEach { coin ->
            if (!result.contains(coin.denom)) {
                result.add(coin.denom)
            }
        }
    }
    return result
}

fun QueryProto.QueryRewardsResponse.earnRewardDenoms(): MutableList<String> {
    val result = mutableListOf<String>()
    earnClaimsList.forEach { earnClaim ->
        earnClaim.baseClaim.rewardList.forEach { coin ->
            if (!result.contains(coin.denom)) {
                result.add(coin.denom)
            }
        }
    }
    return result
}

fun CollateralParam.liquidationRatioAmount(): BigDecimal {
    return liquidationRatio.toBigDecimal().movePointLeft(18).setScale(18, RoundingMode.DOWN)
}

fun CollateralParam.expectCollateralUSDXValue(collateralAmount: BigDecimal?, priceFeed: QueryPricesResponse?): BigDecimal {
    val collateralPrice = priceFeed?.kavaOraclePrice(liquidationMarketId)
    val collateralValue = collateralAmount?.multiply(collateralPrice)?.movePointLeft(conversionFactor.toInt())?.setScale(6, RoundingMode.DOWN)
    return collateralValue?.movePointRight(6)?.setScale(0, RoundingMode.DOWN) ?: BigDecimal.ZERO
}

fun CollateralParam.expectUSDXLTV(collateralAmount: BigDecimal?, priceFeed: QueryPricesResponse?): BigDecimal {
    return expectCollateralUSDXValue(collateralAmount, priceFeed).divide(liquidationRatioAmount(), 0, RoundingMode.DOWN)
}

fun CDPResponse.collateralUSDXAmount(): BigDecimal {
    return collateralValue.amount.toBigDecimal().movePointLeft(6).setScale(6, RoundingMode.DOWN)
}

fun CDPResponse.UsdxLTV(collateralParam: CollateralParam): BigDecimal {
    return collateralUSDXAmount().divide(collateralParam.liquidationRatioAmount(), 6, RoundingMode.DOWN)
}

fun CDPResponse.principalAmount(): BigDecimal {
    return principal.amount.toBigDecimal()
}

fun CDPResponse.debtAmount(): BigDecimal {
    return principalAmount().add(accumulatedFees.amount.toBigDecimal())
}

fun CDPResponse.debtUsdxValue(): BigDecimal {
    return debtAmount().movePointLeft(6).setScale(6, RoundingMode.DOWN)
}

fun CDPResponse.liquidationPrice(collateralParam: CollateralParam): BigDecimal {
    val cDenomDecimal = collateralParam.conversionFactor.toInt()
    val collateralAmount = collateral.amount.toBigDecimal().movePointLeft(cDenomDecimal).setScale(cDenomDecimal, RoundingMode.DOWN)
    val rawDebtAmount = debtAmount().multiply(collateralParam.liquidationRatioAmount()).movePointLeft(6).setScale(6, RoundingMode.DOWN)
    return rawDebtAmount.divide(collateralAmount, 6, RoundingMode.DOWN)
}

fun QueryPricesResponse.kavaOraclePrice(marketId: String): BigDecimal {
    pricesList.firstOrNull { it.marketId == marketId }?.let { price ->
        return price.price.toBigDecimal().movePointLeft(18).setScale(6, RoundingMode.DOWN)
    } ?: run {
        return BigDecimal.ZERO
    }
}

