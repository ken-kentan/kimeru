package jp.kentan.cookpad2018.util

import java.util.*

private val random = Random(System.currentTimeMillis())

private val groupImageUrls = listOf(
        "https://campus-hub.jp/wp-content/uploads/2018/04/alcohol-merit-demerit.jpg",
        "https://blog.kentan.jp/wp-content/uploads/2017/08/World_of_Color_Update.png"
)
fun getRandomGroupImageUrl(): String = "https://blog.kentan.jp/wp-content/uploads/2018/09/nomikai.jpg" //groupImageUrls[random.nextInt(groupImageUrls.size)]

//private val userIconUrls = listOf(
//        "https://blog.kentan.jp/wp-content/uploads/2018/09/avator.jpg",
//        ""
//)
fun getRandomUserIcon(): String = "" //userIconUrls[random.nextInt(userIconUrls.size)]