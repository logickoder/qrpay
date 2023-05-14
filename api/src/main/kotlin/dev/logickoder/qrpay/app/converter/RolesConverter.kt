package dev.logickoder.qrpay.app.converter

import dev.logickoder.qrpay.model.Role
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter


@Converter
internal object RolesConverter : AttributeConverter<List<Role>?, String?> {

    override fun convertToDatabaseColumn(attribute: List<Role>?): String? {
        return attribute?.joinToString()
    }

    override fun convertToEntityAttribute(dbData: String?): List<Role>? {
        if (dbData.isNullOrBlank()) return null

        return dbData.split(",").filter {
            it.isNotBlank()
        }.map {
            Role.valueOf(it.trim())
        }
    }
}