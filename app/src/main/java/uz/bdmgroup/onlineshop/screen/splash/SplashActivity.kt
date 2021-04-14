package uz.bdmgroup.onlineshop.screen.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*
import uz.bdmgroup.onlineshop.R
import uz.bdmgroup.onlineshop.screen.MainActivity
import uz.bdmgroup.onlineshop.screen.sign.LoginActivity
import uz.bdmgroup.onlineshop.utils.PrefUtils

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        animationView.postDelayed({
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }, 3000)
    }
}