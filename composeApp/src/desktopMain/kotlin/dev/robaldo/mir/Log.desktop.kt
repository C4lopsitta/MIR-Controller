package dev.robaldo.mir

actual object Log {
    actual val onlyCritical: Boolean
        get() = TODO("Not yet implemented")

    actual fun d(tag: String, message: String) {
    }

    actual fun i(tag: String, message: String) {
    }

    actual fun w(tag: String, message: String) {
    }

    actual fun e(tag: String, message: String, throwable: Throwable?) {
    }

}