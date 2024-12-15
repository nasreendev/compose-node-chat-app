package com.example.chatappwithnodejs.repository

import com.example.chatappwithnodejs.domain.Message
import com.example.chatappwithnodejs.domain.Users
import kotlinx.coroutines.flow.Flow

interface ChatsRepository {
    suspend fun addUser(users: Users): Flow<Int>
    suspend fun fetchUsers(): Flow<List<Users>>
    suspend fun sendMessage(message: Message)
    suspend fun getMessages(chatRoomId: String): Flow<List<Message>>
}