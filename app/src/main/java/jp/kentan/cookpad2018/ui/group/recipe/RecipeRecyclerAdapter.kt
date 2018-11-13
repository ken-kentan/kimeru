package jp.kentan.cookpad2018.ui.group.recipe

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import jp.kentan.cookpad2018.R
import jp.kentan.cookpad2018.data.model.Recipe
import kotlinx.android.synthetic.main.item_recipe_card.view.*

class RecipeRecyclerAdapter(
        private val context: Context,
        private val onLikeClick: (Recipe) -> Unit
) : ListAdapter<Recipe, RecipeRecyclerAdapter.ViewHolder>(Recipe.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)

        val view = layoutInflater.inflate(R.layout.item_recipe_card, parent, false)

        return ViewHolder(view, onLikeClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    class ViewHolder(
            private val view: View,
            private val onLikeClick: (Recipe) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        fun bind(recipe: Recipe) {
            view.name_text.text = recipe.name

            view.card_layout.setOnClickListener {
                val uri = Uri.parse(recipe.url)
                view.context.startActivity(Intent(Intent.ACTION_VIEW, uri))
            }

            if (recipe.imageUrl.isNotBlank()) {
                Picasso.with(view.context).load(recipe.imageUrl).into(view.image_view)
            }

            val sb = StringBuilder()
            recipe.likeUserNames.forEach {
                sb.append(it)
                sb.append(", ")
            }
            if (recipe.likeUserNames.isNotEmpty()) {
                sb.delete(sb.length-2, sb.length)
            }

            view.like_users_text.text = sb.toString()

            view.like_button.apply {
                setOnClickListener { onLikeClick(recipe) }
                text = recipe.likeUserNames.size.toString()

                if (recipe.isLike) {
                    setIconResource(R.drawable.ic_like)
                } else {
                    setIconResource(R.drawable.ic_like_border)
                }
            }
        }
    }
}