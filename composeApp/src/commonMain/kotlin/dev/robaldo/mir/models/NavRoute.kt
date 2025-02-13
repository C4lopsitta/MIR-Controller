package dev.robaldo.mir.models

import androidx.compose.ui.graphics.vector.ImageVector

data class NavRoute(
    val name: String,
    val route: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val showBadge: Boolean = false
)
