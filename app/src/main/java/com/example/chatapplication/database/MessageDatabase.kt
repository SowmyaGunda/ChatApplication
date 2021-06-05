package com.example.chatapplication.database

import android.content.Context
import androidx.room.*
import com.example.chatapplication.model.MessageItem

@Database(entities = arrayOf(MessageItem::class), version = 1, exportSchema = false)
abstract class MessageDatabase : RoomDatabase() {

    abstract fun chatDao() : ChatDao

    companion object {

        @Volatile
        private var INSTANCE: MessageDatabase? = null

        fun getDatabaseClient(context: Context) : MessageDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, MessageDatabase::class.java, "MESSAGE_DATABASE")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!

            }
        }

    }

}