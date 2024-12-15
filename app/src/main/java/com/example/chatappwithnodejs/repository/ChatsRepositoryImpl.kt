package com.example.chatappwithnodejs.repository

import android.util.Log
import com.example.chatappwithnodejs.data.ChatApi
import com.example.chatappwithnodejs.domain.Message
import com.example.chatappwithnodejs.domain.Users
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChatsRepositoryImpl(
    val chatApi: ChatApi,
) : ChatsRepository {
    override suspend fun addUser(users: Users): Flow<Int> = flow {
        emit(chatApi.addUsers(users).code())
    }

    override suspend fun fetchUsers(): Flow<List<Users>> = flow {
        emit(chatApi.fetchUsers().body()!!)
    }

    override suspend fun sendMessage(message: Message) {
        chatApi.sendMessage(message)
    }

    override suspend fun getMessages(chatRoomId: String): Flow<List<Message>> = flow {
        try {
            emit(chatApi.getMessages(chatRoomId).body()!!)
        } catch (e: Exception) {
            Log.d("Exception", e.message.toString())
        }
    }
}