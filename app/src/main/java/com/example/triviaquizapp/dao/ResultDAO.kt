package com.example.triviaquizapp.dao

import androidx.room.*
import com.example.triviaquizapp.entity.QuestionsResult

@Dao
interface RoutineDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuiz(routine: QuestionsResult)

    @Update
    fun updateQuiz(routine: QuestionsResult)

    @Delete
    fun deleteQuiz(routine: QuestionsResult)

    @Query("SELECT * FROM QuizResult")
    fun getQuestions(): List<QuestionsResult>
}