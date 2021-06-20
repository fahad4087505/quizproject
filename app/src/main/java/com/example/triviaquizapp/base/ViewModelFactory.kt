package com.example.triviaquizapp.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.triviaquizapp.repository.CategoriesListRepository
import com.example.triviaquizapp.repository.DifficultyLevelListRepository
import com.example.triviaquizapp.repository.QuestionsRepository
import com.example.triviaquizapp.ui.categories.CategoriesListViewModel
import com.example.triviaquizapp.ui.difficultylevel.DifficultyListViewModel
import com.example.triviaquizapp.ui.questions.QuestionsViewModel
import javax.inject.Inject

/**
Created By Fahad on 18/06/21
 */
class ViewModelFactory @Inject constructor(private val repository: BaseRepository , private val paramters: Any ?= null) :
    ViewModelProvider.Factory {
    // add your view models here
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
          //  example below
            modelClass.isAssignableFrom(CategoriesListViewModel::class.java) -> CategoriesListViewModel(repository   as CategoriesListRepository) as T
            modelClass.isAssignableFrom(DifficultyListViewModel::class.java) -> DifficultyListViewModel(repository   as DifficultyLevelListRepository) as T
            modelClass.isAssignableFrom(QuestionsViewModel::class.java) -> QuestionsViewModel(repository   as QuestionsRepository) as T
            else -> throw IllegalAccessException("Unknown View Model.Please add your view model in factory")
        }
    }
}