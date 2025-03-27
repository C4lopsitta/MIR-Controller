package dev.robaldo.mir.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.robaldo.mir.definitions.Routes
import dev.robaldo.mir.enums.BotBadgeStatus

import dev.robaldo.mir.models.NavRoute
import dev.robaldo.mir.models.view.BotViewModel


@Composable
fun AppNavigationBar(
    navHostController: NavHostController,
    botViewModel: BotViewModel
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
                                if(botViewModel.badge.value == BotBadgeStatus.ERROR) {
                                    Badge(
                                        containerColor = botViewModel.badge.value.toColor(),
                                        content = {
                                            Text(
                                                "${if ((botViewModel.status.value?.errors?.size ?: 0) < 10) botViewModel.status.value?.errors?.size ?: 0 else "9+"}",
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        }
                                    )
                                } else if(botViewModel.badge.value == BotBadgeStatus.EXECUTING) {
                                    Badge (
                                        containerColor = Color.Transparent
                                    ) {
                                        CircularProgressIndicator( modifier = Modifier.width(16.dp).height(12.dp) )
                                    }
                                } else {
                                    Badge(
                                        containerColor = botViewModel.badge.value.toColor()
                                    )
                                }
                            }
                        ) {
                            Icon(route.icon, contentDescription = route.name )
                        }
                    } else Icon(route.icon, contentDescription = route.name )
                },
                selected = route.route == navHostController.currentBackStackEntry?.destination?.route,
                onClick = {
                    navHostController.navigate(route.route)
                },
                alwaysShowLabel = true,
                label = { Text(route.name) }
            )
        }
    }
}
