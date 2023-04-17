package dev.logickoder.qrpay.app.configuration

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CoroutineConfig {
    @Bean
    fun scope(): CoroutineScope = CoroutineScope(Dispatchers.Default)
}