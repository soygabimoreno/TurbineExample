package soy.gabimoreno.turbineexample.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import soy.gabimoreno.turbineexample.presentation.theme.TurbineExampleTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TurbineExampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val mainViewModel: MainViewModel = hiltViewModel()
                    val state by mainViewModel.uiState.collectAsState()
                    val label = when (state) {
                        MainViewModel.UiState.Idle -> "Idle"
                        MainViewModel.UiState.Error -> "Error"
                        MainViewModel.UiState.Success -> "Success"
                    }
                    MainScreen(label) {
                        mainViewModel.changeUiState()
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(label: String, onButtonClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontSize = 40.sp,
            )
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onButtonClicked) {
            Text(
                text = "Change State",
                style = TextStyle(
                    fontSize = 16.sp,
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TurbineExampleTheme {
        MainScreen("Foo") {

        }
    }
}
