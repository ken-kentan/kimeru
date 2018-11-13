package jp.kentan.cookpad2018.ui.user.entry

import android.support.annotation.StringRes

interface UserEntryNavigator {
    fun onNameError(@StringRes messageId: Int)
    fun addChip(text: String)

    fun onUserCreated(userName: String)
}