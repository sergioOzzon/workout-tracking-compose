package com.learning.intervaltimer.network

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequest
import io.ktor.http.HttpStatusCode
import kotlinx.io.IOException

internal class NetworkResponseHandler {
    suspend fun handleResponseError(cause: Throwable, request: HttpRequest) {
        throw when (cause) {
            else -> {
                UnknownError(message = cause.message, cause = cause)
            }
        }
    }
}

sealed class NetworkError(
    message: String? = null,
    cause: Throwable? = null,
) : Exception(message, cause)

data class UnknownError(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : NetworkError(message = message, cause = cause)


