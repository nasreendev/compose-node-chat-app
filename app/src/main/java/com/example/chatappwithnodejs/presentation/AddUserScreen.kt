package com.example.chatappwithnodejs.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chatappwithnodejs.navigation.Screen
import com.example.chatappwithnodejs.navigation.localNavHostController
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddUserScreen(
    addUserViewModel: AddUserViewModel = koinViewModel()
) {

    val state = addUserViewModel.state.collectAsState().value
    val navController = localNavHostController.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var username by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    navController.navigate(Screen.USERS_SCREEN.route)
                }
            ) {
                Text("Just Go")
            }
            Spacer(modifier = Modifier.width(12.dp))
            Button(
                onClick = {
                    addUserViewModel.onEvent(AddUserEvent.AddUser(username))
                }
            ) {
                Text("Add User")
            }
            LaunchedEffect(state) {
                if (state.addedUser) {
                    Log.d("AddUser", "User Added!")
                    navController.navigate(Screen.USERS_SCREEN.route)
                }
            }
        }
    }
}