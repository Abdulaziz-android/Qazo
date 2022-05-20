package uz.mnsh.qazo.presentation.welcome.splash_screen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import uz.mnsh.qazo.R
import uz.mnsh.qazo.data.local.AppDatabase
import uz.mnsh.qazo.presentation.main.MainActivity
import uz.mnsh.qazo.presentation.welcome.WelcomeActivity
import uz.mnsh.qazo.presentation.welcome.intro_screen.IntroFragment
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    @Inject lateinit var appDatabase: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val exists = appDatabase.userDao().isExists()
        val handler = Handler(Looper.getMainLooper())
        val r = Runnable {
            if (exists) {
                (activity as WelcomeActivity?)?.finish()
                startActivity(Intent(requireContext(), MainActivity::class.java))
            } else {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, IntroFragment()).commit()
            }
        }
        handler.postDelayed(r, 2000)


        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

}