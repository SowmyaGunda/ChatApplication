package com.example.chatapplication.database


import android.content.Context
import androidx.lifecycle.LiveData
import com.example.chatapplication.model.MessageItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ChatRepository(context: Context) {


    var messageDatabase: MessageDatabase = initializeDB(context)

    fun initializeDB(context: Context): MessageDatabase {
        return MessageDatabase.getDatabaseClient(context)
    }

    fun insertData(messageItem: MessageItem) {
        CoroutineScope(IO).launch {
            messageDatabase.chatDao().insert(messageItem)
        }
    }

    fun getAllMessages(): LiveData<List<MessageItem>> {
        return messageDatabase.chatDao().allMessages
    }


}
