package com.example.chatapplication.utils

import android.text.format.DateFormat

class Utils {
    companion object {
        var headerTime: Long = System.currentTimeMillis() - (60 * 60 * 1000)

        fun isMessageReceivedInLastHour(time: Long) = time > headerTime

        fun getTimeStampString(): String = convertDate(headerTime, "EEEE hh:mm")

        fun isMsgReceivedOrSent20SecOlder(time: Long) = (System.currentTimeMillis() - time) <= (20*1000)

        private fun convertDate(dateInMilliseconds: Long, dateFormat: String): String =
            DateFormat.format(dateFormat, dateInMilliseconds).toString()
    }
}