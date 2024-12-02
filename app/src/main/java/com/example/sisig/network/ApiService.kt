package com.example.sisig.network

import com.example.sisig.model.InventoryItem
import com.example.sisig.model.LoginRequest
import com.example.sisig.model.LoginResponse
import com.example.sisig.model.ResetPasswordRequest
import com.example.sisig.model.ResetPasswordResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/reset-password")
    suspend fun resetPassword(@Body resetRequest: ResetPasswordRequest): Response<ResetPasswordResponse>

    @GET("/inventory")
    suspend fun getInventory(): Response<List<InventoryItem>>

    @POST("/inventory")
    suspend fun addInventory(@Body item: InventoryItem): Response<Map<String, String>>

    @PUT("/inventory/{id}")
    suspend fun updateInventory(@Path("id") id: Int, @Body item: InventoryItem): Response<Map<String, String>>
}