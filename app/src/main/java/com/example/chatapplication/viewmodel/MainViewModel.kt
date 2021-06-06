package com.example.chatapplication.viewmodel


import androidx.lifecycle.ViewModel
import com.example.chatapplication.database.ChatRepository
import com.example.chatapplication.model.MessageItem

class MainViewModel() : ViewModel() {

    fun sendMessage(repository: ChatRepository, messageItem: MessageItem) = repository.insertData(messageItem)
    fun getAllMessages(repository: ChatRepository) = repository.getAllMessages()
}