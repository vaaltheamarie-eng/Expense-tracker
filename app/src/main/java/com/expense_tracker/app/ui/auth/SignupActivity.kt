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

class SignupActivity : AppCompatActivity() {
    private lateinit var authManager: AuthManager
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    private lateinit var signupBtn: Button
    private lateinit var loginLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        authManager = AuthManager(this)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput)
        signupBtn = findViewById(R.id.signupBtn)
        loginLink = findViewById(R.id.loginLink)

        signupBtn.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val confirmPassword = confirmPasswordInput.text.toString().trim()

            when {
                email.isEmpty() -> emailInput.error = "Email is required"
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> emailInput.error = "Please enter a valid email"
                password.isEmpty() -> passwordInput.error = "Password is required"
                password.length < 8 -> passwordInput.error = "Password must be at least 8 characters"
                password != confirmPassword -> confirmPasswordInput.error = "Passwords do not match"
                else -> createUser(email, password)
            }
        }

        loginLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun createUser(email: String, password: String) {
        signupBtn.isEnabled = false
        signupBtn.text = "Creating account..."

        lifecycleScope.launch {
            val result = authManager.signup(email, password)
            result.onSuccess {
                sendOtp(email)
            }.onFailure { error ->
                signupBtn.isEnabled = true
                signupBtn.text = "Create Account"
                Toast.makeText(this@SignupActivity, "Signup failed: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendOtp(email: String) {
        lifecycleScope.launch {
            val result = authManager.sendOtpToEmail(email)
            result.onSuccess {
                val intent = Intent(this@SignupActivity, TwoFactorActivity::class.java)
                intent.putExtra("email", email)
                startActivity(intent)
                finish()
            }.onFailure { error ->
                signupBtn.isEnabled = true
                signupBtn.text = "Create Account"
                Toast.makeText(this@SignupActivity, "Could not send OTP: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
