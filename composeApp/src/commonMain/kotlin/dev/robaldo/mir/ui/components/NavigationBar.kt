package dev.robaldo.mir.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Route
import androidx.compose.material.icons.outlined.SmartToy
import androidx.compose.material.icons.outlined.SportsEsports
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Map
import androidx.compose.material.icons.rounded.Route
import androidx.compose.material.icons.rounded.SmartToy
import androidx.compose.material.icons.rounded.SportsEsports
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import dev.robaldo.mir.definitions.Routes
import dev.robaldo.mir.models.NavRoute


@Composable
fun AppNavigationBar(
    navHostController: NavHostController
) {
    val navRoutes = listOf(
        NavRoute("Home", Routes.HOME, Icons.Rounded.Home, Icons.Outlined.Home),
        NavRoute("Controller", Routes.CONTROLLER, Icons.Rounded.SportsEsports, Icons.Outlined.SportsEsports),
        NavRoute("Missions", Routes.MISSIONS, Icons.Rounded.Route, Icons.Outlined.Route),
        NavRoute("Maps", Routes.MAPS, Icons.Rounded.Map, Icons.Outlined.Map),
        NavRoute("MIR", Routes.ROBOT, Icons.Rounded.SmartToy, Icons.Outlined.SmartToy, showBadge = true)
    )

    BottomAppBar (

    ) {
        navRoutes.forEach { route ->
            NavigationBarItem(
                icon = {
                    if(route.showBadge) {
                        BadgedBox (
                            badge = {
                                Badge( containerColor = Color.Green )
                            }
                        ) {
                            Icon(route.icon, contentDescription = route.name )
                        }
                    } else Icon(route.icon, contentDescription = route.name )
                },
                selected = false,
                onClick = {
                    navHostController.navigate(route.route)
                },
                alwaysShowLabel = true,
                label = { Text(route.name) }
            )
        }
    }
}
