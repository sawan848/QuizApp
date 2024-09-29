package com.example.quizapp.data

import com.example.quizapp.data.RemoteDataRepository
import com.example.quizapp.data.QuestionApi
import com.example.quizapp.model.Question
import com.example.quizapp.model.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataRepositoryImpl
@Inject constructor(
    private val api: QuestionApi
): RemoteDataRepository {
    override suspend fun getAllQuestion():List<Question> {
        return withContext(Dispatchers.Default){
            api.getAllQuestion()
        }
    }
}