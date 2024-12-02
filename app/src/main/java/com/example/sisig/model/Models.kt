package com.example.sisig.model

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val success: Boolean,
    val message: String
)

data class User(
    val id: Int,
    val username: String
)

data class ResetPasswordRequest(
    val username: String,
    val newPassword: String
)

data class ResetPasswordResponse(
    val success: Boolean,
    val message: String
)

data class InventoryItem(
    val id: Int,
    val name: String,
    val quantity: Int,
    val price: Double
)