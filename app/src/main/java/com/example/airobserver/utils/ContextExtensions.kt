package com.example.airobserver.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.airobserver.utils.showMessage


fun showProgress(progressBar: ProgressBar) {
    progressBar.visibility = View.VISIBLE
}

fun hideProgress(progressBar: ProgressBar) {
    progressBar.visibility = View.GONE
}

fun Activity.view(): View? =
    findViewById(android.R.id.content)

fun Context.showToast(msg: String) {
    Toast(this).showMessage(this, msg)
}

fun Context.showToast(msgId: Int) {
    Toast(this).showMessage(this, msgId)
}