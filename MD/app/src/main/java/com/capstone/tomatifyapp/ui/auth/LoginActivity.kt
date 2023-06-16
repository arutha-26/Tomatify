@file:Suppress("DEPRECATION")

package com.capstone.tomatifyapp.ui.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.tomatifyapp.R
import com.capstone.tomatifyapp.databinding.ActivityLoginBinding
import com.capstone.tomatifyapp.model.LoginResult
import com.capstone.tomatifyapp.ui.auth.viewmodel.AuthViewModel
import com.capstone.tomatifyapp.ui.main.HomeActivity
import com.capstone.tomatifyapp.utils.PREF_TOKEN
import dagger.hilt.android.AndroidEntryPoint
import com.capstone.tomatifyapp.utils.Result

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        hideActionBar()
        isLoading(false)

        playAnimation()

        binding.edSigninEmail.addTextChangedListener(watcher())
        binding.edSigninPassword.addTextChangedListener(watcher())

        binding.tvRegis.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnSignIn.setOnClickListener{
            login()
        }
    }

    private fun hideActionBar() {
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

    private fun playAnimation() {
        with(binding) {
            val imgView = ObjectAnimator.ofFloat(imageView3, View.ALPHA, 1f).setDuration(500)
            val titleLogin = ObjectAnimator.ofFloat(txtwelcome, View.ALPHA, 1f).setDuration(500)
            val titleEmail = ObjectAnimator.ofFloat(titleEmail, View.ALPHA, 1f).setDuration(500)
            val edEmail = ObjectAnimator.ofFloat(edSigninEmail, View.ALPHA, 1f).setDuration(500)
            val titlePass = ObjectAnimator.ofFloat(titlePassword, View.ALPHA, 1f).setDuration(500)
            val edPass = ObjectAnimator.ofFloat(edSigninPassword, View.ALPHA, 1f).setDuration(500)
            val btnSignIn = ObjectAnimator.ofFloat(btnSignIn, View.ALPHA, 1f).setDuration(500)
            val notMember = ObjectAnimator.ofFloat(notmember, View.ALPHA, 1f).setDuration(500)
            val titleRegister = ObjectAnimator.ofFloat(tvRegis, View.ALPHA, 1f).setDuration(500)

            AnimatorSet().apply {
                playSequentially(imgView, titleLogin, titleEmail, edEmail, titlePass, edPass, btnSignIn, notMember, titleRegister)
                start()
            }
        }
    }

    private fun login() {
        val email = binding.edSigninEmail.text.toString().trim()
        val password = binding.edSigninPassword.text.toString().trim()

        authViewModel.login(email, password).observe(this) { loginResult ->
            when (loginResult) {
                is Result.Loading -> {
                    isLoading(true)
                }
                is Result.Success -> {
                    isLoading(false)
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("email", email) // Mengirim data email ke halaman profile
                    startActivity(intent)
                    finish()

                }
                is Result.Error -> {
                    isLoading(false)

                    val errorMessage = loginResult.message ?: resources.getString(R.string.signin_failed)
                    if (errorMessage.equals("User not found")) {
                        binding.edSigninEmail.error = errorMessage
                    } else if (errorMessage.equals("Invalid password")) {
                        binding.edSigninPassword.error = errorMessage
                    }
                    Toast.makeText(
                        this@LoginActivity,
                        errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    private fun watcher() : TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.btnSignIn.isEnabled =
                    binding.edSigninEmail.text.toString().trim().isNotEmpty() &&
                            binding.edSigninEmail.error == null &&
                            binding.edSigninPassword.text.toString().trim().isNotEmpty() &&
                            binding.edSigninPassword.error == null
            }
        }
    }

    private fun isLoading(isL: Boolean) {
        binding.btnSignIn.isEnabled = !isL
        binding.rlLoading.visibility = if (isL) View.VISIBLE else View.GONE
    }
}
