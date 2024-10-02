package com.example.quizapp.ui.viewmodel

import android.util.Log
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

    private val _currentQuestion= MutableStateFlow<Results<Question>>(Results.Idle)
    val currentQuestion=_currentQuestion.asStateFlow()

    private var questions: List<Question> = emptyList()

    private val _quizFinished = MutableStateFlow(false)
    val quizFinished: StateFlow<Boolean> = _quizFinished.asStateFlow()


    private val _isBackButtonEnabled = MutableStateFlow(false)
    val isBackButtonEnabled: StateFlow<Boolean> = _isBackButtonEnabled

    private val _isNextButtonEnabled = MutableStateFlow(true)
    val isNextButtonEnabled: StateFlow<Boolean> = _isNextButtonEnabled

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex

    init {
        getAllQuestion()
        loadQuestion()
    }

    private fun loadQuestion()=viewModelScope.launch {
        useCases.getAllQuestion().onStart {
            _currentQuestion.value= Results.Loading

        }.catch {
            Log.d("result ",it.toString())
            _currentQuestion.value=Results.Error(it)
        }.collect{
            questions->
            if (questions.isNotEmpty()){
                val currQue=questions[_currentQuestionIndex.value]
                _currentQuestion.value=Results.Success(currQue)

            }
        }

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


    fun getAnswers(index: Int): String? {
        return when (val result = _todoResponse.value) {
            is Results.Success -> result.data.getOrNull(index)?.rightAnswer
            else -> null
        }
    }

    fun selectAnswer(questionIndex: Int, selectedAnswerIndex: Int) {
        _todoResponse.value = Results.Success(questions)

    }
    fun previousQuestion() {
        viewModelScope.launch {
            useCases.getAllQuestion().collect { list ->
                val size = list.size
                if (_currentQuestionIndex.value > 0) {
                    _currentQuestionIndex.value--
                    loadQuestion()
                } else {
                    viewModelScope.launch {

                        _currentQuestionIndex.value = size - 1
                        loadQuestion()
                    }
                }
            }
        }
    }

    fun nextQuestion() {
        viewModelScope.launch {
            useCases.getAllQuestion().collect { list ->
                val size = list.size
                if (_currentQuestionIndex.value < size- 1) {
                    _currentQuestionIndex.value++
                    loadQuestion()
                } else {
                    _currentQuestionIndex.value = 0
                    loadQuestion()
                }
            }
        }

    }




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