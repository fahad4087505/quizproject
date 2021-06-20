package com.example.triviaquizapp.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBinding

/**
Created By Fahad on 18/06/21
 */
abstract class BaseActivity<B : ViewBinding, R : BaseRepository> : AppCompatActivity() {

    protected lateinit var factory: ViewModelFactory

    protected lateinit var binding: B
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //changeStatusBar()
        binding = DataBindingUtil.setContentView(this, getLayout())
        factory = ViewModelFactory(getRepository())
        onPostInit()
    }

    abstract fun getLayout(): Int

    abstract fun onPostInit()

    abstract fun getRepository(): R

    fun changeStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = Color.WHITE
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}