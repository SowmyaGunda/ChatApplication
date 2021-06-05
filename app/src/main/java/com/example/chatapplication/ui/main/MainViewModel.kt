package com.example.chatapplication.ui.main


import androidx.lifecycle.ViewModel
import com.example.chatapplication.database.ChatRepository
import com.example.chatapplication.model.MessageItem

class MainViewModel() : ViewModel() {


    var messages = ArrayList<MessageItem>()

    fun sendMessage(repository: ChatRepository, messageItem: MessageItem) = repository.insertData(messageItem)
    fun getAllMessages(repository: ChatRepository) = repository.getAllMessages()
}