package jp.kentan.cookpad2018.data.dao

import java.sql.Connection
import java.sql.DriverManager

abstract class BaseDao {

    private companion object {
        const val URI = "jdbc:mariadb://kentan.jp/cookpad2018"
        const val USER = "cookpad2018"
        const val PASS = "PM0B4G6xnUIOsxkR"
    }

    val connection: Connection
        get() = DriverManager.getConnection(URI, USER, PASS)
}