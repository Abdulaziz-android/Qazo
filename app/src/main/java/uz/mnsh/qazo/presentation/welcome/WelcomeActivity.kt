package uz.mnsh.qazo.presentation.welcome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.mnsh.qazo.R
import uz.mnsh.qazo.databinding.ActivityWelcomeBinding
import uz.mnsh.qazo.presentation.welcome.splash_screen.SplashFragment

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, SplashFragment()).commit()
    }
}