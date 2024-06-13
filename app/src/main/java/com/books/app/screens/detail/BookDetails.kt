package com.books.app.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.books.app.R
import com.books.app.models.Book

@Composable
fun BookDetails(book: Book) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BookDetailItem(value = book.views, label = "Readers")
            BookDetailItem(value = book.likes, label = "Likes")
            BookDetailItem(value = book.quotes, label = "Quotes")
            BookDetailItem(value = book.genre, label = "Genre")
        }
        Divider(color = Color(0xFFD9D5D6), thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))
        Text(
            text = "Summary",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.nunito_sans)),
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = book.summary,
            color = Color.Black,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.nunito_sans)),
            fontWeight = FontWeight.Normal,
        )
    }
}

@Composable
fun BookDetailItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            color = Color.Black,
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.nunito_sans_bold))
        )
        Text(
            text = label,
            color = Color.Gray,
            fontFamily = FontFamily(Font(R.font.nunito_sans)),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
