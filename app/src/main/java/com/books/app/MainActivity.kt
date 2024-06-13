package com.books.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.books.app.models.Book
import com.books.app.models.Config
import com.books.app.models.DetailsCarousel
import com.books.app.screens.detail.DetailsScreen
import com.books.app.screens.loading.LoadingScreen
import com.books.app.screens.main.MainScreen
import com.books.app.ui.theme.TestBooksTheme
import com.google.common.reflect.TypeToken
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private lateinit var remoteConfig: FirebaseRemoteConfig
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(mapOf("json_data" to "{}", "details_carousel" to "[]"))

        setContent {
            TestBooksTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val configState = remember { mutableStateOf<Config?>(null) }
                    val coroutineScope = rememberCoroutineScope()

                    LaunchedEffect(Unit) {
                        coroutineScope.launch {
                            fetchRemoteConfig { jsonData, detailsCarousel ->
                                val gson = Gson()
                                val configType = object : TypeToken<Config>() {}.type
                                val config: Config = gson.fromJson(jsonData, configType)
                                val detailsCarouselType = object : TypeToken<DetailsCarousel>() {}.type
                                val detailsBooks: DetailsCarousel = gson.fromJson(detailsCarousel, detailsCarouselType)

                                configState.value = config.copy(details_carousel = detailsBooks.books)
                            }
                        }
                    }

                    if (configState.value != null) {
                        val navController = rememberNavController()
                        NavHost(navController, startDestination = "mainScreen") {
                            composable("mainScreen") {
                                MainScreen(configState.value!!, navController)
                            }
                            composable(
                                route = "detailsScreen/{bookId}",
                                arguments = listOf(navArgument("bookId") { type = NavType.IntType })
                            ) { backStackEntry ->
                                val bookId = backStackEntry.arguments?.getInt("bookId") ?: 0
                                DetailsScreen(configState.value!!, bookId, navController)
                            }
                        }
                    } else {
                        LoadingScreen()
                    }
                }
            }
        }
    }

    private fun fetchRemoteConfig(onComplete: (String, String) -> Unit) {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val jsonData = remoteConfig.getString("json_data")
                    val detailsCarousel = remoteConfig.getString("details_carousel")
                    Log.e("json_data", jsonData)
                    Log.e("details_carousel", detailsCarousel)
                    onComplete(jsonData, detailsCarousel)
                } else {
                    // Fetch failed
                }
            }
    }
}




@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    TestBooksTheme {
        LoadingScreen()
    }
}
