package com.books.app.models

data class Book(
    val id: Int,
    val name: String,
    val author: String,
    val summary: String,
    val genre: String,
    val cover_url: String,
    val views: String,
    val likes: String,
    val quotes: String
)
