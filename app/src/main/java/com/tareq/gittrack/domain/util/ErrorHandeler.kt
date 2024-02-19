package com.tareq.gittrack.domain.util


sealed interface ErrorHandler

//region User
open class UserException : Exception()
class InvalidInputException : UserException()

sealed class UserErrorHandler : ErrorHandler {
    //region User
    object InvalidInput : UserErrorHandler()
    //endregion
}

fun handelUserException(
    exception: UserException,
    onError:  (t: ErrorHandler) -> Unit,
) {
    when (exception) {
        is InvalidInputException -> onError(UserErrorHandler.InvalidInput)
    }
}
//endregion

//region Network
open class NetworkException : Exception()
class NoConnectionException : NetworkException()
class InvalidDataException : NetworkException()
class NotFoundException : NetworkException()
class InternalServerException : NetworkException()
class ForbiddenException : NetworkException()

sealed interface NetworkErrorHandler : ErrorHandler {
    object NoConnection : NetworkErrorHandler
    object InvalidData : NetworkErrorHandler
    object NotFound : NetworkErrorHandler
    object InternalServer : NetworkErrorHandler
    object Forbidden : NetworkErrorHandler
}

fun handelNetworkException(
    exception: NetworkException,
    onError:  (t: ErrorHandler) -> Unit,
) {
    when (exception) {
        is NoConnectionException -> onError(NetworkErrorHandler.NoConnection)
        is InvalidDataException -> onError(NetworkErrorHandler.InvalidData)
        is NotFoundException -> onError(NetworkErrorHandler.NotFound)
        is InternalServerException -> onError(NetworkErrorHandler.InternalServer)
        is ForbiddenException -> onError(NetworkErrorHandler.Forbidden)
    }
}

//endregion

//region Database
open class DatabaseException : Exception()
class NotFoundOfflineException : DatabaseException()
class ForbiddenAndNotFoundOffline : DatabaseException()

sealed class DatabaseErrorHandler : ErrorHandler {
    object NotFoundOffline : DatabaseErrorHandler()
    object ForbiddenAndNotFound : DatabaseErrorHandler()
}

fun handelDatabaseException(
    exception: DatabaseException,
    onError:  (t: ErrorHandler) -> Unit,
) {
    when (exception) {
        is NotFoundOfflineException -> onError(DatabaseErrorHandler.NotFoundOffline)
        is ForbiddenAndNotFoundOffline -> onError(DatabaseErrorHandler.ForbiddenAndNotFound)
    }
}
//endregion











