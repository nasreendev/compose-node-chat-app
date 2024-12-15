package com.example.chatappwithnodejs.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatappwithnodejs.repository.ChatsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsersViewModel(
    private val chatsRepository: ChatsRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(UsersState())
    val state = _state.asStateFlow()

    fun onEvent(event: UsersEvent) {
        when (event) {
            UsersEvent.FetchUsersList -> {
                viewModelScope.launch {
                    chatsRepository.fetchUsers().collect { usersList ->
                        _state.value=state.value.copy(usersList=usersList)
                    }
                }
            }
        }
    }
}