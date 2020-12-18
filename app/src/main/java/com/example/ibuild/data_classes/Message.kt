package com.example.ibuild.data_classes

import com.google.firebase.Timestamp
import java.util.*

data class Message(
    val chatTimeId: String,
    val chatId: String,
    val message: String,
    val senderId: String,
    val timestamp: Date
) {
    constructor(): this("","","", "", Date())
}