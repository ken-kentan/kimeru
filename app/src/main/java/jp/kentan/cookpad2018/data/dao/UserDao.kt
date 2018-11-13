package jp.kentan.cookpad2018.data.dao

import com.squareup.moshi.Moshi
import jp.kentan.cookpad2018.data.model.User

class UserDao : BaseDao() {

    private companion object {
        const val TABLE = "user"
    }

    private val adapter = Moshi.Builder().build().adapter(User::class.java)

    fun getUser(id: String): User? {
        return null
    }

    fun getUsers(): List<User> {
        try {
            val jsonList = mutableListOf<String>()

            connection.use { conn ->
                val st = conn.prepareStatement("SELECT * FROM user")
                val result = st.executeQuery()

                while (result.next()) {
                    jsonList.add(result.getString("json"))
                }
            }

            return jsonList.mapNotNull { adapter.fromJson(it) }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return emptyList()
    }

    fun createUser(user: User): Pair<Boolean, String?> {
        try {
            connection.use { conn ->
                val st = conn.prepareStatement("INSERT INTO $TABLE VALUES(null, ?, ?, NOW(), NOW())")

                st.setString(1, user.name)
                st.setString(2, adapter.toJson(user))

                return Pair(st.executeUpdate() != 0, null)
            }
        } catch (e: Exception) {
            e.printStackTrace()

            return Pair(false, e.message)
        }
    }
}