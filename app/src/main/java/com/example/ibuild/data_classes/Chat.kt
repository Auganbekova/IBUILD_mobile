package com.example.ibuild.data_classes

data class Chat(
    val id: String,
    val participantIds: List<String>,
    val participants: List<User>,
    val lastMessage: String,
    val lastMessageTimestamp: java.util.Date
) {
    constructor(): this("", emptyList(), emptyList(), "", java.util.Date())
}