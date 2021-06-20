package com.example.triviaquizapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "QuizResult")
data class QuestionsResult(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var score:String,
    var results: String)