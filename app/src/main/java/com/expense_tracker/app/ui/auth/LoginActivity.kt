package com.expense_tracker.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.expense_tracker.app.R
import com.expense_tracker.app.data.AuthManager
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var authManager: AuthManager
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginBtn: Button
    private lateinit var signupLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        authManager = AuthManager(this)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginBtn = findViewById(R.id.loginBtn)
        signupLink = findViewById(R.id.signupLink)

        loginBtn.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            when {
                email.isEmpty() -> emailInput.error = "Email is required"
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> emailInput.error = "Please enter a valid email"
                password.isEmpty() -> passwordInput.error = "Password is required"
                password.length < 8 -> passwordInput.error = "Password must be at least 8 characters"
                else -> loginUser(email, password)
            }
        }

        signupLink.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    private fun loginUser(email: String, password: String) {
        loginBtn.isEnabled = false
        loginBtn.text = "Signing in..."

        lifecycleScope.launch {
            val result = authManager.login(email, password)
            result.onSuccess {
                sendOtp(email)
            }.onFailure { error ->
                loginBtn.isEnabled = true
                loginBtn.text = "Sign In"
                Toast.makeText(this@LoginActivity, "Login failed: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendOtp(email: String) {
        lifecycleScope.launch {
            val result = authManager.sendOtpToEmail(email)
            result.onSuccess {
                val intent = Intent(this@LoginActivity, TwoFactorActivity::class.java)
                intent.putExtra("email", email)
                startActivity(intent)
                finish()
            }.onFailure { error ->
                loginBtn.isEnabled = true
                loginBtn.text = "Sign In"
                Toast.makeText(this@LoginActivity, "Could not send OTP: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
