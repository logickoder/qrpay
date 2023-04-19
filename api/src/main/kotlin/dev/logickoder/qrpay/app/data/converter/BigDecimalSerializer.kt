package dev.logickoder.qrpay.app.data.converter

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.math.BigDecimal

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = BigDecimal::class)
object BigDecimalSerializer {
    override fun serialize(encoder: Encoder, value: BigDecimal) {
        encoder.encodeString(String.format("%.2f", value.toDouble()))
    }

    override fun deserialize(decoder: Decoder): BigDecimal {
        return decoder.decodeString().toBigDecimal()
    }
}