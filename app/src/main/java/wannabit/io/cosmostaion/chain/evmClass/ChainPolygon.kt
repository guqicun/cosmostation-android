package wannabit.io.cosmostaion.chain.evmClass

import com.google.common.collect.ImmutableList
import org.bitcoinj.crypto.ChildNumber
import wannabit.io.cosmostaion.R
import wannabit.io.cosmostaion.chain.AccountKeyType
import wannabit.io.cosmostaion.chain.EthereumLine
import wannabit.io.cosmostaion.chain.PubKeyType

class ChainPolygon : EthereumLine() {

    override var name: String = "Polygon"
    override var tag: String = "polygon60"
    override var logo: Int = R.drawable.chain_polygon
    override var swipeLogo: Int = R.drawable.chain_swipe_polygon
    override var apiName: String = "polygon"

    override var coinSymbol: String = "MATIC"
    override var coinGeckoId: String = "matic-network"
    override var coinLogo: Int = R.drawable.token_matic
    override var supportStaking = false

    override var accountKeyType = AccountKeyType(PubKeyType.ETH_KECCAK256, "m/44'/60'/0'/0/X")
    override var setParentPath: List<ChildNumber> = ImmutableList.of(
        ChildNumber(44, true), ChildNumber(60, true), ChildNumber.ZERO_HARDENED, ChildNumber.ZERO
    )

    override var rpcUrl: String = "https://polygon-rpc.com"

    override var explorerURL = "https://polygonscan.com/"
    override var addressURL = explorerURL + "address/"
    override var txURL = explorerURL + "tx/"
}