package wannabit.io.cosmostaion.chain

import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import wannabit.io.cosmostaion.chain.cosmosClass.OKT_GECKO_ID
import wannabit.io.cosmostaion.common.BaseData
import wannabit.io.cosmostaion.common.safeApiCall
import wannabit.io.cosmostaion.data.api.RetrofitInstance
import wannabit.io.cosmostaion.data.model.res.NetworkResult
import java.math.BigDecimal
import java.math.RoundingMode

class OktFetcher(val chain: BaseChain) : CosmosFetcher(chain) {

    var oktAccountInfo: JsonObject? = JsonObject()
    var oktDeposits: JsonObject? = JsonObject()
    var oktWithdaws: JsonObject? = JsonObject()
    var oktTokens: JsonObject? = JsonObject()
    var oktValidatorInfo: MutableList<JsonObject> = mutableListOf()

    fun loadValidators() = CoroutineScope(Dispatchers.IO).launch {
        if (oktValidatorInfo.size > 0) {
            return@launch
        }

        when (val response = safeApiCall { RetrofitInstance.lcdApi(chain).oktValidators() }) {
            is NetworkResult.Success -> {
                oktValidatorInfo.clear()
                response.data.sortWith { o1, o2 ->
                    when {
                        o1?.get("description")?.asJsonObject?.get("moniker")?.asString == "Cosmostation" -> -1
                        o2?.get("description")?.asJsonObject?.get("moniker")?.asString == "Cosmostation" -> 1
                        o1.get("jailed").asBoolean && !o2.get("jailed").asBoolean -> 1
                        o1.get("delegator_shares").asString.toDouble() > o2.get("delegator_shares").asString.toDouble() -> -1
                        o1.get("delegator_shares").asString.toDouble() < o2.get("delegator_shares").asString.toDouble() -> 1
                        else -> 0
                    }
                }
                oktValidatorInfo = response.data
            }

            is NetworkResult.Error -> {
                return@launch
            }
        }
    }

    override fun allAssetValue(isUsd: Boolean?): BigDecimal {
        return oktBalanceValue(chain.stakeDenom, isUsd).add(oktDepositValue(isUsd))
            .add(oktWithdrawValue(isUsd))
    }

//    override fun isTxFeePayable(c: Context): Boolean {
//        val availableAmount = lcdBalanceAmount(chain.stakeDenom)
//        return availableAmount > BigDecimal(OKT_BASE_FEE)
//    }

    fun oktBalanceAmount(denom: String?): BigDecimal {
        oktAccountInfo?.get("value")?.asJsonObject?.get("coins")?.asJsonArray?.firstOrNull { it.asJsonObject["denom"].asString == denom }
            ?.let { balance ->
                return balance.asJsonObject["amount"].asString.toBigDecimal()
            }
        return BigDecimal.ZERO
    }

    private fun oktBalanceValue(denom: String, isUsd: Boolean?): BigDecimal {
        if (denom == chain.stakeDenom) {
            val amount = oktBalanceAmount(denom)
            val price = BaseData.getPrice(OKT_GECKO_ID, isUsd)
            return price.multiply(amount).setScale(6, RoundingMode.DOWN)
        }
        return BigDecimal.ZERO
    }

    fun oktDepositAmount(): BigDecimal {
        return oktDeposits?.get("tokens")?.asString?.toBigDecimal() ?: BigDecimal.ZERO
    }

    private fun oktDepositValue(isUsd: Boolean? = false): BigDecimal {
        val price = BaseData.getPrice(OKT_GECKO_ID, isUsd)
        val amount = oktDepositAmount()
        return price.multiply(amount).setScale(6, RoundingMode.DOWN)
    }

    fun oktWithdrawAmount(): BigDecimal {
        return oktWithdaws?.get("quantity")?.asString?.toBigDecimal() ?: BigDecimal.ZERO
    }

    private fun oktWithdrawValue(isUsd: Boolean? = false): BigDecimal {
        val price = BaseData.getPrice(OKT_GECKO_ID, isUsd)
        val amount = oktWithdrawAmount()
        return price.multiply(amount).setScale(6, RoundingMode.DOWN)
    }
}