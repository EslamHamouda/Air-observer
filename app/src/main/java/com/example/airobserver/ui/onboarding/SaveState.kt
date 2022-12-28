package com.example.airobserver.ui.onboarding

import android.content.Context
import android.content.SharedPreferences

class SaveState(var context: Context, var saveName: String) {
    var sp: SharedPreferences = context.getSharedPreferences(saveName, Context.MODE_PRIVATE)

    var state: Int
        get() = sp.getInt("key", 0)
        set(key) {
            val editor = sp.edit()
            editor.putInt("key", key)
            editor.apply()
        }
}