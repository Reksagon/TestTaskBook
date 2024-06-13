package com.books.app.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.books.app.R
import com.books.app.models.Config

@Composable
fun DetailsScreen(config: Config, bookId: Int, navController: NavController) {
    var selectedBook by remember { mutableStateOf(config.books.first { it.id == bookId }) }
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF532454))
    ) {
        Image(
            painter = painterResource(id = R.drawable.back_detail),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.3f)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Back Button
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(16.dp)
                    .size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            // Header Carousel
            HeaderCarousel(
                books = config.details_carousel,
                selectedBookId = bookId,
                onBookSelected = { book ->
                    selectedBook = book
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column {
                    // Book Details
                    BookDetails(book = selectedBook)

                    Divider(color = Color(0xFFD9D5D6), thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))

                    // You Will Also Like Section
                    YouWillAlsoLikeSection(
                        books = config.books.filter { config.you_will_like_section.contains(it.id) }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Read Now Button
                    ReadNowButton()
                }
            }
        }
    }
}


@Composable
fun ReadNowButton() {
    Button(
        onClick = { /* TODO: Add action */ },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDD48A1)),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Read Now",
            color = Color.White,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.nunito_sans_bold)),
        )
    }
}