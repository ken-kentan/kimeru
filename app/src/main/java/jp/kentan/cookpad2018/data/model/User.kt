package jp.kentan.cookpad2018.data.model

import android.support.v7.util.DiffUtil
import com.squareup.moshi.Json
import jp.kentan.cookpad2018.data.component.CookingFrequency
import jp.kentan.cookpad2018.data.component.Food

data class User(
        @field:Json(name = "name")
        val name: String = "",
        @field:Json(name = "dislike_foods")
        val dislikeFoods: List<Food> = emptyList(),
        @field:Json(name = "frequency")
        val frequency: CookingFrequency = CookingFrequency.EVERY_DAY,
        @field:Json(name = "icon_url")
        val iconUrl: String = ""
) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return  oldItem == newItem
            }
        }
    }
}