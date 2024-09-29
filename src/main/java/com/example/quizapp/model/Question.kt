package com.example.quizapp.model

import android.icu.text.CaseMap.Title
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Question(
    @Json(name = "id")

    var id:Int,
    @Json(name = "questionTitle")

    var questionTitle: String?="",
    @Json(name = "category")
    var category: String?="",
    @Json(name = "option1")

    var option1: String?="",
    @Json(name = "option2")

    var option2: String?="",
    @Json(name = "option3")

    var option3: String?="",
    @Json(name = "rightAnswer")

    var rightAnswer: String?="",
    @Json(name = "difficultyLevel")

    var difficultyLevel: String?=""


) {

}