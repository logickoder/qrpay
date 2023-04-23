package dev.logickoder.qrpay.app.data.local

import androidx.room.TypeConverter
import dev.logickoder.qrpay.model.TransactionDescription
import dev.logickoder.qrpay.model.serializer.LocalDateTimeSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDateTime

object Converters {

    @TypeConverter
    fun fromTransactionDescription(description: TransactionDescription?): String? {
        if (description == null) return null

        return Json.encodeToString(description)
    }

    @TypeConverter
    fun toTransactionDescription(description: String?): TransactionDescription? {
        if (description == null) return null

        return Json.decodeFromString(description)
    }


    @TypeConverter
    fun fromLocalDateTime(time: LocalDateTime?): String? {
        if (time == null) return null

        return Json.encodeToString(LocalDateTimeSerializer, time)
    }

    @TypeConverter
    fun toLocalDateTime(time: String?): LocalDateTime? {
        if (time == null) return null

        return Json.decodeFromString(LocalDateTimeSerializer, time)
    }
}