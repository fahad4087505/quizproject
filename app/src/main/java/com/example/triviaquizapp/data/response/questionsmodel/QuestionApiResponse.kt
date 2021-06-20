package com.example.triviaquizapp.data.response.questionsmodel

data class QuestionApiResponse(
    val response_code: Int,
    val results: List<Result>)