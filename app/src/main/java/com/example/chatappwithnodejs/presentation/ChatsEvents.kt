package com.example.chatappwithnodejs.presentation

import com.example.chatappwithnodejs.domain.Message

sealed class ChatsEvents {
    data class SendMessage(val message: Message) : ChatsEvents()
}