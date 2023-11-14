package wannabit.io.cosmostaion.ui.main.edit

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import wannabit.io.cosmostaion.R
import wannabit.io.cosmostaion.chain.ChainType
import wannabit.io.cosmostaion.chain.CosmosLine
import wannabit.io.cosmostaion.chain.cosmosClass.ChainBinanceBeacon
import wannabit.io.cosmostaion.chain.cosmosClass.ChainCosmos
import wannabit.io.cosmostaion.common.formatAssetValue
import wannabit.io.cosmostaion.common.goneOrVisible
import wannabit.io.cosmostaion.common.visibleOrGone
import wannabit.io.cosmostaion.database.model.BaseAccount
import wannabit.io.cosmostaion.databinding.ItemEditBinding

class ChainEditHolder(
    val context: Context,
    private val binding: ItemEditBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(baseAccount: BaseAccount, line: CosmosLine, cnt: Int, displayChains: MutableList<String>) {
        binding.apply {
            when (line.chainType) {
                ChainType.COSMOS_TYPE -> {
                    editView.setBackgroundResource(R.drawable.item_common_bg)
                    headerLayout.visibleOrGone(adapterPosition == 0)
                    headerTitle.text = context.getString(R.string.str_cosmos_class)
                    headerCnt.text = cnt.toString()

                    chainImg.setImageResource(line.logo)
                    chainName.text = line.name.uppercase()
                    chainPath.text = line.getHDPath(baseAccount.lastHDPath)
                    chainValue.text = formatAssetValue(line.allAssetValue())

                    if (line is ChainBinanceBeacon) {
                        assetCnt.text = line.lcdAccountInfo?.balances?.count().toString() + " Coins"
                    } else {
                        assetCnt.text = line.cosmosBalances.count().toString() + " Coins"
                    }

                    if (line.evmCompatible) {
                        chainLegacy.text = context.getString(R.string.str_evm)
                        chainLegacy.setBackgroundResource(R.drawable.round_box_evm)
                        chainLegacy.setTextColor(ContextCompat.getColor(context, R.color.color_base01))
                    } else if (!line.isDefault) {
                        chainLegacy.text = context.getString(R.string.str_deprecated)
                        chainLegacy.setBackgroundResource(R.drawable.round_box_deprecated)
                        chainLegacy.setTextColor(ContextCompat.getColor(context, R.color.color_base02))
                    } else {
                        chainLegacy.visibility = View.GONE
                    }

                    selectSwitch.bringToFront()
                    selectSwitch.goneOrVisible(line is ChainCosmos)
                    selectSwitch.isChecked = displayChains.contains(line.tag)

                    if (selectSwitch.isChecked) {
                        selectSwitch.thumbDrawable = ContextCompat.getDrawable(context, R.drawable.switch_thumb_on)
                    } else {
                        selectSwitch.thumbDrawable = ContextCompat.getDrawable(context, R.drawable.switch_thumb_off)
                    }

                    selectSwitch.setOnCheckedChangeListener { _, isChecked ->
                        val thumbDrawable: Drawable?
                        if (isChecked) {
                            thumbDrawable = ContextCompat.getDrawable(context, R.drawable.switch_thumb_on)
                            if (!displayChains.contains(line.tag)) {
                                displayChains.add(line.tag)
                            }
                        } else {
                            thumbDrawable = ContextCompat.getDrawable(context, R.drawable.switch_thumb_off)
                            displayChains.removeAll { it == line.tag }
                        }
                        selectSwitch.thumbDrawable = thumbDrawable
                    }
                }

                else -> { }
            }

        }
    }
}