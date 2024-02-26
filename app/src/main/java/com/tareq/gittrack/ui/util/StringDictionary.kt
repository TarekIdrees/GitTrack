package com.tareq.gittrack.ui.util

import com.tareq.gittrack.domain.util.ErrorHandler

interface StringDictionary {
    val errorString : Map<ErrorHandler,String>
    val emptyResultString : String
    val emptySearchTerm: String
}