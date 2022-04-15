package uz.mnsh.qazo.presentation.welcome.splash_screen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uz.mnsh.qazo.R
import uz.mnsh.qazo.presentation.welcome.intro_screen.IntroFragment

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val handler = Handler(Looper.getMainLooper())
        val r = Runnable {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, IntroFragment()).commit()
        }
        handler.postDelayed(r, 2000)


        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

}