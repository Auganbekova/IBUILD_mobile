package com.example.ibuild.data_classes

import java.util.*

data class Chat(
    val id: String,
    val participantIds: List<String>,
    val participants: List<User>,
    val lastMessage: String,
    val lastMessageTimestamp: String
) {
    constructor(): this("", emptyList(), emptyList(), "", Date().toString())
}