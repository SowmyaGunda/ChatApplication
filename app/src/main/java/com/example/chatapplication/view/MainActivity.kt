package com.example.chatapplication.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
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
        ChatAdapter(mutableListOf())
    private lateinit var chatList: RecyclerView
    private lateinit var btnSend: ImageButton
    private lateinit var message: EditText
    private lateinit var repository: ChatRepository
    private lateinit var toolbar: Toolbar
    private lateinit var btnBack: ImageButton
    private var isSendClicked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        toolbar = findViewById(R.id.toolbar)
        toolbar.setTitle("")
        setSupportActionBar(toolbar)
        initView()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        repository = ChatRepository(this)
        getPreviousChatHistory()
    }

    private fun getPreviousChatHistory(){
        viewModel.getAllMessages(repository).observe(this, Observer {
            if(it != null) {
                runOnUiThread {
                    chatAdapter.addChatHistory(it)
                    chatList.scrollToPosition(chatAdapter.itemCount -1)
                }
            }
        })
    }


    private fun initView() {
        btnBack = findViewById(R.id.back_button)
        back_button.setOnClickListener{
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

        btnSend = findViewById(R.id.btnSend)
        message = findViewById(R.id.txtMessage)
        btnSend.setOnClickListener {
            onSendClicked(message.text.toString(),it)
        }
    }

    private fun onSendClicked(msg: String, view: View) {
        isSendClicked = true;
        if (msg.isNotEmpty()) {
            viewModel.sendMessage(repository,MessageItem(msg, MessageItem.TYPE_MY_MESSAGE, System.currentTimeMillis(),true))
            message.text.clear()
        }
        hideKeyboardFrom(this, view)
        viewModel.sendMessage(repository,getRandomReply())
    }

    private fun hideKeyboardFrom(context: Context?, view: View) {
        val imm: InputMethodManager =
                context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun getRandomReply() : MessageItem{
        val random_replies = resources.getStringArray(R.array.random_replies)
        val rand = Random()
        val n: Int = rand.nextInt(random_replies.size - 1)
        return MessageItem(random_replies[n],MessageItem.TYPE_FRIEND_MESSAGE,System.currentTimeMillis(),true)
    }

}