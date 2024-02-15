package com.tareq.gittrack.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tareq.gittrack.domain.util.ErrorHandler
import com.tareq.gittrack.domain.util.NetworkException
import com.tareq.gittrack.domain.util.UserException
import com.tareq.gittrack.domain.util.handelNetworkException
import com.tareq.gittrack.domain.util.handelUserException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

abstract class BaseViewModel<S, E>(initialState: S) : ViewModel() {
    protected val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    protected val _effect = MutableSharedFlow<E>()
    val effect = _effect.asSharedFlow()

    protected fun effectActionExecutor(
        _effect: MutableSharedFlow<E>,
        effect: E,
    ) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    protected fun <T> tryToExecute(
        function: suspend () -> Flow<T>,
        onSuccess: (T) -> Unit,
        onError: (t: ErrorHandler) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                function().collect { result ->
                    onSuccess(result)
                }
            } catch (exception: Exception) {
                handleException(exception, onError)
            }
        }
    }

    private fun handleException(
        exception: java.lang.Exception,
        onError: (t: ErrorHandler) -> Unit,
    ) {
        when (exception) {
            is UserException -> handelUserException(exception, onError)
            is NetworkException -> handelNetworkException(exception, onError)
            is IOException -> onError(ErrorHandler.NoConnection)
            else -> onError(ErrorHandler.InvalidData)
        }
    }
}