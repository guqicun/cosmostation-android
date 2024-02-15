package wannabit.io.cosmostaion.ui.main.chain.evm

import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import wannabit.io.cosmostaion.R
import wannabit.io.cosmostaion.chain.EthereumLine
import wannabit.io.cosmostaion.common.BaseData
import wannabit.io.cosmostaion.common.formatAssetValue
import wannabit.io.cosmostaion.database.Prefs
import wannabit.io.cosmostaion.databinding.FragmentEvmDetailBinding
import wannabit.io.cosmostaion.ui.qr.QrCodeEvmFragment
import wannabit.io.cosmostaion.ui.viewmodel.ApplicationViewModel
import wannabit.io.cosmostaion.ui.viewmodel.intro.WalletViewModel

class EvmDetailFragment : Fragment() {

    private var _binding: FragmentEvmDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var detailPagerAdapter: DetailPagerAdapter

    private lateinit var selectedEvmChain: EthereumLine

    private val walletViewModel: WalletViewModel by activityViewModels()

    private var isClickable = true

    companion object {
        @JvmStatic
        fun newInstance(selectedEvmChain: EthereumLine): EvmDetailFragment {
            val args = Bundle().apply {
                putParcelable("selectedEvmChain", selectedEvmChain)
            }
            val fragment = EvmDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEvmDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        updateTokenValue()
        initTab()
        setUpClickAction()
    }

    private fun initData() {
        binding.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelable("selectedEvmChain", EthereumLine::class.java)
                    ?.let { selectedEvmChain = it }
            } else {
                (arguments?.getParcelable("selectedEvmChain") as? EthereumLine)?.let {
                    selectedEvmChain = it
                }
            }

            BaseData.baseAccount?.let { account ->
                accountName.text = account.name
                accountAddress.text = selectedEvmChain.address

                if (Prefs.hideValue) {
                    accountValue.text = "✱✱✱✱✱"
                    accountValue.textSize = 18f
                    btnHide.setImageResource(R.drawable.icon_hide)
                } else {
                    accountValue.text = formatAssetValue(selectedEvmChain.allValue(false))
                    accountValue.textSize = 24f
                    btnHide.setImageResource(R.drawable.icon_not_hide)
                }
                btnHide.setColorFilter(
                    ContextCompat.getColor(requireContext(), R.color.color_base03),
                    PorterDuff.Mode.SRC_IN
                )
            }
        }
    }

    private fun updateTokenValue() {
        if (isAdded) {
            requireActivity().runOnUiThread {
                binding.apply {
                    if (Prefs.hideValue) {
                        accountValue.text = "✱✱✱✱✱"
                        accountValue.textSize = 18f
                        btnHide.setImageResource(R.drawable.icon_hide)
                    } else {
                        accountValue.text = formatAssetValue(selectedEvmChain.allValue(false))
                        accountValue.textSize = 24f
                        btnHide.setImageResource(R.drawable.icon_not_hide)
                    }
                }
            }
        }
    }

    private fun initTab() {
        binding.apply {
            detailPagerAdapter = DetailPagerAdapter(
                requireActivity(), selectedEvmChain
            )
            viewPager.adapter = detailPagerAdapter
            viewPager.offscreenPageLimit = 2
            viewPager.isUserInputEnabled = false
            tabLayout.bringToFront()

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> getString(R.string.title_asset)
                    1 -> getString(R.string.title_nft)
                    else -> getString(R.string.title_history)
                }
            }.attach()

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val position = tab?.position ?: 0
                    viewPager.setCurrentItem(position, false)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }

    private fun setUpClickAction() {
        binding.apply {
            btnBack.setOnClickListener {
                requireActivity().onBackPressed()
            }

            btnAccount.setOnClickListener {
                val accountUrl = selectedEvmChain.addressURL + selectedEvmChain.address
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(accountUrl)))
                Prefs.foreToBack = false
            }

            accountAddress.setOnClickListener {
                QrCodeEvmFragment.newInstance(selectedEvmChain).show(
                    requireActivity().supportFragmentManager, QrCodeEvmFragment::class.java.name
                )
            }

            btnHide.setOnClickListener {
                Prefs.hideValue = !Prefs.hideValue
                if (Prefs.hideValue) {
                    accountValue.text = "✱✱✱✱✱"
                    accountValue.textSize = 18f
                    btnHide.setImageResource(R.drawable.icon_hide)
                } else {
                    accountValue.text = formatAssetValue(selectedEvmChain.allValue(false))
                    accountValue.textSize = 24f
                    btnHide.setImageResource(R.drawable.icon_not_hide)
                }
                ApplicationViewModel.shared.hideValue()
            }
        }
    }

    class DetailPagerAdapter(
        fragmentActivity: FragmentActivity, selectedEvmChain: EthereumLine
    ) : FragmentStateAdapter(fragmentActivity) {
        private val fragments = mutableListOf<Fragment>()

        init {
            fragments.add(AssetFragment.newInstance(selectedEvmChain))
            fragments.add(NftFragment.newInstance(selectedEvmChain))
            fragments.add(HistoryFragment.newInstance(selectedEvmChain))
        }

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}