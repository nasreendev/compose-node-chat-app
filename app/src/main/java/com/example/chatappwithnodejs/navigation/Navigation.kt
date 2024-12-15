package com.example.chatappwithnodejs.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatappwithnodejs.presentation.AddUserScreen
import com.example.chatappwithnodejs.presentation.ChatScreen
import com.example.chatappwithnodejs.presentation.ChatViewModel
import com.example.chatappwithnodejs.presentation.UserScreen
import com.example.chatappwithnodejs.presentation.UsersViewModel
import org.koin.androidx.compose.koinViewModel

val localNavHostController = compositionLocalOf<NavHostController> {
    error("NavController is not provided!")
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    CompositionLocalProvider(localNavHostController provides navController) {
        NavHost(navController = navController, startDestination = Screen.ADD_USER_SCREEN.route) {
            composable(Screen.ADD_USER_SCREEN.route) {
                AddUserScreen()
            }
            composable(Screen.USERS_SCREEN.route) {
                val viewModel: UsersViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                UserScreen(
                    event = viewModel::onEvent,
                    state = state
                ) { receiverId ->
                    navController.navigate(
                        Screen.CHAT_SCREEN.createRoute(
                            receiverId = receiverId,
                            senderId = "675ed4c4664c1f0665ab8eb2"
                        )
                    )
                }
            }
            composable("chat/{senderId}/{receiverId}") {
                val senderId = it.arguments?.getString("senderId") ?: ""
                val receiverId = it.arguments?.getString("receiverId") ?: ""
                val viewModel: ChatViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()

                LaunchedEffect(Unit) {
                    viewModel.getMessages(createChatRoomId(senderId, receiverId))
                }
                ChatScreen(
                    senderId = senderId,
                    receiverId = receiverId,
                    events = viewModel::onEvent,
                    states = state
                )
            }
        }
    }
}

fun createChatRoomId(id1: String, id2: String): String {
    val ids = listOf(id1, id2).sorted()
    return ids.joinToString(separator = "_")
}

enum class Screen(val route: String) {
    ADD_USER_SCREEN("add_user"),
    USERS_SCREEN("users_screen"),
    CHAT_SCREEN("chat/{senderId}/{receiverId}")
}
fun Screen.createRoute(senderId: String, receiverId: String): String {
    return when (this) {
        Screen.CHAT_SCREEN -> "chat/$senderId/$receiverId"
        else -> route
    }
}
