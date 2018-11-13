package jp.kentan.cookpad2018.ui.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import jp.kentan.cookpad2018.data.GroupRepository
import jp.kentan.cookpad2018.data.UserRepository
import jp.kentan.cookpad2018.ui.user.entry.UserEntryViewModel

class ViewModelFactory(
        private val userRepository: UserRepository,
        private val groupRepository: GroupRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = with(modelClass) {
        when {
            isAssignableFrom(UserEntryViewModel::class.java) ->
                UserEntryViewModel(userRepository)
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}