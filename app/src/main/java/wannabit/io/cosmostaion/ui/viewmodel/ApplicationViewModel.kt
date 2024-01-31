package wannabit.io.cosmostaion.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cosmos.bank.v1beta1.QueryProto
import com.google.gson.Gson
import io.grpc.ManagedChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import wannabit.io.cosmostaion.chain.CosmosLine
import wannabit.io.cosmostaion.chain.cosmosClass.ChainBinanceBeacon
import wannabit.io.cosmostaion.chain.cosmosClass.ChainNeutron
import wannabit.io.cosmostaion.chain.cosmosClass.ChainOkt60
import wannabit.io.cosmostaion.common.BaseData
import wannabit.io.cosmostaion.common.BaseUtils
import wannabit.io.cosmostaion.common.getChannel
import wannabit.io.cosmostaion.data.model.res.AccountResponse
import wannabit.io.cosmostaion.data.model.res.BnbToken
import wannabit.io.cosmostaion.data.model.res.NetworkResult
import wannabit.io.cosmostaion.data.model.res.OktAccountResponse
import wannabit.io.cosmostaion.data.model.res.OktDepositedResponse
import wannabit.io.cosmostaion.data.model.res.OktTokenResponse
import wannabit.io.cosmostaion.data.model.res.OktWithdrawResponse
import wannabit.io.cosmostaion.data.model.res.Param
import wannabit.io.cosmostaion.data.model.res.TokenResponse
import wannabit.io.cosmostaion.data.model.res.VestingData
import wannabit.io.cosmostaion.data.repository.wallet.WalletRepository
import wannabit.io.cosmostaion.database.model.BaseAccount
import wannabit.io.cosmostaion.database.model.RefAddress
import wannabit.io.cosmostaion.ui.main.CosmostationApp
import wannabit.io.cosmostaion.ui.viewmodel.event.SingleLiveEvent
import java.util.concurrent.TimeUnit

