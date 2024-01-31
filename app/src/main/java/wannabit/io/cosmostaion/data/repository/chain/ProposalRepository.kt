package wannabit.io.cosmostaion.data.repository.chain

import io.grpc.ManagedChannel
import retrofit2.Response
import wannabit.io.cosmostaion.data.model.res.CosmosProposal
import wannabit.io.cosmostaion.data.model.res.NetworkResult
import wannabit.io.cosmostaion.data.model.res.ResDaoVoteStatus
import wannabit.io.cosmostaion.data.model.res.VoteStatus

interface ProposalRepository {

    suspend fun cosmosProposal(chain: String): NetworkResult<Response<MutableList<CosmosProposal>>>

    suspend fun voteStatus(chain: String, account: String?): NetworkResult<Response<VoteStatus>>

    suspend fun daoProposals(managedChannel: ManagedChannel, contAddress: String?): NetworkResult<String?>

    suspend fun daoVoteStatus(chain: String, address: String?): NetworkResult<Response<MutableList<ResDaoVoteStatus>>>
}