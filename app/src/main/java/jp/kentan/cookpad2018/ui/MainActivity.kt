package jp.kentan.cookpad2018.ui

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import dagger.android.AndroidInjection
import jp.kentan.cookpad2018.R
import jp.kentan.cookpad2018.data.GroupRepository
import jp.kentan.cookpad2018.ui.group.create.GroupCreateActivity
import jp.kentan.cookpad2018.ui.group.detail.GroupDetailActivity
import jp.kentan.cookpad2018.ui.group.list.GroupRecyclerAdapter
import jp.kentan.cookpad2018.ui.user.entry.UserEntryActivity
import jp.kentan.cookpad2018.util.getMyUserName
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.widget.EditText
import androidx.core.view.isVisible
import jp.kentan.cookpad2018.util.saveMyUserName


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var groupRepository: GroupRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        title = "グループ一覧"

//        startActivity(UserEntryActivity.createIntent(this))
//        startActivity(GroupDetailActivity.createIntent(this, "test", "user1"))
//        startActivity(GroupCreateActivity.createIntent(this, "user3"))

        val userName = getMyUserName()
        if (userName == null) {
            startActivity(UserEntryActivity.createIntent(this))
            finish()
            return
        }

        AndroidInjection.inject(this)

        fab.setOnClickListener { view ->
            startActivity(GroupCreateActivity.createIntent(this, userName))
        }

//        val toggle = ActionBarDrawerToggle(
//                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
//        drawer_layout.addDrawerListener(toggle)
//        toggle.syncState()

//        nav_view.setNavigationItemSelectedListener(this)

        val adapter = GroupRecyclerAdapter(this, userName)

        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)

        groupRepository.getMyGroups(userName).observe(this, Observer { groups ->
            progress_bar.isVisible = false
            adapter.submitList(groups)
        })
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_debug -> {
                val alert = AlertDialog.Builder(this)

                alert.setTitle("Debug ONLY")
                alert.setMessage("Overwrite user name(${getMyUserName()})")

                val input = EditText(this)
                alert.setView(input)

                alert.setPositiveButton("Ok") { _, _ ->
                    val value = input.text.toString()
                    saveMyUserName(value)
                }

                alert.setNegativeButton("Cancel", null)

                alert.show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
