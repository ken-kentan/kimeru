package jp.kentan.cookpad2018.ui.group.detail

import android.content.Context
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import jp.kentan.cookpad2018.R
import jp.kentan.cookpad2018.data.model.User
import kotlinx.android.synthetic.main.item_user_grid.view.*

class UserRecyclerAdapter(
        private val context: Context
) : ListAdapter<User, UserRecyclerAdapter.ViewHolder>(User.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)

        val view = layoutInflater.inflate(R.layout.item_user_grid, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class ViewHolder(
            private val view: View
    ) : RecyclerView.ViewHolder(view) {
        fun bind(user: User) {
            view.name_text.text = user.name
            if (user.iconUrl.isNotEmpty()) {
                Picasso.with(view.context).load(user.iconUrl).into(view.icon_image)
            } else {
                view.icon_image.setImageResource(R.drawable.ic_person)
            }
        }
    }
}