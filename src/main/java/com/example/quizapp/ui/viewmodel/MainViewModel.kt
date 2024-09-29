package com.example.quizapp.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.model.Question
import com.example.quizapp.use_case.QuestionUseCases
import com.example.quizapp.util.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject
constructor(
    private val useCases: QuestionUseCases
):ViewModel(){

    private val _todoResponse= MutableStateFlow<Results<List<Question>>>(Results.Idle)
    val response=_todoResponse.asStateFlow()
//    private var currentQuestionIndex = 0

    private var questions: List<Question> = emptyList()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    private val _quizFinished = MutableStateFlow(false)
    val quizFinished: StateFlow<Boolean> = _quizFinished.asStateFlow()
    init {
        getAllQuestion()
    }



    private fun getAllQuestion()=viewModelScope.launch {
        useCases.getAllQuestion().onStart {
            _todoResponse.value= Results.Loading
        }.catch {
            Log.d("result ",it.toString())
            _todoResponse.value=Results.Error(it)
        }.collect{
           val result=it
            questions=result
            _todoResponse.value=Results.Success(result)
        }
    }


    fun selectAnswer(questionIndex: Int, selectedAnswerIndex: Int) {
        _todoResponse.value = Results.Success(questions)

    }
//    fun answerQuestion(selectedAnswer: String) {
//        val currentQuestion =questions.getOrNull(_currentQuestionIndex.value)
//
//        if (currentQuestion != null) {
//            // Check if the answer is correct and update the score
//            if (currentQuestion.rightAnswer==selectedAnswer) {
//                _score.value = _score.value + 1
//            }
//
//            // Move to the next question
//            _currentQuestionIndex.value += 1
//
//            // Check if we've reached the end of the quiz
//            if (_currentQuestionIndex.value >= questions.size) {
//                _quizFinished.value = true
//                _todoResponse.value = Results.Finished(
//                    totalQuestions = questions.size,
//                    correctAnswers = _score.value
//                )
//            } else {
//                // If not finished, update UI with the next question
//                _todoResponse.value = Results.Success(questions)
//            }
//        } else {
//            // Handle the case where we're somehow out of questions
//            _todoResponse.value = Results.Error( Throwable("No more questions available"))
//        }
//    }
//
//    fun restartQuiz() {
//        _currentQuestionIndex.value = 0
//        _score.value = 0
//        _quizFinished.value = false
//        if (questions.isNotEmpty()) {
//            _todoResponse.value = Results.Success(questions)
//        } else {
//            getAllQuestion()
//        }
//    }
}