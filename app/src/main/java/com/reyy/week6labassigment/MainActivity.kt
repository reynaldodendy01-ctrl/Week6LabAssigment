package com.reyy.week6labassigment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyy.week6labassigment.ui.theme.Week6LabAssigmentTheme
import com.reyy.week6labassigment.view.MainAppScreen
import com.reyy.week6labassigment.viewmodel.CampusViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week6LabAssigmentTheme {
                val appViewModel: CampusViewModel = viewModel()
                MainAppScreen(appViewModel)
            }
        }
    }
}