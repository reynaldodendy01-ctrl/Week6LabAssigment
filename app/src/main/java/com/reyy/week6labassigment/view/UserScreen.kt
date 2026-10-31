package com.reyy.week6labassigment.view
// UserScreen.kt
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyy.week6labassigment.viewmodel.UserViewModel


@Composable
fun UserScreen(
    // Inject ViewModel di level parameter Screen
    viewModel: UserViewModel = viewModel()
) {
    // Collect state dari ViewModel agar UI ter-recompose saat data berubah
    val name by viewModel.userName.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Halo, $name!", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.updateName("Reynaldo") }) {
            Text("Login")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UserScreenPreview(){
    UserScreen()
}