package uz.mnsh.qazo.presentation.main.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.mnsh.qazo.databinding.ItemPrayerBinding
import uz.mnsh.qazo.domain.model.Prayer
import uz.mnsh.qazo.presentation.main.adapter.callBacks.DiffCallBacks

class PrayerAdapter(
    private val listener: OnPrayerClickListener
) : ListAdapter<Prayer, PrayerAdapter.PVH>(
    DiffCallBacks.DiffCallBack
) {

    inner class PVH(private val itemBinding: ItemPrayerBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(prayer: Prayer, position: Int) {
            itemBinding.apply {
                titleTv.text = prayer.prayerTimeName
                itemBinding.countTv.text = prayer.remainingCount.toString()

                progressBar.apply {
                    progressBarColor = Color.parseColor(prayer.progressColor)
                    backgroundProgressBarColor = Color.parseColor(prayer.progressColor20)

                    if (prayer.remainingCount == 0) {
                        progressMax = 1f
                        progress = 1f
                    } else {
                        progressMax = prayer.remainingCount.toFloat() + prayer.performedCount.toFloat()
                        progress = prayer.performedCount.toFloat()
                    }
                }

                root.setOnClickListener {
                    listener.onPrayerClicked(prayer, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PVH {
        return PVH(ItemPrayerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PVH, position: Int) {
        holder.onBind(getItem(position), position)
    }

    interface OnPrayerClickListener {
        fun onPrayerClicked(prayer: Prayer, position: Int)
    }

}