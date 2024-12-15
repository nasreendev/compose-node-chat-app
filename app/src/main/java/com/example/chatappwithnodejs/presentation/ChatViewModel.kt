package com.example.chatappwithnodejs.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatappwithnodejs.domain.Message
import com.example.chatappwithnodejs.repository.ChatsRepository
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import io.socket.client.Socket
import kotlinx.coroutines.flow.MutableStateFlow
import io.socket.client.IO

import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class ChatViewModel(
    private val chatsRepository: ChatsRepository,
) : ViewModel() {

    private var socket: Socket = IO.socket("http://192.168.1.8:3000/")

    private val _state = MutableStateFlow(ChatStates())
    val state = _state.asStateFlow()

    init {
        socket.connect()
        socket.on("newMessage") {
            it?.let { arrayData ->
                val messageJson = arrayData[0]
                try {
                    val jsonObject = JSONObject(messageJson.toString())
                    val messageObject = jsonObject.getJSONObject("message")
                    val messageText = messageObject.getString("message")

                    val message = Message(
                        message = messageText,
                        senderId = jsonObject.getString("senderId"),
                        receiverId = jsonObject.getString("receiverId"),
                        timestamp = messageObject.optString("timestamp")
                    )
                    val newMessageList = state.value.messageList.toMutableList()
                    newMessageList.add(message)
                    _state.value = state.value.copy(messageList = newMessageList)

                } catch (e: Exception) {
                    println("Failed to parse message JSON: ${e.message}")
                }
            }
        }
    }


    fun onEvent(events: ChatsEvents) {
        when (events) {
            is ChatsEvents.SendMessage -> {
                viewModelScope.launch {
                    chatsRepository.sendMessage(events.message)
                }
            }
        }
    }

    fun getMessages(chatRoomId: String) {
        viewModelScope.launch {
            chatsRepository.getMessages(chatRoomId).collect {
                _state.value = state.value.copy(messageList = it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        socket.disconnect()
        socket.off()
    }
}