package dev.logickoder.qrpay.app.converter

import dev.logickoder.qrpay.model.TransactionDescription
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Converter
internal object TransactionDescriptionConverter : AttributeConverter<TransactionDescription?, String?> {

    override fun convertToDatabaseColumn(attribute: TransactionDescription?): String? {
        if (attribute == null) return null

        return Json.encodeToString(attribute)
    }

    override fun convertToEntityAttribute(dbData: String?): TransactionDescription? {
        if (dbData.isNullOrBlank()) return null

        return Json.decodeFromString(dbData)
    }
}