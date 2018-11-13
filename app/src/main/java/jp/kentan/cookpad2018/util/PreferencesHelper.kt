package jp.kentan.cookpad2018.util

import android.content.Context
import androidx.core.content.edit
import org.jetbrains.anko.defaultSharedPreferences

fun Context.getMyUserName(): String? = defaultSharedPreferences.getString("USER_NAME", null)

fun Context.saveMyUserName(userName: String) = defaultSharedPreferences.edit {
    putString("USER_NAME", userName)
}