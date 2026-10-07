package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.example.myapplication.data.local.SharedPrefsLecturaStorage
import com.example.myapplication.data.local.SharedPrefsSesionStorage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(
                storage = SharedPrefsLecturaStorage(applicationContext),
                sesionStorage = SharedPrefsSesionStorage(applicationContext)
            )
        }
    }
}
