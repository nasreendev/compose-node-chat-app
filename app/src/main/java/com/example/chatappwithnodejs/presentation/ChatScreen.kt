package com.example.chatappwithnodejs.presentation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatappwithnodejs.domain.Message
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ChatScreen(
    senderId: String,
    states: ChatStates,
    receiverId: String,
    events: (ChatsEvents) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        UserAppBar("Chats")
        Spacer(Modifier.height(10.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            reverseLayout = false
        ) {
            items(states.messageList) { message ->
                MessageBox(
                    message = message
                )
            }
        }
        MessageInputField { message ->
            val message = Message(
                message = message,
                senderId = senderId,
                receiverId = receiverId
            )
            events(ChatsEvents.SendMessage(message))
        }
    }
}

@Composable
fun MessageInputField(
    onSend: (String) -> Unit,
) {
    val message = remember { mutableStateOf("") }
    val containerColor = Color(0xFF2B2B2B)
    TextField(
        value = message.value,
        onValueChange = {
            message.value = it
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 9.dp)
            .navigationBarsPadding()
            .imePadding(),
        textStyle = TextStyle(
            color = Color(0xFFCCCCCC),
            fontSize = 16.sp
        ),
        placeholder = {
            Text(
                text = "Type a message...",
                style = TextStyle(
                    color = Color(0xFFCCCCCC),
                    fontSize = 16.sp
                )
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    onSend(message.value)
                    message.value = ""
                },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Color(0xFFCCCCCC)
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = null
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        shape = RoundedCornerShape(50),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@SuppressLint("NewApi")
@Composable
fun MessageBox(
    message: Message,
) {
    val modifier = if ("675ed4c4664c1f0665ab8eb2" == message.senderId) {
        Modifier
            .padding(start = 16.dp, end = 8.dp)
            .defaultMinSize(minHeight = 60.dp)
            .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp, bottomStart = 20.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF007EF4),
                        Color(0xFF2A75BC)
                    )
                )
            )
    } else {
        Modifier
            .padding(start = 8.dp, end = 16.dp)
            .defaultMinSize(minHeight = 60.dp)
            .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp, bottomStart = 20.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF454545),
                        Color(0xFF2B2B2B)
                    )
                )
            )
    }
    val boxArrangement =
        if ("675ed4c4664c1f0665ab8eb2" == message.senderId) Alignment.CenterEnd else Alignment.CenterStart


    Box(
        modifier = Modifier
            .padding(vertical = 12.dp)
            .fillMaxWidth(), contentAlignment = boxArrangement
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Box(modifier = modifier) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = message.message,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    )
                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = formatTimestampToHHMM(message.timestamp!!),
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserAppBar(
    title: String,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 25.sp
                ),
                fontWeight = FontWeight.Bold
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatTimestampToHHMM(timestamp: String): String {
    val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    val zoneDateTime = ZonedDateTime.parse(timestamp, formatter)
    val zoneId = ZoneId.of("Asia/Karachi")
    val isDateTime = zoneDateTime.withZoneSameInstant(zoneId)
    val hours = isDateTime.hour.toString().padStart(2, '0')
    val minutes = isDateTime.minute.toString().padStart(2, '0')
    return "$hours:$minutes"
}