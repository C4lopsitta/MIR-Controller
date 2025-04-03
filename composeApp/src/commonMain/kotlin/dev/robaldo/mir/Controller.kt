package dev.robaldo.mir

expect object Controller {
    /**
     * Get a list of ids of all connected controllers.
     *
     * @return A list of ids
     *
     * @author Marco Garro
     *
     */
    fun getGameControllerIds(): List<Int>
    fun getLeftJoystickValues(): Pair<Float, Float>}