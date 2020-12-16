package com.example.ibuild.data_classes

data class Category (
    val id: Int,
    val title: String
) {
    constructor() : this(0, "")
}