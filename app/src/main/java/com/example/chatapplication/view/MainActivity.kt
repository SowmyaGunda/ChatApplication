package com.example.chatapplication.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.OvershootInterpolator
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
       // handleSendButtonClick(message, view)
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

    private fun handleSendButtonClick(composeMessage: EditText, view: View) {
        this.handler.postDelayed({
            val viewHolder: RecyclerView.ViewHolder? = chatList.findViewHolderForAdapterPosition(chatAdapter.itemCount - 1)
            if (viewHolder is ChatAdapter.MyMessageViewHolder) {
                val outgoingReplyViewHolder: ChatAdapter.MyMessageViewHolder = viewHolder
                val outgoingMessageView: View = outgoingReplyViewHolder.messageContent
                val composeMessagePos = IntArray(2)
                composeMessage.getLocationInWindow(composeMessagePos)
                val startX = composeMessagePos[0]
                val startY = composeMessagePos[1]
                val newMessagePos = IntArray(2)
                outgoingMessageView.getLocationInWindow(newMessagePos)
                val x = newMessagePos[0] - startX
                val y = newMessagePos[1] - startY
                val flySet = AnimatorSet()
                val flyX: Animator = ObjectAnimator.ofFloat(outgoingMessageView, View.TRANSLATION_X, x.toFloat())
                val flyY: Animator = ObjectAnimator.ofFloat(outgoingMessageView, View.TRANSLATION_Y, y.toFloat())
                flySet.playTogether(flyX, flyY)
                flySet.duration = 250
                flySet.interpolator = OvershootInterpolator(1.0f)
                flySet.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        outgoingReplyViewHolder.itemView.visibility = View.VISIBLE
                    }
                })
                flySet.start()
            }
        }, messageAnimator.addDuration)
    }

}