package wannabit.io.cosmostaion.chain

import com.google.gson.JsonObject
import wannabit.io.cosmostaion.common.BaseData
import java.math.BigDecimal
import java.math.RoundingMode

class SuiFetcher(private val chain: BaseChain) : CosmosFetcher(chain) {

    var suiSystem = JsonObject()
    var suiBalances: MutableList<Pair<String?, BigDecimal?>> = mutableListOf()
    var suiStakedList: MutableList<JsonObject> = mutableListOf()
    var suiObjects: MutableList<JsonObject> = mutableListOf()
    var suiValidators: MutableList<JsonObject> = mutableListOf()
    val suiCoinMeta: MutableMap<String, JsonObject> = mutableMapOf()

    var suiState = true

    override fun allAssetValue(isUsd: Boolean?): BigDecimal {
        return suiBalanceValueSum(isUsd).add(suiStakedValue(isUsd))
    }

    fun allSuiAmount(): BigDecimal? {
        return suiBalanceAmount(SUI_MAIN_DENOM)?.add(stakedAmount()) ?: BigDecimal.ZERO
    }

    fun allSuiValue(isUsd: Boolean? = false): BigDecimal {
        val amount = allSuiAmount()
        if (amount == BigDecimal.ZERO) return BigDecimal.ZERO
        BaseData.getAsset(chain.apiName, SUI_MAIN_DENOM)?.let { asset ->
            val price = BaseData.getPrice(asset.coinGeckoId, isUsd)
            return price.multiply(amount).movePointLeft(asset.decimals ?: 6)
                .setScale(6, RoundingMode.DOWN)
        }
        return BigDecimal.ZERO
    }

    fun suiBalanceAmount(coinType: String): BigDecimal? {
        suiBalances.firstOrNull { it.first == coinType }?.let { suiCoin ->
            return suiCoin.second
        }
        return BigDecimal.ZERO
    }

    fun suiBalanceValue(coinType: String, isUsd: Boolean? = false): BigDecimal {
        val amount = suiBalanceAmount(coinType)
        if (amount == BigDecimal.ZERO) return BigDecimal.ZERO
        BaseData.getAsset(chain.apiName, coinType)?.let { asset ->
            val price = BaseData.getPrice(asset.coinGeckoId, isUsd)
            return price.multiply(amount).movePointLeft(asset.decimals ?: 6)
                .setScale(6, RoundingMode.DOWN)
        }
        return BigDecimal.ZERO
    }

    fun suiBalanceValueSum(isUsd: Boolean? = false): BigDecimal {
        var sum = BigDecimal.ZERO
        if (suiBalances.isNotEmpty()) {
            suiBalances.forEach { balance ->
                balance.first?.let { sum = sum.add(suiBalanceValue(it, isUsd)) }
            }
        }
        return sum
    }

    fun stakedAmount(): BigDecimal {
        var staked = BigDecimal.ZERO
        var earned = BigDecimal.ZERO
        suiStakedList.forEach { suiStaked ->
            suiStaked["stakes"].asJsonArray.forEach { stakes ->
                staked = staked.add(stakes.asJsonObject["principal"].asLong.toBigDecimal())
                earned = earned.add(
                    stakes.asJsonObject.get("estimatedReward")?.asLong?.toBigDecimal()
                        ?: BigDecimal.ZERO
                )
            }
        }
        return staked.add(earned)
    }

    fun suiStakedValue(isUsd: Boolean? = false): BigDecimal {
        val amount = stakedAmount()
        if (amount == BigDecimal.ZERO) {
            return BigDecimal.ZERO
        }
        BaseData.getAsset(chain.apiName, SUI_MAIN_DENOM)?.let { asset ->
            val price = BaseData.getPrice(asset.coinGeckoId, isUsd)
            return price.multiply(amount).movePointLeft(asset.decimals ?: 6)
                .setScale(6, RoundingMode.DOWN)
        }
        return BigDecimal.ZERO
    }

    fun principalAmount(): BigDecimal {
        var staked = BigDecimal.ZERO
        suiStakedList.forEach { suiStaked ->
            suiStaked["stakes"].asJsonArray.forEach { stakes ->
                staked = staked.add(stakes.asJsonObject["principal"].asLong.toBigDecimal())
            }
        }
        return staked
    }

    fun principalValue(isUsd: Boolean? = false): BigDecimal {
        val amount = principalAmount()
        if (amount == BigDecimal.ZERO) {
            return BigDecimal.ZERO
        }
        BaseData.getAsset(chain.apiName, SUI_MAIN_DENOM)?.let { asset ->
            val price = BaseData.getPrice(asset.coinGeckoId, isUsd)
            return price.multiply(amount).movePointLeft(asset.decimals ?: 6)
                .setScale(6, RoundingMode.DOWN)
        }
        return BigDecimal.ZERO
    }

    fun estimateRewardAmount(): BigDecimal {
        var earned = BigDecimal.ZERO
        suiStakedList.forEach { suiStaked ->
            suiStaked["stakes"].asJsonArray.forEach { stakes ->
                earned = earned.add(stakes.asJsonObject["estimatedReward"].asLong.toBigDecimal())
            }
        }
        return earned
    }

    fun estimateRewardValue(isUsd: Boolean? = false): BigDecimal {
        val amount = estimateRewardAmount()
        if (amount == BigDecimal.ZERO) {
            return BigDecimal.ZERO
        }
        BaseData.getAsset(chain.apiName, SUI_MAIN_DENOM)?.let { asset ->
            val price = BaseData.getPrice(asset.coinGeckoId, isUsd)
            return price.multiply(amount).movePointLeft(asset.decimals ?: 6)
                .setScale(6, RoundingMode.DOWN)
        }
        return BigDecimal.ZERO
    }

    fun suiRpc(): String {
        return chain.mainUrl
    }
}

fun String.suiIsCoinType(): Boolean {
    return this.startsWith(SUI_TYPE_COIN)
}

fun String?.suiCoinType(): String? {
    if (this?.suiIsCoinType() == false) {
        return null
    }
    this?.split("<")?.last()?.let { s1 ->
        return s1.split(">").first()
    }
    return null
}

fun String?.suiCoinSymbol(): String? {
    if (this?.suiIsCoinType() == false) {
        return null
    }
    this?.split("<")?.last()?.let { s1 ->
        s1.split(">").first().let { s2 ->
            return s2.split("::").last()
        }
    }
    return null
}

fun JsonObject?.assetImg(): String {
    return this?.get("iconUrl")?.asString ?: ""
}