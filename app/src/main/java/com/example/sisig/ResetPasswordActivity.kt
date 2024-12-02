package com.example.sisig

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.sisig.model.ResetPasswordRequest
import com.example.sisig.network.ApiService
import com.example.sisig.network.RetrofitClient
import kotlinx.coroutines.launch

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextNewPassword: EditText
    private lateinit var buttonReset: Button

    private val apiService: ApiService = RetrofitClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        supportActionBar?.hide()

        editTextUsername = findViewById(R.id.editTextUsername)
        editTextNewPassword = findViewById(R.id.editTextNewPassword)
        buttonReset = findViewById(R.id.buttonResetPassword)

        buttonReset.setOnClickListener { handleResetPassword() }
    }

    private fun handleResetPassword() {
        val username = editTextUsername.text.toString().trim()
        val newPassword = editTextNewPassword.text.toString().trim()

        if (username.isEmpty() || newPassword.isEmpty()) {
            Toast.makeText(this, "Please enter username and new password", Toast.LENGTH_SHORT).show()
            return
        }

        val resetRequest = ResetPasswordRequest(username, newPassword)

        lifecycleScope.launch {
            try {
                val response = apiService.resetPassword(resetRequest)
                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()!!
                    if (responseBody.success) {
                        Toast.makeText(this@ResetPasswordActivity, "Password reset successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@ResetPasswordActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@ResetPasswordActivity, responseBody.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@ResetPasswordActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ResetPasswordActivity, "Password reset failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}