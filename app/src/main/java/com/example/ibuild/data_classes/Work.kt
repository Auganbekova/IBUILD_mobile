package com.example.ibuild.data_classes

data class Work (
    val userId: String,
    val title: String,
    val experience: String,
    val selfInfo: String,
    val price: String,
    val category: String,
    val finished: Boolean
) {
  constructor() : this("", "", "", "", "", "", false)
}