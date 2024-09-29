package com.example.quizapp.use_case

import com.example.quizapp.data.RemoteDataRepository
import com.example.quizapp.model.Question
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllQuestionUseCase @Inject
constructor(
    private val repository: RemoteDataRepository
){
    suspend operator fun invoke():Flow<List<Question>> = flow {
        emit(repository.getAllQuestion())
    }

}
