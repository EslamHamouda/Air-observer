package com.example.airobserver.utils

import android.content.SharedPreferences
import com.example.airobserver.di.SharedPref
import com.google.gson.Gson


fun SharedPreferences.putData(key: String, value: String) {
    edit()?.putString(key, value)?.apply()
}

fun SharedPreferences.putData(key: String, value: Float) {
    edit()?.putFloat(key, value)?.apply()
}

fun SharedPreferences.putData(key: String, value: Boolean) {
    edit()?.putBoolean(key, value)?.apply()
}

fun <T> SharedPreferences.putData(key: String, value: T) {
    edit()?.putString(key, Gson().toJson(value))?.apply()
}

fun SharedPreferences.getString(key: String): String? {
    return getString(key, null)
}

fun SharedPreferences.getBoolean(key: String): Boolean {
    return getBoolean(key, false)
}

fun SharedPreferences.getFloat(key: String): Float {
    return getFloat(key, 0.0F)
}

fun SharedPreferences.removeData(key: String) {
    edit()?.remove(key)?.apply()
}

fun SharedPreferences.removeAuthentication() = run {
    removeData(SharedPref.IS_LOGIN)
    removeData(SharedPref.EMAIL)
}