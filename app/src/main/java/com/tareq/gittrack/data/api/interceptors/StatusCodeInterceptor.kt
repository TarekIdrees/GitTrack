package com.tareq.gittrack.data.api.interceptors

import com.tareq.gittrack.data.api.util.ForbiddenException
import com.tareq.gittrack.data.api.util.InternalServerException
import com.tareq.gittrack.data.api.util.InvalidDataException
import com.tareq.gittrack.data.api.util.NoConnectionException
import com.tareq.gittrack.data.api.util.NotFoundException
import com.tareq.gittrack.data.api.util.UnAuthorizedException
import okhttp3.Interceptor
import okhttp3.Response

class StatusCodeInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        when (response.code) {
            502 -> throw NoConnectionException()
            400 -> throw InvalidDataException()
            401 -> throw UnAuthorizedException()
            403 -> throw ForbiddenException()
            404 -> throw NotFoundException()
            500 -> throw InternalServerException()
            else -> {
                // You may handle other status codes here or return the response as it is
            }
        }
        return response
    }
}