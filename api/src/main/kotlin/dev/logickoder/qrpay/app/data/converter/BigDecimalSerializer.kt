package dev.logickoder.qrpay.app.data.converter

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.math.BigDecimal

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = BigDecimal::class)
class BigDecimalSerializer {
    override fun serialize(encoder: Encoder, value: BigDecimal) {
        encoder.encodeDouble(value.toDouble())
    }

    override fun deserialize(decoder: Decoder): BigDecimal {
        return decoder.decodeDouble().toBigDecimal()
    }
}