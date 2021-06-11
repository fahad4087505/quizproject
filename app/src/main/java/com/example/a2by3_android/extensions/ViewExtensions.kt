package com.example.a2by3_android.extensions

import android.view.View

fun View.hide() {
  this.visibility = View.GONE
}

fun View.invisible() {
  this.visibility = View.INVISIBLE
}

fun View.show() {
  this.visibility = View.VISIBLE
}