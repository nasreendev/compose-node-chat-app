package com.example.chatappwithnodejs.presentation

import com.example.chatappwithnodejs.domain.Message

data class ChatStates(
    val messageList: List<Message> = emptyList(),
)
