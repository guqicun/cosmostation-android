package wannabit.io.cosmostaion.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import wannabit.io.cosmostaion.chain.CosmosLine
import wannabit.io.cosmostaion.common.BaseConstant
import wannabit.io.cosmostaion.common.BaseData
import wannabit.io.cosmostaion.common.formatAssetValue
import wannabit.io.cosmostaion.common.toMoveAnimation
import wannabit.io.cosmostaion.database.model.BaseAccount
import wannabit.io.cosmostaion.databinding.FragmentDashboardBinding
import wannabit.io.cosmostaion.ui.main.chain.CosmosDetailActivity
import wannabit.io.cosmostaion.ui.viewmodel.ApplicationViewModel
import wannabit.io.cosmostaion.ui.viewmodel.intro.WalletViewModel
import java.math.BigDecimal


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding: FragmentDashboardBinding? get() = _binding

    private lateinit var dashAdapter: DashboardAdapter

    private var baseAccount: BaseAccount? = null

    private val walletViewModel: WalletViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModels()
        updateView()
    }

    private fun updateView() {
        initView()
        initRecyclerView()
        onUpdateLoadData()
    }

    private fun initView() {
        baseAccount = BaseData.baseAccount
        baseAccount?.initDisplayData()
    }

    private fun initRecyclerView() {
        baseAccount?.let { baseAccount ->
            dashAdapter = DashboardAdapter(requireContext())

            binding?.recycler?.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                adapter = dashAdapter
                dashAdapter.submitList(baseAccount.displayCosmosLineChains as List<Any>?)

                dashAdapter.setOnItemClickListener {
                    Intent(requireContext(), CosmosDetailActivity::class.java).apply {
                        putExtra("selectPosition", it)
                        startActivity(this)
                        requireActivity().toMoveAnimation()
                    }
                }
            }
        }
    }

    private fun onUpdateLoadData() {
        baseAccount?.let {
            it.displayCosmosLineChains.forEach { line ->
                line.setLoadDataCallBack(object : CosmosLine.LoadDataCallback {
                    override fun onDataLoaded(isLoaded: Boolean) {
                        lifecycleScope.launch {
                            withContext(Dispatchers.Main) {
                                if (line.fetched) {
                                    dashAdapter.notifyDataSetChanged()
                                    onUpdateTotal()
                                }
                            }
                        }
                    }
                })
            }
            onUpdateTotal()
        }
    }

    private fun onUpdateTotal() {
        var sum = BigDecimal.ZERO
        baseAccount?.let {
            it.displayCosmosLineChains.forEach { line ->
                sum = sum.add(line.allAssetValue())
            }
            if (isAdded) {
                requireActivity().runOnUiThread {
                    binding?.totalValue?.text = formatAssetValue(sum)
                }
            }
        }
    }

    private fun setupViewModels() {
        walletViewModel.walletPriceResult.observe(viewLifecycleOwner) { result ->
            if (result == BaseConstant.SUCCESS) {
                onUpdateTotal()
                dashAdapter.notifyDataSetChanged()
            }
        }

        walletViewModel.changeResult.observe(viewLifecycleOwner) { result ->
            if (result == BaseConstant.SUCCESS) {
                dashAdapter.notifyDataSetChanged()
            }
        }

        ApplicationViewModel.shared.currentAccountResult.observe(viewLifecycleOwner) {
            updateView()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}