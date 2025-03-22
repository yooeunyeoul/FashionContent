package com.example.stylefeed

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.example.stylefeed.ui.theme.FashionContentTheme
import com.example.stylefeed.ui.viewmodel.ProductState
import com.example.stylefeed.ui.viewmodel.ProductViewModel
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
                ProductScreen()
            }
        }
    }

}

@Composable
fun ProductScreen() {
    val viewModel: ProductViewModel = mavericksViewModel()
//    val viewModel: ExampleViewModel = mavericksViewModel()
    val state by viewModel.collectAsState(ProductState::sections)

    when (val sections = state) {
        is Loading -> CircularProgressIndicator()
        is Success -> {
            Log.d("Sections", sections.invoke().toString())
        }
        is Fail -> {
            Log.d("Sections", sections.error.toString())
        }
        else -> {
            Log.d("Sections", "else")
        }
    }
}
