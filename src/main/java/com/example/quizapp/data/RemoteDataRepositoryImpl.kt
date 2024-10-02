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
    val question = listOf(
        Question(
            id = 1,
            questionTitle = "What is the capital of France?",
            category = "History",
            option1 = "Berlin",
            option2 = "Paris",
            option3 = "London",
            rightAnswer = "Paris",
            difficultyLevel = "Easy"
        ),
        Question(
            id = 2,
            questionTitle = "Which planet is known as the Red Planet?",
            category = "Science",
            option1 = "Earth",
            option2 = "Mars",
            option3 = "Jupiter",
            rightAnswer = "Mars",
            difficultyLevel = "Medium"
        ),
        Question(
            id = 3,
            questionTitle = "Who painted the famous painting 'The Starry Night'?",
            category = "Art",
            option1 = "Leonardo da Vinci",
            option2 = "Vincent van Gogh",
            option3 = "Pablo Picasso",
            rightAnswer = "Vincent van Gogh",
            difficultyLevel = "Hard"
        ),
        Question(
            id = 4,
            questionTitle = "What is the largest mammal on Earth?",
            category = "Science",
            option1 = "Blue whale",
            option2 = "Fin whale",
            option3 = "Humpback whale",
            rightAnswer = "Blue whale",
            difficultyLevel = "Easy"
        ),
        Question(
            id = 5,
            questionTitle = "Which country is home to the ancient city of Petra?",
            category = "History",
            option1 = "Egypt",
            option2 = "Jordan",
            option3 = "Israel",
            rightAnswer = "Jordan",
            difficultyLevel = "Medium"
        ),
        Question(
            id = 6,
            questionTitle = "Who wrote the famous novel 'To Kill a Mockingbird'?",
            category = "Literature",
            option1 = "F. Scott Fitzgerald",
            option2 = "Harper Lee",
            option3 = "Jane Austen",
            rightAnswer = "Harper Lee",
            difficultyLevel = "Hard"
        ),
        Question(
            id = 7,
            questionTitle = "What is the chemical symbol for gold?",
            category = "Science",
            option1 = "Ag",
            option2 = "Au",
            option3 = "Hg",
            rightAnswer = "Au",
            difficultyLevel = "Easy"
        ),
        Question(
            id = 8,
            questionTitle = "Which musician is known as the 'King of Rock and Roll'?",
            category = "Music",
            option1 = "Elvis Presley",
            option2 = "Chuck Berry",
            option3 = "Little Richard",
            rightAnswer = "Elvis Presley",
            difficultyLevel = "Medium"
        ),
        Question(
            id = 9,
            questionTitle = "What is the largest planet in our solar system?",
            category = "Science",
            option1 = "Earth",
            option2 = "Saturn",
            option3 = "Jupiter",
            rightAnswer = "Jupiter",
            difficultyLevel = "Easy"
        ),
        Question(
            id = 10,
            questionTitle = "Who painted the famous painting 'The Mona Lisa'?",
            category = "Art",
            option1 = "Leonardo da Vinci",
            option2 = "Michelangelo",
            option3 = "Raphael",
            rightAnswer = "Leonardo da Vinci",
            difficultyLevel = "Hard"
        )
    )
    override suspend fun getAllQuestion():List<Question> {
        return withContext(Dispatchers.Default){
//            api.getAllQuestion()
         question
        }
    }
}