package dev.robaldo.mir.docSamples

import dev.robaldo.mir.api.MirApi
import dev.robaldo.mir.models.responses.get.Item

suspend fun ItemSample() {
    val myMap = Item("guid", "url", "name")

    val myDetailedMap = MirApi.getMap(myMap.guid)
}
