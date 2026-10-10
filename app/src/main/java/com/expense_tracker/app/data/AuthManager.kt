package com.expense_tracker.app.data

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.FirebaseFunctions
import kotlinx.coroutines.tasks.await

class AuthManager(private val context: Context) {
    private val auth = FirebaseAuth.getInstance()
    private val functions = FirebaseFunctions.getInstance()

    fun isLoggedIn(): Boolean = auth.currentUser != null

    fun getCurrentUser() = auth.currentUser

    suspend fun login(email: String, password: String): Result<String> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success("Login successful")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signup(email: String, password: String): Result<String> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Result.success("Account created")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendOtpToEmail(email: String): Result<String> {
        return try {
            val data = hashMapOf("email" to email)
            val result = functions.getHttpsCallable("generateAndSendOTP").call(data).await()
            val payload = result.data as? Map<*, *> ?: emptyMap<Any, Any>()
            val message = payload["message"] as? String ?: "OTP sent successfully"
            Result.success(message)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun verifyOtp(email: String, otp: String): Result<String> {
        return try {
            val data = hashMapOf(
                "email" to email,
                "otp" to otp
            )
            val result = functions.getHttpsCallable("verifyOTP").call(data).await()
            val payload = result.data as? Map<*, *> ?: emptyMap<Any, Any>()
            val message = payload["message"] as? String ?: "OTP verified"
            Result.success(message)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        auth.signOut()
    }
}
