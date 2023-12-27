package wannabit.io.cosmostaion.ui.main.chain

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import wannabit.io.cosmostaion.chain.CosmosLine
import wannabit.io.cosmostaion.chain.cosmosClass.ChainBinanceBeacon
import wannabit.io.cosmostaion.chain.cosmosClass.ChainKava459
import wannabit.io.cosmostaion.chain.cosmosClass.ChainOkt60
import wannabit.io.cosmostaion.common.BaseData
import wannabit.io.cosmostaion.common.BaseUtils
import wannabit.io.cosmostaion.data.model.res.Coin
import wannabit.io.cosmostaion.data.model.res.CoinType
import wannabit.io.cosmostaion.database.model.BaseAccount
import wannabit.io.cosmostaion.databinding.FragmentCoinBinding
import wannabit.io.cosmostaion.ui.dialog.tx.BridgeOptionFragment
import wannabit.io.cosmostaion.ui.tx.step.LegacyTransferFragment
import wannabit.io.cosmostaion.ui.tx.step.TransferFragment
import wannabit.io.cosmostaion.ui.tx.step.kava.Bep3Fragment
import wannabit.io.cosmostaion.ui.viewmodel.ApplicationViewModel
import wannabit.io.cosmostaion.ui.viewmodel.intro.WalletViewModel

class CoinFragment(position: Int) : Fragment() {

    private var _binding: FragmentCoinBinding? = null
    private val binding get() = _binding!!

    private lateinit var coinAdapter: CoinAdapter
    private lateinit var selectedChain: CosmosLine
    private val selectedPosition = position

    private var baseAccount: BaseAccount? = null

    private val stakeCoins = mutableListOf<Coin>()
    private val nativeCoins = mutableListOf<Coin>()
    private val bridgeCoins = mutableListOf<Coin>()
    private val ibcCoins = mutableListOf<Coin>()

    private val walletViewModel: WalletViewModel by activityViewModels()

