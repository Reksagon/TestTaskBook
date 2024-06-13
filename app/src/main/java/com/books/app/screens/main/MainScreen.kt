package com.books.app.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.books.app.R
import com.books.app.models.Config
import com.books.app.ui.theme.TestBooksTheme

@Composable
fun MainScreen(config: Config, navController: NavHostController) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.Black)
    ) {
        Text(
            text = "Library",
            color = Color(0xFFD0006E),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        BannerSection(bannerSlides = config.top_banner_slides, navController = navController)

        Spacer(modifier = Modifier.height(16.dp))

        // Групування книг по жанрам
        val booksByGenre = config.books.groupBy { it.genre }

        booksByGenre.forEach { (genre, books) ->
            BookSection(
                sectionTitle = genre,
                books = books,
                navController = navController
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun MainScreenPreview() {
//    TestBooksTheme {
//        MainScreen()
//    }
//}
