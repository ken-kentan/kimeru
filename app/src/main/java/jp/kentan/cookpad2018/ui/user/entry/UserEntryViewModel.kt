package jp.kentan.cookpad2018.ui.user.entry

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.databinding.adapters.TextViewBindingAdapter
import jp.kentan.cookpad2018.data.GroupRepository
import jp.kentan.cookpad2018.data.UserRepository
import jp.kentan.cookpad2018.data.component.CookingFrequency
import jp.kentan.cookpad2018.data.component.Food
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class UserEntryViewModel(
        private val userRepository: UserRepository
) : ViewModel() {

    private val dislikeFoods = mutableListOf<Food>()

    val name = ObservableField<String>()
    val cookingFrequency = ObservableInt()
    val cookingFreqEntities = CookingFrequency.values().map { it.displayName }
//    val foods: LiveData<List<String>> = Transformations.map(foodRepository.getFoods()) { foods -> foods.map { it.name } }
    val foods: List<Food> = Food.values().sortedBy { it.displayName }
    val loading = ObservableBoolean()
    val enabledNext = ObservableBoolean()
    val errorName = ObservableField<String>()
    val errorMessage = ObservableField<String>()

    var groupId: String? = null
    var navigator: UserEntryNavigator? = null

    val onNameChanged = TextViewBindingAdapter.OnTextChanged { text: CharSequence, _, _, count: Int ->
        if (text.length >= 5) {
            errorName.set(null)
            enabledNext.set(true)
        } else {
            enabledNext.set(false)
        }
    }

    fun onClickNext() {
        val name = name.get()?.trim()

        if (name == null || name.length < 5) {
            errorName.set("5文字以上の英数字で入力してください")
            return
        }

        val freq = CookingFrequency.values()[cookingFrequency.get()]

        launch(UI) {
            loading.set(true)

            userRepository.createUser(name, dislikeFoods, freq) { success ->
                if (success) {
                    navigator?.onUserCreated(name)
                } else {
                    errorMessage.set("そのユーザー名は既に登録されています.")
                    loading.set(false)
                }
            }
        }
    }

    fun addDislikeFood(food: Food) {
        if (dislikeFoods.contains(food)) {
            return
        }

        dislikeFoods.add(food)
        navigator?.addChip(food.displayName)
    }
}