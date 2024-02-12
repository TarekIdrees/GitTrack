package com.tareq.gittrack.data.api.util

//region Network
open class NetworkException : Exception()
class NoConnectionException : NetworkException()
class InvalidDataException : NetworkException()
class NotFoundException : NetworkException()
class InternalServerException : NetworkException()
class ForbiddenException : NetworkException()
class UnAuthorizedException : NetworkException()
//endregion