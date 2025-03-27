package dev.robaldo.mir.docSamples

import dev.robaldo.mir.api.MirApi
import dev.robaldo.mir.models.responses.get.Item

suspend fun MirApiSample() {
    val missions = MirApi.getMissions()
}
