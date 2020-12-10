package com.example.ibuild.data_classes

data class User(
    val uid: String,
    val email: String,
    val name: String,
    val surname: String,
) {
    constructor() : this("", "", "", "")
}