package com.example.chatapplication

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapplication.database.ChatRepository
import com.example.chatapplication.model.MessageItem
import com.example.chatapplication.ui.adapter.ChatAdapter
import com.example.chatapplication.ui.main.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private var chatAdapter = ChatAdapter(mutableListOf())
    private lateinit var chatList: RecyclerView
    private lateinit var btnSend: ImageButton
    private lateinit var message: EditText
    private lateinit var repository: ChatRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        initView()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        chatAdapter.data = viewModel.messages
        repository = ChatRepository(this)
        getPreviousChatHistory()
    }

    private fun getPreviousChatHistory(){
        viewModel.getAllMessages(repository).observe(this, Observer {
            if(it != null) {
                chatAdapter.addChatHistory(it)
            }
        })
    }


    private fun initView() {
        chatList = findViewById(R.id.messageList)
        chatList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    .apply {
                        stackFromEnd = true
                        isSmoothScrollbarEnabled = true
                    }
            adapter = chatAdapter
        }

        btnSend = findViewById(R.id.btnSend)
        message = findViewById(R.id.txtMessage)
        btnSend.setOnClickListener {
            onSendClicked(message.text.toString(),it)
        }
    }

    private fun onSendClicked(msg: String, view: View) {
        if (msg.isNotEmpty()) {
            viewModel.sendMessage(repository,MessageItem(msg, MessageItem.TYPE_MY_MESSAGE, System.currentTimeMillis(),true))
            message.text.clear()
        }
        hideKeyboardFrom(this, view)
    }

    private fun hideKeyboardFrom(context: Context?, view: View) {
        val imm: InputMethodManager =
                context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}