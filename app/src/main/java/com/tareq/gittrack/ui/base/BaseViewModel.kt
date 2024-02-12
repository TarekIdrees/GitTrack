package com.tareq.gittrack.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tareq.gittrack.domain.util.AdminException
import com.tareq.gittrack.domain.util.CartException
import com.tareq.gittrack.domain.util.CategoryException
import com.tareq.gittrack.domain.util.CouponException
import com.tareq.gittrack.domain.util.ErrorHandler
import com.tareq.gittrack.domain.util.GeneralException
import com.tareq.gittrack.domain.util.ImageException
import com.tareq.gittrack.domain.util.MarketException
import com.tareq.gittrack.domain.util.NetworkException
import com.tareq.gittrack.domain.util.OrderException
import com.tareq.gittrack.domain.util.OwnerException
import com.tareq.gittrack.domain.util.ProductException
import com.tareq.gittrack.domain.util.TokenException
import com.tareq.gittrack.domain.util.UserException
import com.tareq.gittrack.domain.util.handelAdminException
import com.tareq.gittrack.domain.util.handelCartException
import com.tareq.gittrack.domain.util.handelCategoryException
import com.tareq.gittrack.domain.util.handelCouponException
import com.tareq.gittrack.domain.util.handelGeneralException
import com.tareq.gittrack.domain.util.handelImageException
import com.tareq.gittrack.domain.util.handelMarketException
import com.tareq.gittrack.domain.util.handelNetworkException
import com.tareq.gittrack.domain.util.handelOrderException
import com.tareq.gittrack.domain.util.handelOwnerException
import com.tareq.gittrack.domain.util.handelProductException
import com.tareq.gittrack.domain.util.handelTokenException
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

    protected fun <T : BaseUiEffect> effectActionExecutor(
        _effect: MutableSharedFlow<T>,
        effect: T,
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
            handleException(
                onError
            ) {
                function().collect { result ->
                    onSuccess(result)
                }
            }
        }
    }

    private suspend fun <T> handleException(
        onError: (t: ErrorHandler) -> Unit,
        action: suspend () -> T
    ) {
        try {
            action()
        } catch (exception: Exception) {
            when (exception) {
                is UserException -> handelUserException(exception, onError)
                is OwnerException -> handelOwnerException(exception, onError)
                is AdminException -> handelAdminException(exception, onError)
                is MarketException -> handelMarketException(exception, onError)
                is CategoryException -> handelCategoryException(exception, onError)
                is ProductException -> handelProductException(exception, onError)
                is OrderException -> handelOrderException(exception, onError)
                is CartException -> handelCartException(exception, onError)
                is ImageException -> handelImageException(exception, onError)
                is CouponException -> handelCouponException(exception, onError)
                is GeneralException -> handelGeneralException(exception, onError)
                is TokenException -> handelTokenException(exception, onError)
                is NetworkException -> handelNetworkException(exception, onError)
                is IOException -> onError(ErrorHandler.NoConnection)
                else -> onError(ErrorHandler.InvalidData)
            }
        }
    }

}