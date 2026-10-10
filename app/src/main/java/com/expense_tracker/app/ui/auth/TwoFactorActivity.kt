package com.expense_tracker.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.expense_tracker.app.R
import com.expense_tracker.app.data.AuthManager
import com.expense_tracker.app.ui.dashboard.DashboardActivity
import kotlinx.coroutines.launch

class TwoFactorActivity : AppCompatActivity() {

    private lateinit var authManager: AuthManager

    private lateinit var emailText: TextView
    private lateinit var otpInput: EditText
    private lateinit var verifyBtn: Button
    private lateinit var resendBtn: Button

    private var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_fa)

        authManager = AuthManager(this)
        email = intent.getStringExtra("email") ?: ""

        emailText = findViewById(R.id.emailText)
        otpInput = findViewById(R.id.otpInput)
        verifyBtn = findViewById(R.id.verifyBtn)
        resendBtn = findViewById(R.id.resendBtn)

        emailText.text = "Code sent to: $email"

        verifyBtn.setOnClickListener {
            val otp = otpInput.text.toString().trim()
            if (otp.length != 6) {
                otpInput.error = "Enter a valid 6-digit code"
            } else {
                verifyOtp(email, otp)
            }
        }

        resendBtn.setOnClickListener {
            resendOtp()
        }
    }

    private fun verifyOtp(email: String, otp: String) {
        verifyBtn.isEnabled = false
        verifyBtn.text = "Verifying..."

        lifecycleScope.launch {
            val result = authManager.verifyOtp(email, otp)
            result.onSuccess {
                val intent = Intent(this@TwoFactorActivity, DashboardActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }.onFailure { error ->
                verifyBtn.isEnabled = true
                verifyBtn.text = "Verify OTP"
                Toast.makeText(this@TwoFactorActivity, "Invalid OTP: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun resendOtp() {
        lifecycleScope.launch {
            val result = authManager.sendOtpToEmail(email)
            result.onSuccess {
                Toast.makeText(this@TwoFactorActivity, "A new OTP has been sent.", Toast.LENGTH_SHORT).show()
            }.onFailure { error ->
                Toast.makeText(this@TwoFactorActivity, "Unable to resend OTP: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
