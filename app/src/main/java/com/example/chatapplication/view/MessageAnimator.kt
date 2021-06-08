package com.example.chatapplication.view

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator


class MessageAnimator : SimpleItemAnimator() {
    override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun animateMove(holder: RecyclerView.ViewHolder, fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
        return false
    }

    override fun animateChange(oldHolder: RecyclerView.ViewHolder, newHolder: RecyclerView.ViewHolder, fromLeft: Int, fromTop: Int, toLeft: Int, toTop: Int): Boolean {
        return false
    }

    override fun runPendingAnimations() {}
    override fun endAnimation(item: RecyclerView.ViewHolder) {}
    override fun endAnimations() {}
    override fun isRunning(): Boolean {
        return false
    }
}