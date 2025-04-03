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
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.WifiTethering
import androidx.compose.material.icons.rounded.WifiTetheringOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zakgof.korender.Korender
import com.zakgof.korender.math.ColorRGB
import com.zakgof.korender.math.ColorRGBA
import com.zakgof.korender.math.Transform.Companion.translate
import com.zakgof.korender.math.Vec3
import com.zakgof.korender.math.y
import com.zakgof.korender.math.z
import dev.robaldo.mir.models.view.BotViewModel
import kotlinproject.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import kotlin.math.sin

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
                Frame {
//                    camera = camera(Vec3(-2.0f, 5f, 30f), -1.z, 1.y)
                    DirectionalLight(Vec3(4f, -6f, -4f).normalize())
                    AmbientLight(ColorRGB.white(0.7f))
//                Billboard(
//                    standart(
//                        baseColor = appBackgroundColor,
//                        xscale = 1.0f,
//                        yscale = 1.0f
//                    ),
//                    position = Vec3(),
//                    transparent = false
//                )
                    Renderable(
                        standart {
                            baseColor = ColorRGBA(0.8f, 0.8f, 0.8f, 1.0f)
                            pbr.roughness = 0.4f
                        },
                        mesh = obj("blendermonkey.obj"),
//                    transform = translate(sin(frameInfo.time).y)
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