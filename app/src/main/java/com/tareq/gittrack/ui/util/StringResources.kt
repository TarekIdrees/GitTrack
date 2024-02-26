package com.tareq.gittrack.ui.util

import android.content.Context
import com.tareq.gittrack.R
import com.tareq.gittrack.domain.util.ErrorHandler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringResources @Inject constructor(
    val context: Context
) : StringDictionary {
    private fun getStringFromFile(fileId: Int): String {
        return context.getString(fileId)
    }

    override val errorString: Map<ErrorHandler, String>
        get() = mapOf(
            ErrorHandler.NoConnection to getStringFromFile(R.string.no_connection),
            ErrorHandler.InternalServer to getStringFromFile(R.string.internalServer),
            ErrorHandler.NotFound to getStringFromFile(R.string.notFoundOnline),
            ErrorHandler.Forbidden to getStringFromFile(R.string.forbidden),
            ErrorHandler.ForbiddenAndNotFoundOffline to getStringFromFile(R.string.forbiddenAndNotFoundOffline),
            ErrorHandler.InvalidInput to getStringFromFile(R.string.invalidData),
            ErrorHandler.InvalidData to getStringFromFile(R.string.invalidData),
            ErrorHandler.NotFoundOffline to getStringFromFile(R.string.notFoundOffline),
            ErrorHandler.ForbiddenAndNotFoundOffline to getStringFromFile(R.string.forbiddenAndNotFoundOffline),
            ErrorHandler.UnKnownError to getStringFromFile(R.string.unknownError)
        )
    override val emptyResultString: String
        get() = getStringFromFile(R.string.notFoundOnline)
    override val emptySearchTerm: String
        get() = getStringFromFile(R.string.invalidData)
}