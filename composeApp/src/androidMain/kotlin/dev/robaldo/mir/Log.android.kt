package dev.robaldo.mir

actual object Log {
    actual val onlyCritical: Boolean = true

    actual fun d(tag: String, message: String) {
        if(onlyCritical) return

        android.util.Log.d(tag, message)
    }

    actual fun i(tag: String, message: String) {
        android.util.Log.i(tag, message)
    }

    actual fun w(tag: String, message: String) {
        if(onlyCritical) return

        android.util.Log.w(tag, message)
    }

    actual fun e(tag: String, message: String, throwable: Throwable?) {
        android.util.Log.e(tag, message, throwable)
    }

}