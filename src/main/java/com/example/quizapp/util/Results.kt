package com.example.quizapp.util

sealed interface Results<out T> {
    data object Idle: Results<Nothing>
    data object Loading: Results<Nothing>
    data class Success<out T>(val data:T): Results<T>
    data class Error(val error:Throwable): Results<Nothing>
    data class Finished(val totalQuestions: Int, val correctAnswers: Int) : Results<Nothing>
}