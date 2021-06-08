package com.example.chatapplication.view

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapplication.R
import com.example.chatapplication.database.ChatRepository
import com.example.chatapplication.model.MessageItem
import com.example.chatapplication.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.main_activity.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private var chatAdapter =
            ChatAdapter(this, mutableListOf())
    private lateinit var chatList: RecyclerView
    private lateinit var btnSend: ImageButton
    private lateinit var message: EditText
    private lateinit var repository: ChatRepository
    private lateinit var toolbar: Toolbar
    private lateinit var btnBack: ImageButton
    private lateinit var replyButton: Button
    private var messageAnimator = MessageAnimator()
    private val handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        toolbar = findViewById(R.id.toolbar)
        toolbar.title = ""
        setSupportActionBar(toolbar)
        initView()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        repository = ChatRepository(this)
        getPreviousChatHistory()
    }

    private fun getPreviousChatHistory() {
        viewModel.getAllMessages(repository).observe(this, Observer {
            if (it != null) {
                runOnUiThread {
                    chatAdapter.addChatHistory(it)
                    chatList.scrollToPosition(chatAdapter.itemCount - 1)
                }
            }
        })
    }


    private fun initView() {
        btnBack = findViewById(R.id.back_button)
        back_button.setOnClickListener {
            onBackPressed()
        }
        chatList = findViewById(R.id.messageList)
        chatList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    .apply {
                        stackFromEnd = true
                        isSmoothScrollbarEnabled = true
                    }
            adapter = chatAdapter
        }
        chatList.itemAnimator = messageAnimator

        btnSend = findViewById(R.id.btnSend)
        message = findViewById(R.id.txtMessage)
        btnSend.setOnClickListener {
            onSendClicked(message.text.toString(), it)
        }
        replyButton = findViewById(R.id.reply_button)
        replyButton.setOnClickListener {
            viewModel.sendMessage(repository, getRandomReply())
        }
    }

    private fun onSendClicked(msg: String, view: View) {
        if (msg.isNotEmpty()) {
            viewModel.sendMessage(repository, MessageItem(msg, MessageItem.TYPE_MY_MESSAGE, System.currentTimeMillis(), true))
            message.text.clear()
        }
    }


    private fun getRandomReply(): MessageItem {
        val randomReplies = resources.getStringArray(R.array.random_replies)
        val rand = Random()
        val n: Int = rand.nextInt(randomReplies.size - 1)
        return MessageItem(randomReplies[n], MessageItem.TYPE_FRIEND_MESSAGE, System.currentTimeMillis(), true)
    }
}