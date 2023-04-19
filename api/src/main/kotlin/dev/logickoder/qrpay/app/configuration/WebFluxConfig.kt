package dev.logickoder.qrpay.app.configuration

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.codec.ByteArrayDecoder
import org.springframework.core.codec.ByteArrayEncoder
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.http.codec.json.KotlinSerializationJsonDecoder
import org.springframework.http.codec.json.KotlinSerializationJsonEncoder
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer


@Configuration
@EnableWebFlux
class WebFluxConfig : WebFluxConfigurer {
    @OptIn(ExperimentalSerializationApi::class)
    @Bean
    fun json(): Json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
    }

    override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
        configurer.customCodecs().apply {
            // used to serialize and deserialize the swagger api docs
            register(ByteArrayEncoder())
            register(ByteArrayDecoder())
            // used to serialize and deserialize json with kotlinx
            register(KotlinSerializationJsonEncoder(json()))
            register(KotlinSerializationJsonDecoder(json()))
        }
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*")
    }
}