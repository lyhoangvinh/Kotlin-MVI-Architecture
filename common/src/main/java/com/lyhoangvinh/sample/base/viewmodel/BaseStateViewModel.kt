package com.lyhoangvinh.sample.base.viewmodel

import com.lyhoangvinh.sample.domain.model.state.BaseViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseStateViewModel<STATE : BaseViewState<*>, INTENT> : BaseViewModel() {

    private val _uiState = MutableStateFlow<BaseViewState<*>>(BaseViewState.Empty)
    val uiState = _uiState.asStateFlow()

    abstract fun onIntent(intentType: INTENT)

    protected fun withState(state: STATE) = safeLaunch {
        _uiState.emit(state)
    }

    protected fun withStateData(state: STATE) = safeLaunch {
        _uiState.emit(BaseViewState.Data(state))
    }

    protected fun loading() = safeLaunch {
        _uiState.emit(BaseViewState.Loading)
    }

    protected fun empty() = safeLaunch {
        _uiState.emit(BaseViewState.Empty)
    }

    override fun startLoading() {
        super.startLoading()
        _uiState.value = BaseViewState.Loading
    }

    override fun handleError(exception: Throwable) {
        super.handleError(exception)
        _uiState.value = BaseViewState.Error(exception)
    }
}