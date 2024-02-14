package com.tareq.gittrack.domain.util

//region User
open class UserException : Exception()
class InvalidInputException : UserException()
//endregion


//region Network
open class NetworkException : Exception()
class NoConnectionException : NetworkException()
class InvalidDataException : NetworkException()
class NotFoundException : NetworkException()
class InternalServerException : NetworkException()
class ForbiddenException : NetworkException()
//endregion


sealed interface ErrorHandler {
    //region User
    object InvalidInput : ErrorHandler
    //endregion

    //region Network
    object NoConnection : ErrorHandler
    object InvalidData : ErrorHandler
    object NotFound : ErrorHandler
    object InternalServer : ErrorHandler
    object Forbidden : ErrorHandler
    //endregion
}

fun handelUserException(
    exception: UserException,
    onError: (t: ErrorHandler) -> Unit,
) {
    when (exception) {
        is InvalidInputException -> onError(ErrorHandler.InvalidInput)
    }
}

fun handelNetworkException(
    exception: NetworkException,
    onError: (t: ErrorHandler) -> Unit,
) {
    when (exception) {
        is NoConnectionException -> onError(ErrorHandler.NoConnection)
        is InvalidDataException -> onError(ErrorHandler.InvalidData)
        is NotFoundException -> onError(ErrorHandler.NotFound)
        is InternalServerException -> onError(ErrorHandler.InternalServer)
        is ForbiddenException -> onError(ErrorHandler.Forbidden)
    }
}