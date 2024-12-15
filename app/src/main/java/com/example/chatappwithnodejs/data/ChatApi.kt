package com.example.chatappwithnodejs.data

import com.example.chatappwithnodejs.domain.Message
import com.example.chatappwithnodejs.domain.Users
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatApi {
    @POST("addUser")
    suspend fun addUsers(@Body users: Users): Response<Users>

    @GET("users")
    suspend fun fetchUsers(): Response<List<Users>>

    @POST("send-message")
    suspend fun sendMessage(@Body message: Message): Response<Message>

    @GET("messages/{chatRoomId}")
    suspend fun getMessages(
        @Path("chatRoomId") chatRoomId: String,
    ): Response<List<Message>>
}