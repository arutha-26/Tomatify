@file:Suppress("DEPRECATION")

package com.capstone.tomatifyapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.capstone.tomatifyapp.R
import com.capstone.tomatifyapp.ui.auth.LoginActivity
import com.capstone.tomatifyapp.ui.main.HomeActivity
import com.capstone.tomatifyapp.ui.predict.PredictActivity
import com.capstone.tomatifyapp.utils.PREF_TOKEN


@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hideActionBar()

        Handler().postDelayed(
        {

            val token = PreferenceManager.getDefaultSharedPreferences(this@SplashScreenActivity).getString(
                PREF_TOKEN, "")
            if (!token.isNullOrEmpty()){
                val i = Intent(this@SplashScreenActivity, HomeActivity::class.java)
                startActivity(i)
                finish()
            } else {
                val i = Intent(this@SplashScreenActivity, HomeActivity::class.java)
                startActivity(i)
                finish()
            }

        }, 3 * 1000)
    }

    private fun hideActionBar() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}