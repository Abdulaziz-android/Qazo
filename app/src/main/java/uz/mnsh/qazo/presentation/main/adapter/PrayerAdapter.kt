package uz.mnsh.qazo.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mnsh.qazo.databinding.ItemPrayerBinding
import uz.mnsh.qazo.domain.model.Prayer
import javax.inject.Inject

class PrayerAdapter @Inject constructor():RecyclerView.Adapter<PrayerAdapter.PVH>() {

    private var list = listOf<Prayer>()

    inner class PVH(private val itemBinding:ItemPrayerBinding):RecyclerView.ViewHolder(itemBinding.root){
        fun onBind(prayer:Prayer){
            itemBinding.titleTv.text = prayer.name
            itemBinding.countTv.text = prayer.count.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PVH {
        return PVH(ItemPrayerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PVH, position: Int) {
        holder.onBind(list[position])
    }

    fun submitList(list: List<Prayer>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

}