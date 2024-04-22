package com.tareq.gittrack.domain.util


sealed class ErrorHandler : Exception(){
    data object InvalidInput: ErrorHandler()

    data object NoConnection: ErrorHandler()
    data object InvalidData: ErrorHandler()
    data object NotFound: ErrorHandler()
    data object InternalServer : ErrorHandler()
    data object Forbidden : ErrorHandler()

    data object ForbiddenAndNotFoundOffline : ErrorHandler()
    data object NotFoundOffline: ErrorHandler()

    data object UnKnownError: ErrorHandler()
}
