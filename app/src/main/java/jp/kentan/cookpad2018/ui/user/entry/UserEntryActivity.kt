package jp.kentan.cookpad2018.ui.user.entry

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.chip.Chip
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.AdapterView
import dagger.android.AndroidInjection
import jp.kentan.cookpad2018.R
import jp.kentan.cookpad2018.data.GroupRepository
import jp.kentan.cookpad2018.data.component.Food
import jp.kentan.cookpad2018.databinding.ActivityUserEntryBinding
import jp.kentan.cookpad2018.ui.MainActivity
import jp.kentan.cookpad2018.ui.group.detail.GroupDetailActivity
import jp.kentan.cookpad2018.ui.viewmodel.ViewModelFactory
import jp.kentan.cookpad2018.util.saveMyUserName
import org.jetbrains.anko.newTask
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class UserEntryActivity : AppCompatActivity(), UserEntryNavigator {

    companion object {
        private const val GROUP_ID_EXTRA = "group_id"

        fun createIntent(context: Context) = Intent(context, UserEntryActivity::class.java)

        fun createIntent(context: Context, groupId: String?) =
                Intent(context, UserEntryActivity::class.java).apply {
                    if (groupId != null) {
                        putExtra(GROUP_ID_EXTRA, groupId)
                    }
                }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var groupRepository: GroupRepository

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, viewModelFactory).get(UserEntryViewModel::class.java)
    }

    private lateinit var binding: ActivityUserEntryBinding

    private var groupId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_entry)

        title = "ようこそ!"

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_entry)
        binding.setLifecycleOwner(this)

        AndroidInjection.inject(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        groupId = intent.getStringExtra(GROUP_ID_EXTRA)

        viewModel.navigator = this

        binding.viewModel = viewModel
        binding.dislikeFoodEditText.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            viewModel.addDislikeFood(parent.getItemAtPosition(position) as Food) }
    }

    override fun onDestroy() {
        viewModel.navigator = null
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onNameError(messageId: Int) {
        binding.nameTextLayout.apply {
            error = getString(messageId)
            requestFocus()
        }
    }

    override fun onUserCreated(userName: String) {
        val id = groupId

        saveMyUserName(userName)

        if (id != null) {
            groupRepository.joinGroup(userName, id).observe(this, Observer { success ->
                if (success != null && success) {
                    val intent = GroupDetailActivity.createIntent(this, id, userName)
                    startActivity(intent.newTask())
                }

                finish()
            })
        } else {
            startActivity<MainActivity>()
            finish()
        }
    }

    override fun addChip(text: String) {
        val chip = Chip(this).apply {
            setText(text)
        }
        binding.dislikeFoodChipGroup.addView(chip)
        binding.dislikeFoodEditText.setText("")
    }
}