    private var isClickable = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModels()
        refreshData()
        initRecyclerView()
        initData()
    }

    private fun initRecyclerView() {
        baseAccount = BaseData.baseAccount
        baseAccount?.let { baseAccount ->
            selectedChain = baseAccount.sortedDisplayCosmosLines()[selectedPosition]

            coinAdapter = CoinAdapter(requireContext(), selectedChain)
            binding.recycler.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                adapter = coinAdapter

                coinAdapter.setOnItemClickListener { line, denom ->
                    if (line is ChainBinanceBeacon) {
                        if (BaseUtils.isHtlcSwappableCoin(line, denom)) {
                            selectBridgeOption(line, denom)
                        } else {
                            startLegacyTransfer(line, denom)
                        }

                    } else if (line is ChainKava459) {
                        if (BaseUtils.isHtlcSwappableCoin(line, denom)) {
                            selectBridgeOption(line, denom)
                        } else {
                            startTransfer(line, denom)
                        }

                    } else if (line is ChainOkt60) {
                        startLegacyTransfer(line, denom)

                    } else {
                        startTransfer(line, denom)
                    }
                }
            }
        }
    }

    private fun initData() {
        stakeCoins.clear()
        nativeCoins.clear()
        ibcCoins.clear()
        bridgeCoins.clear()

        selectedChain.stakeDenom?.let { stakeDenom ->
            when (selectedChain) {
                is ChainBinanceBeacon -> {
                    selectedChain.lcdAccountInfo?.balances?.forEach { balance ->
                        if (balance.symbol == stakeDenom) {
                            stakeCoins.add(Coin(balance.symbol, balance.free, CoinType.STAKE))
                        } else {
                            val totalBalance = balance.free.toBigDecimal().add(balance.frozen.toBigDecimal()).add(balance.locked.toBigDecimal())
                            nativeCoins.add(Coin(balance.symbol, totalBalance.toPlainString(), CoinType.ETC))
                        }
                    }
                    if (stakeCoins.none { it.denom == stakeDenom }) {
                        stakeCoins.add(Coin(stakeDenom, "0", CoinType.STAKE))
                    }
                    nativeCoins.sortBy { it.denom }
                }

                is ChainOkt60 -> {
                    (selectedChain as ChainOkt60).oktLcdAccountInfo?.value?.coins?.forEach { balance ->
                        if (balance.denom == stakeDenom) {
                            stakeCoins.add(Coin(balance.denom, balance.amount, CoinType.STAKE))
                        } else {
                            nativeCoins.add(Coin(balance.denom, balance.amount, CoinType.ETC))
                        }
                    }
                    if (stakeCoins.none { it.denom == stakeDenom }) {
                        stakeCoins.add(Coin(stakeDenom, "0", CoinType.STAKE))
                    }
                    nativeCoins.sortBy { it.denom }
                }

                else -> {
                    selectedChain.cosmosBalances.forEach { coin ->
                        val coinType = BaseData.getAsset(selectedChain.apiName, coin.denom)?.type
                        coinType?.let {
                            when (it) {
                                "staking" -> stakeCoins.add(Coin(coin.denom, coin.amount, CoinType.STAKE))
                                "native" -> nativeCoins.add(Coin(coin.denom, coin.amount, CoinType.NATIVE))
                                "bep", "bridge" -> bridgeCoins.add(Coin(coin.denom, coin.amount, CoinType.BRIDGE))
                                "ibc" -> ibcCoins.add(Coin(coin.denom, coin.amount, CoinType.IBC))
                            }
                        }
                    }

                    if (stakeCoins.none { it.denom == selectedChain.stakeDenom }) {
                        stakeCoins.add(Coin(stakeDenom, "0", CoinType.STAKE))
                    }

                    nativeCoins.sortWith(compareByDescending { selectedChain.balanceValue(it.denom) })
                    ibcCoins.sortWith(compareByDescending { selectedChain.balanceValue(it.denom) })
                    bridgeCoins.sortWith(compareByDescending { selectedChain.balanceValue(it.denom) })
                }
            }
            coinAdapter.submitList(stakeCoins + nativeCoins + ibcCoins + bridgeCoins)
            coinAdapter.notifyDataSetChanged()
        }
        binding.refresher.isRefreshing = false
    }

    private fun refreshData() {
        binding.refresher.setOnRefreshListener {
            if (!selectedChain.fetched) {
                binding.refresher.isRefreshing = false
            } else {
                baseAccount?.id?.let { accountId ->
                    walletViewModel.loadChainData(selectedChain, accountId)
                }
            }
        }
    }

    private fun observeViewModels() {
        ApplicationViewModel.shared.hideValueResult.observe(viewLifecycleOwner) {
            coinAdapter.notifyDataSetChanged()
        }

        walletViewModel.fetchedResult.observe(viewLifecycleOwner) {
            if (selectedChain.fetched) {
                initData()
            }
        }
    }

    private fun startTransfer(line: CosmosLine, denom: String) {
        TransferFragment(line, denom).show(
            requireActivity().supportFragmentManager, TransferFragment::class.java.name
        )
        setClickableOnce(isClickable)
    }

    private fun startBep3Transfer(line: CosmosLine, denom: String) {
        Bep3Fragment(line, denom).show(
            requireActivity().supportFragmentManager, Bep3Fragment::class.java.name
        )
        setClickableOnce(isClickable)
    }

    private fun startLegacyTransfer(line: CosmosLine, denom: String) {
        LegacyTransferFragment(line, denom).show(
            requireActivity().supportFragmentManager, LegacyTransferFragment::class.java.name
        )
        setClickableOnce(isClickable)
    }

    private fun selectBridgeOption(line: CosmosLine, denom: String) {
        BridgeOptionFragment(line, denom, bridgeClickAction).show(
            requireActivity().supportFragmentManager, BridgeOptionFragment::class.java.name
        )
        setClickableOnce(isClickable)
    }

    private fun setClickableOnce(clickable: Boolean) {
        if (clickable) {
            isClickable = false

            Handler(Looper.getMainLooper()).postDelayed({
                isClickable = true
            }, 1000)
        }
    }

    private val bridgeClickAction = object : BridgeClickListener {
        override fun bep3Transfer(line: CosmosLine, denom: String) {
            startBep3Transfer(line, denom)
        }

        override fun simpleTransfer(line: CosmosLine, denom: String) {
            if (line is ChainBinanceBeacon) {
                startLegacyTransfer(line, denom)
            } else {
                startTransfer(line, denom)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

interface BridgeClickListener {
    fun bep3Transfer(line: CosmosLine, denom: String)
    fun simpleTransfer(line: CosmosLine, denom: String)
}