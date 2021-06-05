package com.example.chatapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.chatapplication.model.MessageItem


@Dao
interface ChatDao {
    @Insert
    fun insert(messageEntity: MessageItem)

    @get:Query("SELECT * FROM Messages")
    val allMessages: LiveData<List<MessageItem>>
}