class ApplicationViewModel(
    application: Application, private val walletRepository: WalletRepository
) : AndroidViewModel(application) {
    companion object {
        val shared
            get() = CosmostationApp.instance.applicationViewModel
    }

    private var _currentAccountResult = MutableLiveData<Pair<Boolean, BaseAccount?>>()
    val currentAccountResult: LiveData<Pair<Boolean, BaseAccount?>> get() = _currentAccountResult
    fun currentAccount(baseAccount: BaseAccount?, isNew: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        _currentAccountResult.postValue(Pair(isNew, baseAccount))
    }

    var txRecreateResult = SingleLiveEvent<Boolean>()
    fun txRecreate() = viewModelScope.launch(Dispatchers.IO) {
        txRecreateResult.postValue(true)
    }

    var walletEditResult = SingleLiveEvent<MutableList<String>>()
    fun walletEdit(displayChains: MutableList<String>) = viewModelScope.launch(Dispatchers.IO) {
        walletEditResult.postValue(displayChains)
    }

    private var _hideValueResult = MutableLiveData<Boolean>()
    val hideValueResult: LiveData<Boolean> get() = _hideValueResult
    fun hideValue() = CoroutineScope(Dispatchers.IO).launch {
        _hideValueResult.postValue(true)
    }

    var fetchedTokenResult = SingleLiveEvent<Boolean>()
    fun fetchedToken() = viewModelScope.launch(Dispatchers.IO) {
        fetchedTokenResult.postValue(true)
    }

    var displayLegacyResult = SingleLiveEvent<Boolean>()
    fun displayLegacy(isDisplay: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        displayLegacyResult.postValue(isDisplay)
    }


    private val _chainDataErrorMessage = MutableLiveData<String>()
    val chainDataErrorMessage: LiveData<String> get() = _chainDataErrorMessage

    fun loadChainData(
        line: CosmosLine, baseAccountId: Long
    ) = viewModelScope.launch(Dispatchers.IO) {
        line.apply {
            val loadParamDeferred = async { walletRepository.param(this@apply) }
            val tokenDeferred = async { walletRepository.token(this@apply) }

            if (supportCw20 || supportErc20) {
                val responses = awaitAll(loadParamDeferred, tokenDeferred)
                responses.forEach { response ->
                    when (response) {
                        is NetworkResult.Success -> {
                            when (response.data) {
                                is Param -> {
                                    line.param = response.data
                                }

                                is TokenResponse -> {
                                    line.tokens = response.data.assets
                                }
                            }
                        }

                        is NetworkResult.Error -> {
                            _chainDataErrorMessage.postValue("error type : ${response.errorType}  error message : ${response.errorMessage}")
                        }
                    }
                }

            } else {
                when (val paramResponse = loadParamDeferred.await()) {
                    is NetworkResult.Success -> {
                        param = paramResponse.data
                    }

                    is NetworkResult.Error -> {
                        _chainDataErrorMessage.postValue("error type : ${paramResponse.errorType}  error message : ${paramResponse.errorMessage}")
                    }
                }
            }

            if (this is ChainBinanceBeacon || this is ChainOkt60) {
                loadLcdData(this, baseAccountId)
            } else {
                loadGrpcAuthData(this, baseAccountId)
            }
        }
    }

    var fetchedSendResult = SingleLiveEvent<String>()
    var fetchedStakeResult = SingleLiveEvent<String>()
    var fetchedVoteResult = SingleLiveEvent<String>()

    private fun loadGrpcAuthData(
        line: CosmosLine, baseAccountId: Long
    ) = viewModelScope.launch(Dispatchers.IO) {
        line.apply {
            val channel = getChannel(line)
            when (val response = walletRepository.auth(channel, this)) {
                is NetworkResult.Success -> {
                    cosmosAuth = response.data?.account
                    loadGrpcMoreData(channel, baseAccountId, line)
                }

                is NetworkResult.Error -> {
                    fetched = true
                    if (fetched) {
                        val refAddress = RefAddress(baseAccountId, tag, address, "0", "0", "0", 0)
                        BaseData.updateRefAddressesMain(refAddress)
                        fetchedSendResult.postValue(tag)
                        fetchedStakeResult.postValue(tag)
                        fetchedVoteResult.postValue(tag)
                    }
                }
            }
        }
    }

    private fun loadLcdData(
        line: CosmosLine, baseAccountId: Long
    ) = viewModelScope.launch(Dispatchers.IO) {
        line.apply {
            if (this is ChainBinanceBeacon) {
                val loadAccountInfoDeferred = async { walletRepository.binanceAccountInfo(line) }
                val loadBeaconTokenInfoDeferred = async { walletRepository.beaconTokenInfo() }

                val responses = awaitAll(
                    loadAccountInfoDeferred, loadBeaconTokenInfoDeferred
                )

                responses.forEach { response ->
                    when (response) {
                        is NetworkResult.Success -> {
                            when (response.data) {
                                is AccountResponse -> {
                                    lcdAccountInfo = response.data
                                }

                                is MutableList<*> -> {
                                    if (response.data.all { it is BnbToken }) {
                                        lcdBeaconTokens = response.data as MutableList<BnbToken>
                                    }
                                }
                            }

                            fetched = true
                            if (fetched) {
                                val refAddress = RefAddress(
                                    baseAccountId,
                                    tag,
                                    address,
                                    allAssetValue(true).toString(),
                                    lcdBalanceAmount(stakeDenom).toString(),
                                    "0",
                                    lcdAccountInfo?.balances?.size?.toLong() ?: 0
                                )
                                BaseData.updateRefAddressesMain(refAddress)
                                fetchedSendResult.postValue(tag)
                                fetchedStakeResult.postValue(tag)
                                fetchedVoteResult.postValue(tag)
                            }
                        }

                        is NetworkResult.Error -> {
                            fetched = true
                            if (fetched) {
                                val refAddress =
                                    RefAddress(baseAccountId, tag, address, "0", "0", "0", 0)
                                BaseData.updateRefAddressesMain(refAddress)
                                fetchedSendResult.postValue(tag)
                                fetchedStakeResult.postValue(tag)
                                fetchedVoteResult.postValue(tag)
                            }
                        }
                    }
                }

            } else if (this is ChainOkt60) {
                val loadAccountInfoDeferred = async { walletRepository.oktAccountInfo(line) }
                val loadDepositDeferred = async { walletRepository.oktDeposit(line) }
                val loadWithdrawDeferred = async { walletRepository.oktWithdraw(line) }
                val loadTokenDeferred = async { walletRepository.oktToken(line) }

                val responses = awaitAll(
                    loadAccountInfoDeferred,
                    loadDepositDeferred,
                    loadWithdrawDeferred,
                    loadTokenDeferred
                )

                responses.forEach { response ->
                    when (response) {
                        is NetworkResult.Success -> {
                            when (response.data) {
                                is OktAccountResponse -> {
                                    oktLcdAccountInfo = response.data
                                }

                                is OktDepositedResponse -> {
                                    oktDepositedInfo = response.data
                                }

                                is OktWithdrawResponse -> {
                                    oktWithdrawInfo = response.data
                                }

                                is OktTokenResponse -> {
                                    oktTokenInfo = response.data
                                }
                            }

                            fetched = true
                            if (fetched) {
                                val refAddress = RefAddress(
                                    baseAccountId,
                                    tag,
                                    address,
                                    allAssetValue(true).toString(),
                                    lcdBalanceAmount(stakeDenom).toString(),
                                    "0",
                                    oktLcdAccountInfo?.value?.coins?.size?.toLong() ?: 0
                                )
                                BaseData.updateRefAddressesMain(refAddress)
                                fetchedSendResult.postValue(tag)
                                fetchedStakeResult.postValue(tag)
                                fetchedVoteResult.postValue(tag)
                            }
                        }

                        is NetworkResult.Error -> {
                            fetched = true
                            if (fetched) {
                                val refAddress =
                                    RefAddress(baseAccountId, tag, address, "0", "0", "0", 0)
                                BaseData.updateRefAddressesMain(refAddress)
                                fetchedSendResult.postValue(tag)
                                fetchedStakeResult.postValue(tag)
                                fetchedVoteResult.postValue(tag)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loadGrpcMoreData(
        channel: ManagedChannel, id: Long, line: CosmosLine
    ) = viewModelScope.launch(Dispatchers.IO) {
        line.apply {
            try {
                val loadBalanceDeferred = async { walletRepository.balance(channel, line) }
                if (line.supportStaking) {
                    val loadDelegationDeferred =
                        async { walletRepository.delegation(channel, line) }
                    val loadUnBondingDeferred = async { walletRepository.unBonding(channel, line) }
                    val loadRewardDeferred = async { walletRepository.reward(channel, line) }

                    val responses = awaitAll(
                        loadBalanceDeferred,
                        loadDelegationDeferred,
                        loadUnBondingDeferred,
                        loadRewardDeferred
                    )

                    responses.forEach { response ->
                        when (response) {
                            is NetworkResult.Success -> {
                                when (response.data) {
                                    is QueryProto.QueryAllBalancesResponse -> {
                                        response.data.balancesList?.let { cosmosBalances = it }
                                    }

                                    is com.cosmos.staking.v1beta1.QueryProto.QueryDelegatorDelegationsResponse -> {
                                        cosmosDelegations.clear()
                                        response.data.delegationResponsesList.forEach { delegation ->
                                            if (delegation.balance.amount != "0") {
                                                cosmosDelegations.add(delegation)
                                            }
                                        }
                                    }

                                    is com.cosmos.staking.v1beta1.QueryProto.QueryDelegatorUnbondingDelegationsResponse -> {
                                        response.data.unbondingResponsesList?.let {
                                            cosmosUnbondings = it
                                        }
                                    }

                                    is com.cosmos.distribution.v1beta1.QueryProto.QueryDelegationTotalRewardsResponse -> {
                                        response.data.rewardsList?.let { cosmosRewards = it }
                                    }
                                }
                            }

                            is NetworkResult.Error -> {
                                _chainDataErrorMessage.postValue("error type : ${response.errorType}  error message : ${response.errorMessage}")
                            }
                        }
                    }

                } else {
                    if (line is ChainNeutron) {
                        val loadVaultDepositDeferred =
                            async { walletRepository.vaultDeposit(channel, line) }
                        val loadVestingDeferred =
                            async { walletRepository.vestingData(channel, line) }

                        val responses = awaitAll(
                            loadBalanceDeferred, loadVestingDeferred, loadVaultDepositDeferred
                        )

                        responses.forEach { response ->
                            when (response) {
                                is NetworkResult.Success -> {
                                    when (response.data) {
                                        is QueryProto.QueryAllBalancesResponse -> {
                                            response.data.balancesList?.let { cosmosBalances = it }
                                        }

                                        is com.cosmwasm.wasm.v1.QueryProto.QuerySmartContractStateResponse -> {
                                            line.neutronVesting = Gson().fromJson(
                                                response.data.data.toStringUtf8(),
                                                VestingData::class.java
                                            )
                                        }

                                        else -> {
                                            line.neutronDeposited =
                                                response.data.toString().toBigDecimal()
                                        }
                                    }
                                }

                                is NetworkResult.Error -> {
                                    _chainDataErrorMessage.postValue("error type : ${response.errorType}  error message : ${response.errorMessage}")
                                }
                            }
                        }

                    } else {
                        when (val balanceResponse = loadBalanceDeferred.await()) {
                            is NetworkResult.Success -> {
                                balanceResponse.data?.balancesList?.let { cosmosBalances = it }
                            }

                            is NetworkResult.Error -> {
                                _chainDataErrorMessage.postValue("error type : ${balanceResponse.errorType}  error message : ${balanceResponse.errorMessage}")
                            }
                        }
                    }
                }
                BaseUtils.onParseVestingAccount(line)
                fetched = true
                if (fetched) {
                    val refAddress = RefAddress(
                        id,
                        tag,
                        address,
                        allAssetValue(true).toPlainString(),
                        allStakingDenomAmount().toString(),
                        "0",
                        cosmosBalances?.size?.toLong() ?: 0
                    )
                    BaseData.updateRefAddressesMain(refAddress)
                    fetchedSendResult.postValue(tag)
                    fetchedStakeResult.postValue(tag)
                    fetchedVoteResult.postValue(tag)
                }

            } finally {
                channel.shutdown()
                try {
                    if (!channel.awaitTermination(5, TimeUnit.SECONDS)) {
                        channel.shutdownNow()
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }

    val loadTokenResult = SingleLiveEvent<Unit>()
    fun loadAllTokenBalance(
        line: CosmosLine, baseAccountId: Long
    ) = CoroutineScope(Dispatchers.Default).launch {
        line.apply {
            val deferredList = mutableListOf<Deferred<Unit>>()
            tokens.forEach { token ->
                if (supportCw20) {
                    val channel = getChannel(line)
                    val deferred = async { walletRepository.cw20Balance(channel, line, token) }
                    deferredList.add(deferred)
                } else {
                    val deferred = async { walletRepository.erc20Balance(line, token) }
                    deferredList.add(deferred)
                }
            }

            runBlocking {
                deferredList.awaitAll()

                val refAddress = RefAddress(
                    baseAccountId, tag, address, "0", "0", allTokenValue().toPlainString(), 0
                )
                val updatedResult = BaseData.updateRefAddressesToken(refAddress)
                loadTokenResult.postValue(updatedResult)
            }
        }
    }

    private var _filterDataResult = MutableLiveData<Boolean>()
    val filterDataResult: LiveData<Boolean> get() = _filterDataResult
    fun updateFilterData(isShowAll: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        _filterDataResult.postValue(isShowAll)
    }
}