package uz.mnsh.qazo.presentation.welcome.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.mnsh.qazo.presentation.welcome.data_collect_screen.PagerFragment

class PagerAdapter(fm: FragmentManager, l:Lifecycle) : FragmentStateAdapter(fm, l) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return PagerFragment.newInstance("", position)
    }


}