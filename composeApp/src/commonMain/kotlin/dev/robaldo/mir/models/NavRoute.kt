package dev.robaldo.mir.models

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * A data class containing a route's name, route, icon and selected icon.
 *
 * @property name The display name of the route
 * @property route The actual route from the [dev.robaldo.mir.definitions.Routes] object
 * @property icon The icon to be displayed when the route is not selected
 * @property selectedIcon The icon to be displayed when the route is selected
 * @property showBadge When true, the Navigation Bar Item will have a badge.
 *
 * @see dev.robaldo.mir.definitions.Routes
 * @see dev.robaldo.mir.ui.components.AppNavigationBar
 * @author Simone Robaldo
 */
data class NavRoute(
    val name: String,
    val route: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val showBadge: Boolean = false
)


