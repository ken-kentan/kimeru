package jp.kentan.cookpad2018.util

import java.net.URLEncoder

fun makeGroupLink(groupId: String): String {
    val id = URLEncoder.encode(groupId, "UTF-8")
    return "http://cookpad2018.com?group=$id"
}