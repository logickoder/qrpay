package dev.logickoder.qrpay.app.utils

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

fun JsonObject.toMap(): MutableMap<String, Any?> {
    return entries.associate {
        it.key to extractValue(it.value)
    }.toMutableMap()
}

private fun extractValue(element: JsonElement): Any? {
    return when (element) {
        is JsonNull -> null
        is JsonPrimitive -> element.content
        is JsonArray -> element.map { extractValue(it) }
        is JsonObject -> element.toMap()
    }
}