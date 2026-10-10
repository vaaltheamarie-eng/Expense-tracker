package com.expense_tracker.app.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.expense_tracker.app.R
import com.expense_tracker.app.data.AuthManager
import com.expense_tracker.app.ui.auth.LoginActivity
import com.expense_tracker.app.ui.dashboard.DashboardActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        authManager = AuthManager(this)

        Handler(Looper.getMainLooper()).postDelayed({
            if (authManager.isLoggedIn()) {
                startActivity(Intent(this, DashboardActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 2000)
    }
}
