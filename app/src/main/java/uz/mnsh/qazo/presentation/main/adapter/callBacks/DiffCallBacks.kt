package uz.mnsh.qazo.presentation.main.adapter.callBacks

import androidx.recyclerview.widget.DiffUtil
import uz.mnsh.qazo.domain.model.Prayer

class DiffCallBacks {

    companion object DiffCallBack : DiffUtil.ItemCallback<Prayer>() {
        override fun areItemsTheSame(oldItem: Prayer, newItem: Prayer): Boolean {
            return (oldItem.prayerTimeName == newItem.prayerTimeName)
        }

        override fun areContentsTheSame(oldItem: Prayer, newItem: Prayer): Boolean {
            return (oldItem == newItem)
        }
    }

}
