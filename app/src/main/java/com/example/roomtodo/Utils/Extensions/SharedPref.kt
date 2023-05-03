package com.example.roomtodo.Utils.Extensions

import android.content.Context
import com.example.roomtodo.Utils.Extensions.Constants.PREF_FILE
import com.example.roomtodo.Utils.Extensions.Constants.PREF_USER
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPref @Inject constructor(@ApplicationContext context: Context) {
    private var pref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)

    fun saveUser(user: String) {
        val editor = pref.edit()
        editor.putString(PREF_USER, user)
        editor.apply()
    }

    fun getUser(): String? {
        return pref.getString(PREF_USER, null)
    }

    fun resetPref(){
        pref.edit().clear().apply()
    }
}
