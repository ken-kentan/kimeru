package jp.kentan.cookpad2018.data.model

import android.support.v7.util.DiffUtil
import com.squareup.moshi.Json
import jp.kentan.cookpad2018.data.component.Food

data class Recipe(
        @field:Json(name = "id")
        val id: String,
        @field:Json(name = "name")
        val name: String,
        @field:Json(name = "url")
        val url: String,
        @field:Json(name = "imageUrl")
        val imageUrl: String,
        @field:Json(name = "foods")
        val foods: List<Food>,
        @field:Json(name = "difficult")
        val difficult: Float,
        val isLike: Boolean = false,
        val likeUserNames: List<String> = emptyList()
) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return  oldItem == newItem
            }
        }
    }
}