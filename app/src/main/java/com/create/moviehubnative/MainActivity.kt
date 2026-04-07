package com.create.moviehubnative

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.create.moviehubnative.ui.screen.MainScreen
import com.create.moviehubnative.ui.theme.MovieHubNativeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieHubNativeTheme {
                MainScreen()
            }
        }
    }
}
