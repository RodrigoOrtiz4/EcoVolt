package com.example.ecovolt_00

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform