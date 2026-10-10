package com.expense_tracker.app.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.expense_tracker.app.R
import com.expense_tracker.app.data.AuthManager
import com.expense_tracker.app.ui.auth.LoginActivity

class DashboardActivity : AppCompatActivity() {
    private lateinit var authManager: AuthManager
    private lateinit var userEmailText: TextView
    private lateinit var logoutBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        authManager = AuthManager(this)
        userEmailText = findViewById(R.id.userEmail)
        logoutBtn = findViewById(R.id.logoutBtn)

        val user = authManager.getCurrentUser()
        userEmailText.text = "Welcome, ${user?.email ?: "User"}"

        logoutBtn.setOnClickListener {
            authManager.logout()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
