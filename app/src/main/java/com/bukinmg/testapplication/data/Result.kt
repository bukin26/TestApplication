package com.bukinmg.testapplication.data

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data object Error : Result<Nothing>()
}

suspend fun <T> wrapResult(block: suspend () -> T) = try {
    Result.Success(block.invoke())
} catch (t: Throwable) {
    Result.Error
}