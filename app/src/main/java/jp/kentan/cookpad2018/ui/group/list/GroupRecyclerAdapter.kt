package jp.kentan.cookpad2018.ui.group.list

import android.content.Context
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import jp.kentan.cookpad2018.R
import jp.kentan.cookpad2018.data.model.Group
import jp.kentan.cookpad2018.ui.group.detail.GroupDetailActivity
import kotlinx.android.synthetic.main.item_group_list.view.*

class GroupRecyclerAdapter(
        private val context: Context,
        private val userName: String
) : ListAdapter<Group, GroupRecyclerAdapter.ViewHolder>(Group.DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, layoutType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)

        val view = layoutInflater.inflate(R.layout.item_group_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class ViewHolder(
            private val view: View
    ) : RecyclerView.ViewHolder(view) {
        fun bind(group: Group) {
            view.name_text.text = group.name
            Picasso.with(context).load(group.imageUrl).into(view.image_view)

            val sb = StringBuilder()
            group.users.forEach {
                sb.append(it.name)
                sb.append(", ")
            }
            if (group.users.isNotEmpty()) {
                sb.delete(sb.length-2, sb.length)
            }

            view.users_text.text = sb.toString()

            view.layout.setOnClickListener {
                val intent = GroupDetailActivity.createIntent(view.context, group.id, userName)
                context.startActivity(intent)
            }
        }
    }
}