package dev.robaldo.mir.ui.routes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.WifiTethering
import androidx.compose.material.icons.rounded.WifiTetheringOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zakgof.korender.Korender
import com.zakgof.korender.math.ColorRGB.Companion.white
import com.zakgof.korender.math.ColorRGBA
import com.zakgof.korender.math.Transform.Companion.scale
import com.zakgof.korender.math.Vec3
import com.zakgof.korender.math.y
import com.zakgof.korender.math.z
import dev.robaldo.mir.bot3dmodel.SideCamera
import dev.robaldo.mir.models.view.BotViewModel
import kotlinproject.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

/**
 * The Home route composable body.
 *
 * @param botViewModel The view model for the bot.
 * @param setFab The function to set the floating action button.
 *
 * @see BotViewModel
 * @see Korender
 *
 * @author Simone Robaldo
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
fun Home(
    botViewModel: BotViewModel,
    setFab: @Composable ((@Composable () -> Unit)?) -> Unit
) {
    setFab(null)
    val appBackgroundColor = MaterialTheme.colorScheme.background

    Column {
        Box (
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.4f)
        ) {
            Korender(
                appResourceLoader = { Res.readBytes(it) }
            ) {
                val orbitCamera = SideCamera(this)

                Frame {
//                    Renderable(
//                        standart {
//                            baseColor = ColorRGBA(appBackgroundColor.red, appBackgroundColor.green, appBackgroundColor.blue, 1.0f)
//                        },
//                        mesh = cube(10f),
////                        transform = translate(0f, 0f, -10f)
//                    )

                    camera = orbitCamera.camera()
                    DirectionalLight(Vec3(4f, -6f, -4f).normalize(), white(10f)) {
                        Cascade(mapSize = 1024, near = 4f,  far =  100f, algorithm = pcss(samples = 64, blurRadius = 0.1f))
                    }
                    AmbientLight(white(0.7f))

                    Renderable(
                        standart {
                            baseColor = ColorRGBA(0.4f, 0.4f, 0.4f, 1.0f)
                            pbr.roughness = 0.8f
                            pbr.metallic = 0.7f
                        },
                        mesh = obj("MiR100.obj"),
                        transform = scale(0.09f)
                    )
                    Renderable(
                        standart {
                            emissiveFactor = botViewModel.badge.value.toModelColor()
                            pbr.metallic = 0.9f
                        },
                        mesh = obj("MiR100_LED.obj"),
                        transform = scale(0.09f)
                    )
                }
            }
        }

        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                if(botViewModel.status.value == null) Icons.Rounded.WifiTetheringOff else Icons.Rounded.WifiTethering,
                contentDescription = "Icon",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.width(92.dp).height(92.dp)
            )
            Text(
                "MiR " + if(botViewModel.status.value == null) "Disconnected" else "Connected",
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding( horizontal = 32.dp, vertical = 12.dp ),
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}