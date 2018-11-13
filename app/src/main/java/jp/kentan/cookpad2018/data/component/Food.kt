package jp.kentan.cookpad2018.data.component

enum class Food(
        val displayName: String
) {
    NIRA("にら"),
    SYOUGA("生姜"),
    BUTANIKU("豚肉"),
    NEGI("ねぎ"),
    KYABETU("キャベツ"),
    TAKO("たこ"),
    GREEN_PEPPER("ピーマン"),
    RAMU("ラム"),
    ASUPARA("アスパラ"),
    TAMANEGI("玉ねぎ"),
    MAGURO("マグロ"),
    SA_MON("サーモン"),
    HOTATE("ホタテ"),
    EGG("たまご"),
    CHI_ZU("チーズ"),
    BUROXTUKORI("ブロッコリー"),
    NINJIN("にんじん"),
    YAMAIMO("山芋"),
    TORINIKU("鶏肉"),
    YO_GURUTO("ヨーグルト"),
    TOMATO("トマト")
    ;

    override fun toString() = displayName
}