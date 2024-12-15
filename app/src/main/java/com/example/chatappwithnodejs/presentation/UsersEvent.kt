package com.example.chatappwithnodejs.presentation

sealed class UsersEvent {
    data object FetchUsersList : UsersEvent()
}