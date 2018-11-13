package jp.kentan.cookpad2018.ui.group.recipe

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import dagger.android.AndroidInjection
import jp.kentan.cookpad2018.R
import jp.kentan.cookpad2018.data.GroupRepository
import jp.kentan.cookpad2018.data.RecipeRepository
import jp.kentan.cookpad2018.data.component.Food
import jp.kentan.cookpad2018.data.model.Recipe
import jp.kentan.cookpad2018.data.model.User
import kotlinx.android.synthetic.main.activity_recommend_recipe.*
import javax.inject.Inject

class RecommendRecipeActivity : AppCompatActivity() {

    @Inject
    lateinit var groupRepository: GroupRepository

    @Inject
    lateinit var recipeRepository: RecipeRepository

    companion object {
        private const val USER_NAME_EXTRA = "user_name"
        private const val GROUP_ID_EXTRA = "group_id"

        fun createIntent(context: Context, userName: String, groupId: String) =
                Intent(context, RecommendRecipeActivity::class.java).apply {
                    putExtra(USER_NAME_EXTRA, userName)
                    putExtra(GROUP_ID_EXTRA, groupId)
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend_recipe)

        title = "おすすめメニュー"

        AndroidInjection.inject(this)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        val userName = intent.getStringExtra(USER_NAME_EXTRA)
        val groupId = intent.getStringExtra(GROUP_ID_EXTRA)

        if (userName == null || groupId == null) {
            finish()
            return
        }

        val adapter = RecipeRecyclerAdapter(this) { recipe ->
            if (recipe.isLike) {
                groupRepository.removeLike(groupId, userName, recipe)
            } else {
                groupRepository.addLike(groupId, userName, recipe)
            }
        }

        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)

        groupRepository.getGroupById(groupId).observe(this, Observer { group ->
            if (group == null) {
                return@Observer
            }

            val recipes = recipeRepository.getRecipes().map { recipe ->
                val groupRecipe = group.recipes.find { it.id == recipe.id }

                return@map if (groupRecipe != null) {
                    recipe.copy(
                            isLike = groupRecipe.users.contains(userName),
                            likeUserNames = groupRecipe.users
                    )
                } else {
                    recipe
                }
            }

            adapter.submitList(filterRecipes(recipes, group.users))
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    private fun filterRecipes(recipes: List<Recipe>, users: List<User>): List<Recipe> {
        val dislikeFoods: List<Food> = users.map { it.dislikeFoods }.flatten()
        val cookingLevel = if (users.isNotEmpty()) {
            users.sumByDouble { it.frequency.level.toDouble() }.toFloat() / users.size
        } else {
            50f
        }

        return recipes.filterNot { recipe ->
            recipe.foods.any(dislikeFoods::contains)
        }.filter { it.difficult in cookingLevel-25..cookingLevel+25 }
    }
}
