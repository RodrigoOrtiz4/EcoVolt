package com.example.ecomovil

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform