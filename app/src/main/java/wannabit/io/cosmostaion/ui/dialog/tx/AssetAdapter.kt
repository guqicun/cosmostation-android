package wannabit.io.cosmostaion.ui.dialog.tx

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import wannabit.io.cosmostaion.chain.CosmosLine
import wannabit.io.cosmostaion.data.model.res.FeeData
import wannabit.io.cosmostaion.databinding.ItemAssetBinding

class AssetAdapter(private val selectedChain: CosmosLine) :
    ListAdapter<FeeData, AssetViewHolder>(AssetDiffCallback()) {

    private var onItemClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        val binding = ItemAssetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AssetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {
        val feeData = currentList[position]
        holder.bind(selectedChain, feeData)

        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                feeData.denom?.let { denom -> it(denom)}
            }
        }
    }

    private class AssetDiffCallback : DiffUtil.ItemCallback<FeeData>() {

        override fun areItemsTheSame(oldItem: FeeData, newItem: FeeData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FeeData, newItem: FeeData): Boolean {
            return oldItem == newItem
        }
    }

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }
}
