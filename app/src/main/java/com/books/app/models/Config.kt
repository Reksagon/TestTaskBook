package com.books.app.models

data class Config(
    val books: List<Book>,
    val top_banner_slides: List<BannerSlide>,
    val you_will_like_section: List<Int>,
    val details_carousel: List<Book>
)