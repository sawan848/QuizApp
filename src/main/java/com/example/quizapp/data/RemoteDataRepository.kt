package com.example.quizapp.data

import com.example.quizapp.model.Question

interface RemoteDataRepository {
    suspend fun getAllQuestion():List<Question>
}