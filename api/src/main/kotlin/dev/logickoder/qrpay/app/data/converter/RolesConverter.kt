package dev.logickoder.qrpay.app.data.converter

import dev.logickoder.qrpay.user.Role
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter


@Converter
object RolesConverter :
    AttributeConverter<List<Role>?, String?> {

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