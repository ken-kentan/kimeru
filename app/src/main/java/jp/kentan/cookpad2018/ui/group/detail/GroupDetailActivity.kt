package jp.kentan.cookpad2018.ui.group.detail

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import jp.kentan.cookpad2018.R
import jp.kentan.cookpad2018.data.GroupRepository
import jp.kentan.cookpad2018.ui.group.recipe.RecommendRecipeActivity
import jp.kentan.cookpad2018.util.makeGroupLink
import kotlinx.android.synthetic.main.activity_group_detail.*
import kotlinx.android.synthetic.main.content_group_detail.*
import org.jetbrains.anko.share
import javax.inject.Inject

class GroupDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var groupRepository: GroupRepository

    companion object {
        private const val GROUP_ID_EXTRA = "group_id"
        private const val USER_NAME_EXTRA = "user_name"
        private const val IS_NEW_USER = "is_new_user"
        private const val IS_POPUP_SHARE = "is_popup_share"

        fun createIntent(context: Context, groupId: String, userName: String, isNewUser: Boolean = false, isPopupShare: Boolean= false) =
                Intent(context, GroupDetailActivity::class.java).apply {
                    putExtra(GROUP_ID_EXTRA, groupId)
                    putExtra(USER_NAME_EXTRA, userName)
                    putExtra(IS_NEW_USER, isNewUser)
                    putExtra(IS_POPUP_SHARE, isPopupShare)
                }
    }

    private var groupId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_detail)

        setSupportActionBar(toolbar)

        AndroidInjection.inject(this)

        val groupId = intent.getStringExtra(GROUP_ID_EXTRA)
        val userName = intent.getStringExtra(USER_NAME_EXTRA)
        if (groupId == null || userName == null) {
            finish()
            return
        }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = groupId
        }


        val adapter = UserRecyclerAdapter(this)

        member_recycler_view.adapter = adapter
        member_recycler_view.layoutManager = GridLayoutManager(this, 4)
        member_recycler_view.isNestedScrollingEnabled = false

        groupRepository.getGroupById(groupId).observe(this, Observer { group ->
            if (group == null || !group.users.any { it.name == userName }) {
                finish()
                return@Observer
            }

            toolbar_layout.title = group.name
            toolbar_layout.isTitleEnabled = true

            Picasso.with(this).load(group.imageUrl).into(image_view)

            adapter.submitList(group.users)

            reccomend_button.setOnClickListener {
                val intent = RecommendRecipeActivity.createIntent(this, userName, group.id)
                startActivity(intent)
            }
        })

        if (intent.getBooleanExtra(IS_NEW_USER, false)) {
            Snackbar.make(reccomend_button, "グループに参加しました", Snackbar.LENGTH_SHORT).show()
        }
        if (intent.getBooleanExtra(IS_POPUP_SHARE, false)) {
            share(makeGroupLink(groupId))
        }

        this.groupId = groupId
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_share, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> {
                groupId?.let { id ->
                    share(makeGroupLink(id), "Kimeru")
                }
            }
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
