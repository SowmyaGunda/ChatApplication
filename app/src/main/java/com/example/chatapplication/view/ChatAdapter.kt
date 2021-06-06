package com.example.chatapplication.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapplication.R
import com.example.chatapplication.model.MessageItem
import com.example.chatapplication.model.MessageItem.Companion.TYPE_FRIEND_MESSAGE
import com.example.chatapplication.model.MessageItem.Companion.TYPE_HEADER
import com.example.chatapplication.model.MessageItem.Companion.TYPE_MY_MESSAGE
import com.example.chatapplication.utils.Utils

class ChatAdapter(var data: MutableList<MessageItem>) : RecyclerView.Adapter<MessageViewHolder<*>>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder<*> {
        val context = parent.context
        return when (viewType) {
            TYPE_MY_MESSAGE -> {
                val view = LayoutInflater.from(context).inflate(R.layout.my_message_item, parent, false)
                MyMessageViewHolder(
                    view
                )
            }
            TYPE_FRIEND_MESSAGE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.friend_message_item, parent, false)
                FriendMessageViewHolder(
                    view
                )
            }
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.time_stamp_item, parent, false)
                HeaderViewHolder(
                    view
                )
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder<*>, position: Int) {
        val item = data[position]
        when (holder) {
            is MyMessageViewHolder -> holder.bind(item)
            is FriendMessageViewHolder -> holder.bind(item)
            is HeaderViewHolder -> holder.bind(item)
            else -> throw IllegalArgumentException()
        }
    }

    fun addChatHistory(messageList: List<MessageItem>) {
        this.data = messageList.toMutableList()
        addHeadersToList()
        notifyDataSetChanged()
    }
    private fun addHeadersToList(){
        for(item in data){
            if(Utils.isMessageReceivedInLastHour(item.timestamp)){
                data.add(data.indexOf(item), MessageItem(Utils.getTimeStampString(), TYPE_HEADER,0,false))
                break;
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int = data[position].messageType

    class MyMessageViewHolder(val view: View) : MessageViewHolder<MessageItem>(view) {
        private val messageContent = view.findViewById<TextView>(R.id.message)

        override fun bind(item: MessageItem) {
            messageContent.text = item.content
        }
    }

    class FriendMessageViewHolder(val view: View) : MessageViewHolder<MessageItem>(view) {
        private val messageContent = view.findViewById<TextView>(R.id.message)

        override fun bind(item: MessageItem) {
            messageContent.text = item.content
        }

    }
    class HeaderViewHolder(val view: View) : MessageViewHolder<MessageItem>(view) {
        private val timeStamp = view.findViewById<TextView>(R.id.time_stamp)

        override fun bind(item: MessageItem) {
            timeStamp.text = item.content
        }

    }
}