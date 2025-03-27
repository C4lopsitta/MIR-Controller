package dev.robaldo.mir

actual class Controller {
    actual fun isControllerConnected(): Boolean {
        val deviceIds = InputDevice.getDeviceIds()
        for (id in deviceIds) {
            val device = InputDevice.getDevice(id)
            if (device.sources and InputDevice.SOURCE_JOYSTICK == InputDevice.SOURCE_JOYSTICK) {
                return true
            }
        }
        return false
    }

}