package com.example.quizapp.data


import com.example.quizapp.model.Question
import com.example.quizapp.model.Todo
import com.example.quizapp.util.Constant
import retrofit2.http.GET

interface QuestionApi {
    @GET(Constant.GET_ALL_QUESTIONS)
    suspend fun getAllQuestion():List<Question>
}