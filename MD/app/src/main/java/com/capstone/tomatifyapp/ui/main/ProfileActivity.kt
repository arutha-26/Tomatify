package com.capstone.tomatifyapp.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.ContactsContract.CommonDataKinds.Email
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.capstone.tomatifyapp.R
import com.capstone.tomatifyapp.ui.auth.LoginActivity

@Suppress("DEPRECATION")
class ProfileActivity : AppCompatActivity() {

    private lateinit var email : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile"

        val logoutButton = findViewById<Button>(R.id.btn_logout)
        logoutButton.setOnClickListener {
            PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        email = intent.getStringExtra("email") ?: ""

        val email = intent.getStringExtra("email") ?: "" // Mendapatkan email dari intent
        val userEmailTextView = findViewById<TextView>(R.id.email)
        userEmailTextView.text = email // Menampilkan email pada TextView

        val contactUs = findViewById<Button>(R.id.btn_contactus)
        contactUs.setOnClickListener {
            openWhatsAppChat()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun openWhatsAppChat() {
        val phoneNumber = "6281999934616" // Ganti dengan nomor telepon tujuan
        val message = "Hi, it's me $email, can you help me?!? " // Pesan yang ingin dikirimkan
        val url = "https://api.whatsapp.com/send?phone=$phoneNumber&text=${Uri.encode(message)}"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
