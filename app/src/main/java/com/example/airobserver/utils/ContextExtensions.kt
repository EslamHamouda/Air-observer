package com.example.airobserver.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.airbnb.lottie.LottieAnimationView


fun showProgress(progressBar: LottieAnimationView) {
    progressBar.visibility = View.VISIBLE
}

fun hideProgress(progressBar: LottieAnimationView) {
    progressBar.visibility = View.GONE
}

fun LottieAnimationView.hideProgressBar() {
    this.visibility = View.GONE
}

fun LottieAnimationView.showProgressBar() {
    this.visibility = View.VISIBLE
}

fun Activity.view(): View? =
    findViewById(android.R.id.content)

fun Context.showToast(msg: String) {
    Toast(this).showMessage(this, msg)
}

fun Context.showToast(msgId: Int) {
    Toast(this).showMessage(this, msgId)
}

@RequiresApi(Build.VERSION_CODES.M)
fun Context.logout(pref: SharedPreferences, activity: Activity) {
    showSnackbar("Logout", activity)
    pref.removeAuthentication()
}