package jp.kentan.cookpad2018.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.google.firebase.database.*
import jp.kentan.cookpad2018.data.model.Group
import jp.kentan.cookpad2018.data.model.GroupRecipe
import jp.kentan.cookpad2018.data.model.Recipe
import jp.kentan.cookpad2018.data.model.User
import jp.kentan.cookpad2018.util.getRandomGroupImageUrl

class GroupRepository {

    private val database = FirebaseDatabase.getInstance()

    fun getMyGroups(userName: String): LiveData<List<Group>> {
        val result = MutableLiveData<List<Group>>()

        database.reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val groups = mutableListOf<Group>()

                snapshot.child("groups").children.forEach { groupSnapshot ->
//                    val groupId = groupSnapshot.key ?: return@forEach

                    val id = groupSnapshot.child("id").value as String
                    val name = groupSnapshot.child("name").value as String
                    val imageUrl = groupSnapshot.child("imageUrl").value as String

                    val userNames = groupSnapshot.child("users").children.map { it.value as String }
                    val users = snapshot.child("users").children.mapNotNull { it.getValue(User::class.java) }

                    val recipes = mutableListOf<GroupRecipe>()

                    groupSnapshot.child("recipes").children.forEach { snap ->
                        val recipeId = snap.key
                        if (recipeId != null) {
                            recipes.add(GroupRecipe(recipeId, snap.children.mapNotNull { it.key }))
                        }
                    }

                    groups.add(
                            Group(id, name, imageUrl, users.filter { userNames.contains(it.name) }, recipes))
                }

                result.value = groups.filter { group ->
                    group.users.any { it.name == userName }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                result.value = null
                Log.d(javaClass.simpleName, error.message)
            }
        })

        return result
    }

    fun getGroupById(groupId: String): LiveData<Group> {
        val result = MutableLiveData<Group>()

        database.reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val groupSnapshot = snapshot.child("groups").child(groupId)
                if (!groupSnapshot.hasChildren()) {
                    result.value = null
                    return
                }

                val id = groupSnapshot.child("id").value as String
                val name = groupSnapshot.child("name").value as String
                val imageUrl = groupSnapshot.child("imageUrl").value as String

                val userNames = groupSnapshot.child("users").children.map { it.value as String }
                val users = snapshot.child("users").children.mapNotNull { it.getValue(User::class.java) }

                val recipes = mutableListOf<GroupRecipe>()

                groupSnapshot.child("recipes").children.forEach { snap ->
                    val recipeId = snap.key ?: return@forEach
                    recipes.add(GroupRecipe(recipeId, snap.children.mapNotNull { it.key }))
                }

                result.value = Group(id, name, imageUrl, users.filter { userNames.contains(it.name) }, recipes)
            }

            override fun onCancelled(error: DatabaseError) {
                result.value = null
                Log.d(javaClass.simpleName, error.message)
            }
        })

        return result
    }

    fun createGroup(groupId: String, userName: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        database.reference.child("groups")
                .child(groupId)
                .runTransaction(object : Transaction.Handler{
                    override fun doTransaction(mutableData: MutableData): Transaction.Result {
                        if (mutableData.hasChildren()) {
                            return Transaction.abort()
                        }

                        mutableData.child("id").value = groupId
                        mutableData.child("name").value = groupId
                        mutableData.child("users").value = listOf(userName)
                        mutableData.child("imageUrl").value = getRandomGroupImageUrl()

                        return Transaction.success(mutableData)
                    }

                    override fun onComplete(error: DatabaseError?, b: Boolean, snapshot: DataSnapshot?) {
                        result.value = error == null
                    }
                })

        return result
    }

    fun joinGroup(userName: String, groupId: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        database.reference.child("groups")
                .child(groupId)
                .child("users")
                .runTransaction(object : Transaction.Handler{
                    override fun doTransaction(mutableData: MutableData): Transaction.Result {
                        val userNames = mutableData.children.map { it.value as String }.toMutableList()

                        if (!userNames.contains(userName)) {
                            userNames.add(userName)
                            mutableData.value = userNames
                        }

                        return Transaction.success(mutableData)
                    }

                    override fun onComplete(error: DatabaseError?, b: Boolean, snapshot: DataSnapshot?) {
                        result.value = error == null
                    }
                })

        return result
    }

    fun addLike(groupId: String, userName: String, recipe: Recipe) {
        val ref = database.reference.child("groups")
                .child(groupId)
                .child("recipes")

        ref.child(recipe.id).child(userName).setValue(true)
    }

    fun removeLike(groupId: String, userName: String, recipe: Recipe) {
        val ref = database.reference.child("groups")
                .child(groupId)
                .child("recipes")

        ref.child(recipe.id).child(userName).removeValue()
    }
}