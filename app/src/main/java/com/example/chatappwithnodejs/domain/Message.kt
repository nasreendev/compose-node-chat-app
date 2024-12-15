package com.example.chatappwithnodejs.domain

data class Message(
    val message: String,
    val senderId: String,
    val receiverId:String,
    val timestamp: String?=null
)


