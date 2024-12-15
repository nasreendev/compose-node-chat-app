package com.example.chatappwithnodejs.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatappwithnodejs.domain.Users
import com.example.chatappwithnodejs.repository.ChatsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddUserViewModel(
    private val chatsRepository: ChatsRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(AddUserState())
    val state = _state.asStateFlow()

    fun onEvent(event: AddUserEvent) {
        when (event) {
            is AddUserEvent.AddUser -> {
                val username = event.userName
                viewModelScope.launch {
                    addUser(username)
                }
            }
        }
    }

    private suspend fun addUser(username: String) {
        val users = Users(username = username)
        chatsRepository.addUser(users).collect { status ->
            if (status == 201) {
                _state.value = state.value.copy(addedUser = true)
            } else {
                _state.value = state.value.copy(addedUser = false)
            }
        }
    }
}