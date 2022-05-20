package uz.mnsh.qazo.presentation.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.mnsh.qazo.databinding.ItemPrayerBinding
import uz.mnsh.qazo.domain.model.Prayer
import javax.inject.Inject

class PrayerAdapter @Inject constructor(
    @ApplicationContext private val context:Context
) : RecyclerView.Adapter<PrayerAdapter.PVH>() {

    private var list = listOf<Prayer>()

    inner class PVH(private val itemBinding: ItemPrayerBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(prayer: Prayer) {
            itemBinding.apply {
                titleTv.text = prayer.prayerTimeName
                itemBinding.countTv.text = prayer.remainingCount.toString()

                progressBar.apply {
                    progressBarColor = Color.parseColor(prayer.progressColor)
                    backgroundProgressBarColor = Color.parseColor(prayer.progressColor20)

                    progressMax = prayer.remainingCount.toFloat()
                    progress = prayer.performedCount.toFloat()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PVH {
        return PVH(ItemPrayerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PVH, position: Int) {
        holder.onBind(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Prayer>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

}