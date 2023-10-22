package wannabit.io.cosmostaion.data.repository.chain

import retrofit2.Response
import wannabit.io.cosmostaion.data.model.res.BnbHistoryResponse
import wannabit.io.cosmostaion.data.model.res.CosmosHistory
import wannabit.io.cosmostaion.data.model.res.NetworkResult

interface HistoryRepository {

    suspend fun cosmosHistory(chain: String, address: String?, limit: String, searchAfter: String): NetworkResult<Response<List<CosmosHistory>>>

    suspend fun bnbHistory(address: String?, startTime: String, endTime: String): NetworkResult<Response<BnbHistoryResponse>>
}