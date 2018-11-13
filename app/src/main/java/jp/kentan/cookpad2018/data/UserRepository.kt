package jp.kentan.cookpad2018.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import jp.kentan.cookpad2018.data.component.CookingFrequency
import jp.kentan.cookpad2018.data.component.Food
import jp.kentan.cookpad2018.data.dao.UserDao
import jp.kentan.cookpad2018.data.model.User
import jp.kentan.cookpad2018.util.getRandomUserIcon
import org.jetbrains.anko.coroutines.experimental.bg
import java.util.logging.Handler

class UserRepository {
    private val userDao = UserDao()
    private val database = FirebaseDatabase.getInstance()

    fun getUserById(id: String): LiveData<User> {
        val result = MutableLiveData<User>()

        bg { result.postValue(userDao.getUser(id)) }

        return result
    }

    fun getUsers(): LiveData<List<User>> {
        val result = MutableLiveData<List<User>>()

        bg { result.postValue(userDao.getUsers()) }

        return result
    }

    fun createUser(name: String, foods: List<Food>, freq: CookingFrequency, callback: (Boolean) -> Unit) {
        val user = User(
                name = name,
                dislikeFoods = foods,
                frequency = freq,
                iconUrl = getRandomUserIcon()
        )

        database.reference.child("users").child(name)
                .runTransaction( object : Transaction.Handler {
            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                if (mutableData.hasChildren()) {
                    return Transaction.abort()
                }

                mutableData.value = user
                return Transaction.success(mutableData)
            }

            override fun onComplete(error: DatabaseError?, success: Boolean, snapshot: DataSnapshot?) {
                callback(success)
            }
        })
    }
}