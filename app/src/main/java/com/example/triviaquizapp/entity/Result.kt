package com.example.triviaquizapp.entity

import androidx.room.Entity

@Entity
data class Result(
    var category: String,
    var correct_answer: String,
    var difficulty: String,
    var incorrect_answers: List<String>,
    var question: String,
    var type: String)