package com.ecovolt.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ecovolt.app.data.local.SharedPrefsLecturaStorage
import com.ecovolt.app.data.local.SharedPrefsSesionStorage

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
