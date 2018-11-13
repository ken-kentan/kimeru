package jp.kentan.cookpad2018.data.model

import android.support.v7.util.DiffUtil

data class Group(
        val id: String = "",
        val name: String = "",
        val imageUrl: String,
        val users: List<User> = emptyList(),
        val recipes: List<GroupRecipe> = emptyList()
) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Group>() {
            override fun areItemsTheSame(oldItem: Group, newItem: Group): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Group, newItem: Group): Boolean {
                return  oldItem == newItem
            }
        }
    }
}