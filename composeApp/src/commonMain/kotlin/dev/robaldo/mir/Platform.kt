package dev.robaldo.mir

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform