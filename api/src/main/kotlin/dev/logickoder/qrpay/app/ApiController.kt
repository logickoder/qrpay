package dev.logickoder.qrpay.app

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.net.URI


/**
 * RestController that handles generic API requests.
 */
@RestController
@RequestMapping("/api")
internal class ApiController {

    @Value("\${qrpay.api.docs}")
    private lateinit var docs: String

    /**
     * Handles requests to retrieve the API documentation.
     */
    @GetMapping("/docs")
    fun getApiDocs(response: ServerHttpResponse): Mono<Void> {
        response.statusCode = HttpStatus.TEMPORARY_REDIRECT
        response.headers.location = URI.create(docs)
        return response.setComplete()
    }
}
