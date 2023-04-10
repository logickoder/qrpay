package dev.logickoder.qrpay.app.data.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.springframework.security.core.authority.SimpleGrantedAuthority


@Converter
object SimpleGrantedAuthoritiesConverter :
    AttributeConverter<List<SimpleGrantedAuthority>?, String?> {

    override fun convertToDatabaseColumn(attribute: List<SimpleGrantedAuthority>?): String? {
        return attribute?.map { it.authority }?.filter { it.isNotBlank() }?.joinToString { "," }
    }

    override fun convertToEntityAttribute(dbData: String?): List<SimpleGrantedAuthority>? {
        if (dbData.isNullOrBlank()) return null

        return dbData.split(",").map {
            SimpleGrantedAuthority(it)
        }
    }
}