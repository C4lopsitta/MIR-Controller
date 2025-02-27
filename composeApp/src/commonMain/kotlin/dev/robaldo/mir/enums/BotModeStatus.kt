package dev.robaldo.mir.enums

enum class BotModeStatus {
    UNDEFINED,
    MISSION;

    companion object {
        fun fromStatus(status: Int): BotModeStatus {
            return when(status) {
                7 -> MISSION
                else -> UNDEFINED
            }
        }
    }
}