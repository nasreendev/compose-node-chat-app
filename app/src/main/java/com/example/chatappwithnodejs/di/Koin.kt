package com.example.chatappwithnodejs.di

import com.example.chatappwithnodejs.data.ChatApi
import com.example.chatappwithnodejs.presentation.AddUserViewModel
import com.example.chatappwithnodejs.presentation.ChatViewModel
import com.example.chatappwithnodejs.presentation.UsersViewModel
import com.example.chatappwithnodejs.repository.ChatsRepository
import com.example.chatappwithnodejs.repository.ChatsRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun provideChatApi(): ChatApi {
    return Retrofit.Builder()
        .baseUrl("http://192.168.1.8:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ChatApi::class.java)
}

fun provideChatsRepository(chatApi: ChatApi): ChatsRepository {
    return ChatsRepositoryImpl(chatApi)
}

val appModule = module {
    single {
        provideChatApi()
    }

    single {
        provideChatsRepository(get())
    }
    viewModel {
        AddUserViewModel(get())
    }

    viewModel {
        ChatViewModel(get())
    }
    viewModel {
        UsersViewModel(get())
    }
}