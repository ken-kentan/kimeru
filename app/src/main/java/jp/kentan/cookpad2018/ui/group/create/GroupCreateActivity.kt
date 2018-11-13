package jp.kentan.cookpad2018.ui.group.create

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.isVisible
import dagger.android.AndroidInjection
import jp.kentan.cookpad2018.R
import jp.kentan.cookpad2018.data.GroupRepository
import jp.kentan.cookpad2018.ui.group.detail.GroupDetailActivity
import kotlinx.android.synthetic.main.activity_group_create.*
import org.jetbrains.anko.longToast
import javax.inject.Inject

class GroupCreateActivity : AppCompatActivity() {

    companion object {
        private const val USER_NAME_EXTRA = "user_name"

        fun createIntent(context: Context, userName: String) =
                Intent(context, GroupCreateActivity::class.java).apply {
                    putExtra(USER_NAME_EXTRA, userName)
                }
    }

    @Inject
    lateinit var groupRepository: GroupRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_create)

        AndroidInjection.inject(this)

        title = "グループ作成"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val userName = intent.getStringExtra(USER_NAME_EXTRA)
        if (userName == null) {
            finish()
            return
        }

        create_button.setOnClickListener {
            val groupName = (name_edit_text.text?.trim() ?: "").toString()

            if (groupName.length < 5) {
                name_text_layout.error = "グループ名は5文字以上で入力してください"
                return@setOnClickListener
            }

            progress_bar.isVisible = true
            scroll_view.isVisible = false

            groupRepository.createGroup(groupName, userName).observe(this, Observer { success ->
                if (success != null && success) {
                    val intent = GroupDetailActivity.createIntent(this, groupName, userName, isPopupShare = true)
                    startActivity(intent)
                    finish()
                } else {
                    progress_bar.isVisible = false
                    scroll_view.isVisible = true
                    longToast("そのグループ名は既に存在します")
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}
