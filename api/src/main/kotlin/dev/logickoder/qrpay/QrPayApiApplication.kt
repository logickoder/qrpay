package dev.logickoder.qrpay

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class QrPayApiApplication

fun main(args: Array<String>) {
    runApplication<QrPayApiApplication>(*args)
}