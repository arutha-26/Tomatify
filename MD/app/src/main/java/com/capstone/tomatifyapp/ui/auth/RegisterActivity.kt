@file:Suppress("DEPRECATION")

package com.capstone.tomatifyapp.ui.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.capstone.tomatifyapp.R
import com.capstone.tomatifyapp.databinding.ActivityRegisterBinding
import com.capstone.tomatifyapp.ui.auth.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.capstone.tomatifyapp.utils.Result

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        hideActionBar()
        isLoading(false)

        playAnimation()

        binding.edRegisterName.addTextChangedListener(watcher())
        binding.edRegisterEmail.addTextChangedListener(watcher())
        binding.edRegisterPassword.addTextChangedListener(watcher())

        binding.btnRegister.setOnClickListener {
            register()
        }

        binding.tvSignin.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun register() {
        val name = binding.edRegisterName.text.toString().trim()
        val email = binding.edRegisterEmail.text.toString().trim()
        val password = binding.edRegisterPassword.text.toString().trim()

        authViewModel.register(name, email, password).observe(this) { registerResult ->

            when(registerResult) {
                is Result.Loading -> isLoading(true)
                is Result.Success -> {
                    isLoading(false)
                    showEmailVerificationDialog()
                }
                else -> {
                    isLoading(false)
                    if (registerResult.message.equals(getString(R.string.email_is_already_taken))) {
                        binding.edRegisterEmail.error = registerResult.message
                    }
                    Toast.makeText(this, registerResult.message ?: getString(R.string.error_register), Toast.LENGTH_LONG).show()
                }
            }

        }
    }

    private fun showEmailVerificationDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog_layout, null)

        // Inisialisasi komponen-komponen pada layout custom_dialog_layout
        val messageTextView = dialogView.findViewById<TextView>(R.id.messageTextView)
        val okButton = dialogView.findViewById<Button>(R.id.okButton)

        messageTextView.text = getString(R.string.email_verification_message)

        dialogBuilder.setView(dialogView)
            .setCancelable(false)

        val alertDialog = dialogBuilder.create()

        okButton.setOnClickListener {
            // Arahkan pengguna ke LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            alertDialog.dismiss()
        }

        alertDialog.show()
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

    private fun watcher() : TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.btnRegister.isEnabled =
                    binding.edRegisterEmail.text.toString().trim().isNotEmpty() &&
                            binding.edRegisterEmail.error == null &&
                            binding.edRegisterPassword.text.toString().trim().isNotEmpty() &&
                            binding.edRegisterPassword.error == null &&
                            binding.edRegisterName.text.toString().trim().isNotEmpty()
            }

        }
    }

    private fun isLoading(isL: Boolean) {
        binding.btnRegister.isEnabled = !isL
        if (isL) {
            binding.rlLoading.visibility = View.VISIBLE
        } else {
            binding.rlLoading.visibility = View.GONE
        }
    }

    private fun playAnimation() {
        with(binding) {
            val imgView = ObjectAnimator.ofFloat(imageView3, View.ALPHA, 1f).setDuration(500)
            val titleRegis = ObjectAnimator.ofFloat(textView, View.ALPHA, 1f).setDuration(500)
            val titleName = ObjectAnimator.ofFloat(titleName, View.ALPHA, 1f).setDuration(500)
            val edName = ObjectAnimator.ofFloat(edRegisterName, View.ALPHA, 1f).setDuration(500)
            val titleEmail = ObjectAnimator.ofFloat(titleEmail, View.ALPHA, 1f).setDuration(500)
            val edEmail = ObjectAnimator.ofFloat(edRegisterEmail, View.ALPHA, 1f).setDuration(500)
            val titlePass = ObjectAnimator.ofFloat(titlePassword, View.ALPHA, 1f).setDuration(500)
            val edPass = ObjectAnimator.ofFloat(edRegisterPassword, View.ALPHA, 1f).setDuration(500)
            val btnSignIn = ObjectAnimator.ofFloat(btnRegister, View.ALPHA, 1f).setDuration(500)
            val notMember = ObjectAnimator.ofFloat(member, View.ALPHA, 1f).setDuration(500)
            val titleRegister = ObjectAnimator.ofFloat(tvSignin, View.ALPHA, 1f).setDuration(500)

            AnimatorSet().apply {
                playSequentially(imgView, titleRegis, titleName, edName, titleEmail, edEmail, titlePass, edPass, btnSignIn, notMember, titleRegister)
                start()
            }
        }
    }

}
