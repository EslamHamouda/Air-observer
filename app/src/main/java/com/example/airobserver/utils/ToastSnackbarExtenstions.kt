package com.example.airobserver.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.airobserver.R
import com.google.android.material.snackbar.Snackbar

fun Toast.showMessage(context: Context, msg: String) =
    Toast.makeText(context, msg, LENGTH_LONG).show()

fun Toast.showMessage(context: Context, msgId: Int) =
    Toast.makeText(context, msgId, LENGTH_LONG).show()

fun Fragment.hideKeyBoard() =
    (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
        view?.windowToken,
        0
    )

@RequiresApi(Build.VERSION_CODES.M)
fun showSnackbar(msg: String, activity: Activity, tryAgain: () -> Unit) {
    activity.view()?.let {
        Snackbar.make(it, msg, Snackbar.LENGTH_LONG)
            .setBackgroundTint(it.context.getColor(R.color.red))
            .setActionTextColor(it.context.getColor(R.color.white))
            .setAction(it.context.getString(R.string.try_again)) {
                tryAgain()
            }
            .show()
    }
}

@RequiresApi(Build.VERSION_CODES.M)
fun showSnackbar(msg: String, activity: Activity) {
    activity.view()?.let {
        Snackbar.make(it, msg, Snackbar.LENGTH_LONG)
            .setBackgroundTint(it.context.getColor(R.color.red))
            .setActionTextColor(it.context.getColor(R.color.white))
            .show()
    }
}

@RequiresApi(Build.VERSION_CODES.M)
fun showSnackBar(msg: String, view: View) {
    view.let {
        Snackbar.make(it, msg, Snackbar.LENGTH_LONG)
            .setBackgroundTint(it.context.getColor(R.color.red))
            .setActionTextColor(it.context.getColor(R.color.white))
            .show()
    }
}

@RequiresApi(Build.VERSION_CODES.M)
fun showSnackBar(msg: String, view: View, tryAgain: () -> Unit) {
    view.let {
        Snackbar.make(it, msg, Snackbar.LENGTH_LONG)
            .setBackgroundTint(it.context.getColor(R.color.red))
            .setActionTextColor(it.context.getColor(R.color.white))
            .setAction(it.context.getString(R.string.try_again)) {
                tryAgain()
            }
            .show()
    }
}
