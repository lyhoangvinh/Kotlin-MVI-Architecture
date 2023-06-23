package com.lyhoangvinh.sample.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyhoangvinh.sample.domain.model.state.DataState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    open fun handleError(exception: Throwable) {}

    open fun startLoading() {}

    protected fun safeLaunch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            Timber.tag("ViewModel-ExceptionHand").e(exception)
            handleError(exception)
        }, block = block)
    }

    protected suspend fun <T> call(
        callFlow: Flow<T>,
        completionHandler: (collect: T) -> Unit = {}
    ) {
        callFlow
            .catch { handleError(it) }
            .collect {
                completionHandler(it)
            }
    }

    protected suspend fun <T> execute(
        callFlow: Flow<DataState<T>>,
        completionHandler: (collect: T) -> Unit = {}
    ) {
        callFlow
            .onStart { startLoading() }
            .catch { handleError(it) }
            .collect { state ->
                when (state) {
                    is DataState.Error -> handleError(state.error)
                    is DataState.Success -> completionHandler(state.result)
                }
            }
    }
}