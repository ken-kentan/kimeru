package jp.kentan.cookpad2018.ui.link

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import dagger.android.AndroidInjection
import jp.kentan.cookpad2018.R
import jp.kentan.cookpad2018.data.GroupRepository
import jp.kentan.cookpad2018.ui.group.detail.GroupDetailActivity
import jp.kentan.cookpad2018.ui.user.entry.UserEntryActivity
import jp.kentan.cookpad2018.util.getMyUserName
import javax.inject.Inject

class LinkActivity : AppCompatActivity() {

    @Inject
    lateinit var groupRepository: GroupRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_link)

        AndroidInjection.inject(this)

        var groupId: String? = null
        if (intent.action == Intent.ACTION_VIEW) {
            intent.data?.let { uri ->
                groupId = uri.getQueryParameter("group")
            }
        }

        val userName = getMyUserName()
        if (userName == null) {
            startActivity(UserEntryActivity.createIntent(this, groupId))
            finish()
            return
        }

        if (groupId != null) {
            groupRepository.joinGroup(userName, groupId!!).observe(this, Observer {success ->
                if (success != null && success) {
                    val intent = GroupDetailActivity.createIntent(this, groupId!!, userName, true)
                    startActivity(intent)
                }
                finish()
            })

            return
        }

        finish()
    }
}
