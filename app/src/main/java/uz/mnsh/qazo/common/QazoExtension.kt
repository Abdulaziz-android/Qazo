package uz.mnsh.qazo.common

import android.app.Activity
import androidx.navigation.Navigation
import uz.mnsh.qazo.R

fun Navigation.popBackStack(activity : Activity){
    this.findNavController(activity , R.id.nav_host_fragment_content_main)
        .popBackStack()
}