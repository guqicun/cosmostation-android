package wannabit.io.cosmostaion.ui.tx.info.neutron

import android.os.Build
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
import wannabit.io.cosmostaion.chain.cosmosClass.ChainNeutron
import wannabit.io.cosmostaion.chain.cosmosClass.NEUTRON_OVERRULE_MODULE
import wannabit.io.cosmostaion.common.getChannel
import wannabit.io.cosmostaion.common.updateButtonView
import wannabit.io.cosmostaion.data.model.res.ProposalData
import wannabit.io.cosmostaion.data.model.res.ResDaoVoteStatus
import wannabit.io.cosmostaion.databinding.FragmentDaoBinding
import wannabit.io.cosmostaion.ui.tx.step.neutron.DaoVoteFragment
import wannabit.io.cosmostaion.ui.viewmodel.chain.ProposalViewModel

class DaoOverruleFragment : Fragment() {

    private var _binding: FragmentDaoBinding? = null
    private val binding get() = _binding!!

    private lateinit var selectedChain: ChainNeutron
    private var neutronMyVotes: MutableList<ResDaoVoteStatus>? = mutableListOf()

    private val proposalViewModel: ProposalViewModel by activityViewModels()

    private lateinit var daoProposalListAdapter: DaoProposalListAdapter

    private var votingPeriods: MutableList<ProposalData?> = mutableListOf()
    private var etcPeriods: MutableList<ProposalData?> = mutableListOf()
    private var filterVotingPeriods: MutableList<ProposalData?> = mutableListOf()
    private var filterEtcPeriods: MutableList<ProposalData?> = mutableListOf()

    private var toVoteOverrule: MutableList<String?> = mutableListOf()

    companion object {
        @JvmStatic
        fun newInstance(
            selectedChain: CosmosLine, neutronMyVotes: MutableList<ResDaoVoteStatus>?
        ): DaoOverruleFragment {
            val args = Bundle().apply {
                putParcelable("selectedChain", selectedChain)
                putParcelableArrayList("neutronMyVotes", neutronMyVotes?.let { ArrayList(it) })
            }
            val fragment = DaoOverruleFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDaoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        setUpClickAction()
        updateFilterList()
    }

    private fun initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("selectedChain", ChainNeutron::class.java)
                ?.let { selectedChain = it }
        } else {
            (arguments?.getParcelable("selectedChain") as? ChainNeutron)?.let {
                selectedChain = it
            }
        }
        neutronMyVotes = arguments?.getParcelableArrayList("neutronMyVotes")

        selectedChain.param?.params?.chainlistParams?.daos?.get(0)?.proposal_modules?.get(2)?.address?.let { contAddress ->
            proposalViewModel.daoProposals(
                getChannel(selectedChain), contAddress, NEUTRON_OVERRULE_MODULE
            )
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.apply {
            proposalViewModel.daoOverruleProposalsResult.observe(viewLifecycleOwner) { response ->
                lifecycleScope.launch(Dispatchers.IO) {
                    response?.let { proposalData ->
                        votingPeriods.clear()
                        etcPeriods.clear()
                        filterVotingPeriods.clear()
                        filterEtcPeriods.clear()

                        votingPeriods =
                            proposalData.filter { "open" == it?.proposal?.status }.toMutableList()
                        etcPeriods =
                            proposalData.filter { "open" != it?.proposal?.status }.toMutableList()

                        filterVotingPeriods = votingPeriods.filter {
                            val title = it?.proposal?.title?.lowercase()
                            title?.contains("airdrop") == false && !title.containsEmoji()
                        }.toMutableList()
                        filterEtcPeriods = etcPeriods.filter {
                            val title = it?.proposal?.title?.lowercase()
                            title?.contains("airdrop") == false && !title.containsEmoji()
                        }.toMutableList()
                    }

                    withContext(Dispatchers.Main) {
                        updateView()
                    }
                }
            }
        }
    }

    private fun updateView() {
        binding.apply {
            loading.visibility = View.GONE
            if (filterVotingPeriods.isEmpty() && filterEtcPeriods.isEmpty()) {
                emptyLayout.visibility = View.VISIBLE
            } else {
                recycler.visibility = View.VISIBLE
                updateRecyclerView(filterVotingPeriods, filterEtcPeriods)
            }
            btnVote.updateButtonView(false)
        }
    }

    private fun updateRecyclerView(
        votingPeriods: MutableList<ProposalData?>, etcPeriods: MutableList<ProposalData?>
    ) {
        binding.recycler.apply {
            daoProposalListAdapter = DaoProposalListAdapter(
                selectedChain,
                NEUTRON_OVERRULE_MODULE,
                votingPeriods,
                etcPeriods,
                neutronMyVotes,
                daoProposalCheckAction
            )
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = daoProposalListAdapter
        }
    }

    private fun updateFilterList() {
        proposalViewModel.filterData.observe(viewLifecycleOwner) { isShowAll ->
            if (isShowAll) {
                updateRecyclerView(votingPeriods, etcPeriods)
            } else {
                updateRecyclerView(filterVotingPeriods, filterEtcPeriods)
            }
        }
    }

    private val daoProposalCheckAction = object : DaoProposalListAdapter.CheckListener {
        override fun daoProposalCheck(isChecked: Boolean, proposalId: String?) {
            if (isChecked && !toVoteOverrule.contains(proposalId)) {
                toVoteOverrule.add(proposalId)

            } else if (!isChecked && toVoteOverrule.contains(proposalId)) {
                toVoteOverrule.indexOf(proposalId).let { index ->
                    if (index != -1) {
                        toVoteOverrule.removeAt(index)
                    }
                }
            }
            binding.btnVote.updateButtonView(toVoteOverrule.isNotEmpty())
        }
    }

    private fun setUpClickAction() {
        binding.btnVote.setOnClickListener {
            val toOverruleProposals =
                votingPeriods.filter { toVoteOverrule.contains(it?.id) }.toMutableList()
            toOverruleProposals.forEach { it?.myVote = null }

            DaoVoteFragment.newInstance(
                selectedChain, mutableListOf(), mutableListOf(), toOverruleProposals
            ).show(
                requireActivity().supportFragmentManager, DaoVoteFragment::class.java.name
            )
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}