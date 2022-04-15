package uz.mnsh.qazo.presentation.welcome.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.mnsh.qazo.presentation.welcome.data_collect_screen.page.DatePage
import uz.mnsh.qazo.presentation.welcome.data_collect_screen.page.GenderPage
import uz.mnsh.qazo.presentation.welcome.data_collect_screen.page.PrayerPage

class PagerAdapter(fm: FragmentManager, l:Lifecycle) : FragmentStateAdapter(fm, l) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> GenderPage.newInstance("", "")
            1 -> DatePage.newInstance("", "")
            2 -> PrayerPage.newInstance("", "")
            else -> GenderPage.newInstance("", "")
        }
    }


}