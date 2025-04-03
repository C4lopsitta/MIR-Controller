package dev.robaldo.mir

import android.view.InputDevice
import android.view.MotionEvent

actual object Controller {

    private var leftStickX: Float = 0f
    private var leftStickY: Float = 0f

    actual fun getGameControllerIds(): List<Int> {

        val gameControllerDeviceIds = mutableListOf<Int>()
        val deviceIds = InputDevice.getDeviceIds()
        deviceIds.forEach { deviceId ->
            InputDevice.getDevice(deviceId)?.apply {

                // Verify that the device has gamepad buttons, control sticks, or both.
                if (sources and InputDevice.SOURCE_GAMEPAD == InputDevice.SOURCE_GAMEPAD
                    || sources and InputDevice.SOURCE_JOYSTICK == InputDevice.SOURCE_JOYSTICK) {
                    // This device is a game controller. Store its device ID.
                    gameControllerDeviceIds
                        .takeIf { !it.contains(deviceId) }
                        ?.add(deviceId)
                }
            }
        }
        return gameControllerDeviceIds
    }

    fun onGenericMotionEvent(event: MotionEvent): Boolean {
        if (event.source and InputDevice.SOURCE_JOYSTICK == InputDevice.SOURCE_JOYSTICK &&
            event.action == MotionEvent.ACTION_MOVE) {

            leftStickX = event.getAxisValue(MotionEvent.AXIS_X)
            leftStickY = event.getAxisValue(MotionEvent.AXIS_Y)
        }
        return false
    }

    actual fun getLeftJoystickValues(): Pair<Float, Float> {
        return Pair(leftStickX, leftStickY)
    }
}