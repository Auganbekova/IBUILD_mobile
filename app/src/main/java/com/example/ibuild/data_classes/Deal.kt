package com.example.ibuild.data_classes

data class Deal (
    val ownerId: String,
    val workerId: String,
    val workId: String,
    val position: String,
    val place: String,
    val startDate: String,
    val endDate: String
) {
    constructor() : this("", "", "", "", "", "", "")
}