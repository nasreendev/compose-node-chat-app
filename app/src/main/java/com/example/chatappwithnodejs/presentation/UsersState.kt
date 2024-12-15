package com.example.chatappwithnodejs.presentation

import com.example.chatappwithnodejs.domain.Users

data class UsersState(
    val usersList: List<Users> = emptyList()
)
