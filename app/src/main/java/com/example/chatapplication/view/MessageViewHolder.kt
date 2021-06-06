package com.example.chatapplication.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class MessageViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}