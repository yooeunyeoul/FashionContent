package com.example.stylefeed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.airbnb.mvrx.compose.mavericksViewModel
import com.example.stylefeed.ui.theme.FashionContentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: ExampleViewModel = mavericksViewModel()
            viewModel.stateFlow
            FashionContentTheme {
            }
        }
    }
}
