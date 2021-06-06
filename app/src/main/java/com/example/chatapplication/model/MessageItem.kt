package com.example.chatapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "Messages")
class MessageItem(val content:String, val messageType:Int, val timestamp: Long, val readStatus: Boolean){
    companion object {
        const val TYPE_MY_MESSAGE = 0
        const val TYPE_FRIEND_MESSAGE = 1
        const val TYPE_HEADER = 2
    }
    @PrimaryKey(autoGenerate = true)
    var id :Int? = null
}