// Example taken from https://github.com/zakgof/korender/blob/main/korender-framework/examples/src/commonMain/kotlin/camera/OrbitCamera.kt

package dev.robaldo.mir.bot3dmodel

import com.zakgof.korender.CameraDeclaration
import com.zakgof.korender.context.KorenderContext
import com.zakgof.korender.math.Vec3

class SideCamera(private val context: KorenderContext) {
    fun camera(): CameraDeclaration {
        return context.camera(
            direction = Vec3(0.64661175f, -0.24487963f, -0.72244537f),
            position = Vec3(-15.572472f, 7.897482f, 17.398787f),
            up = Vec3(0.16331439f, 0.9695535f, -0.18246764f)
        )
    }
}