package com.app.examen_tc2007b.domain.common

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<out T>(val data: T, val isOffline: Boolean = false) : Result<T>()
    data class Error(val message: String, val cause: Throwable? = null) : Result<Nothing>()
}
