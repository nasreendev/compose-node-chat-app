package com.example.chatappwithnodejs.presentation

sealed class AddUserEvent {
    data class AddUser(val userName: String) : AddUserEvent()
}