package uz.mnsh.qazo.presentation.main.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import it.sephiroth.android.library.numberpicker.NumberPicker
import uz.mnsh.qazo.databinding.ItemEditPrayerBinding
import uz.mnsh.qazo.domain.model.Prayer
import javax.inject.Inject

class PrayerEditAdapter @Inject constructor() :
    ListAdapter<Prayer, PrayerEditAdapter.PVH>(DiffCallBack) {

    private val TAG = "PrayerEditAdapter"
    
    inner class PVH(private val itemBinding: ItemEditPrayerBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(prayer: Prayer) {
            itemBinding.apply {
                titleTv.text = prayer.prayerTimeName
                countNp.progress = prayer.remainingCount
                val todayPerformed = prayer.todayPerformedCount
                val remainingCount = prayer.remainingCount
                val performedCount = prayer.performedCount

                countNp.numberPickerChangeListener =
                    object : NumberPicker.OnNumberPickerChangeListener {
                        @SuppressLint("LogNotTimber")
                        override fun onProgressChanged(
                            numberPicker: NumberPicker,
                            progress: Int,
                            fromUser: Boolean
                        ) {
                            prayer.todayPerformedCount = todayPerformed
                            prayer.performedCount = performedCount
                            if (remainingCount>progress){
                                Log.d(TAG, "onProgressChanged: ${(remainingCount - progress)}")
                                prayer.todayPerformedCount += remainingCount - progress
                                prayer.performedCount += remainingCount - progress
                            }
                            prayer.remainingCount = progress
                        }
                        override fun onStartTrackingTouch(numberPicker: NumberPicker) {}
                        override fun onStopTrackingTouch(numberPicker: NumberPicker) {}

                    }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PVH {
        return PVH(
            ItemEditPrayerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PVH, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<Prayer>() {
        override fun areItemsTheSame(oldItem: Prayer, newItem: Prayer): Boolean {
            return (oldItem.prayerTimeName == newItem.prayerTimeName)
        }

        override fun areContentsTheSame(oldItem: Prayer, newItem: Prayer): Boolean {
            return (oldItem.prayerTimeName == newItem.prayerTimeName)
        }

    }
}