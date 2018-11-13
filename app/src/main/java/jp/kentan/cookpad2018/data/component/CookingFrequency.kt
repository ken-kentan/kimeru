package jp.kentan.cookpad2018.data.component

enum class CookingFrequency(
        val displayName: String,
        val level: Float
) {
    EVERY_DAY("ほぼ毎日", 100f),
    FIVE_DAY_PER_WEEK("週4〜5日", 80f),
    TWO_DAY_PER_WEEK("週2〜3日", 60f),
    ONE_DAY_PER_WEEK("週1日", 40f),
    MONTH("月数回", 20f),
    NOTHING("ほとんどしない", 0f)
}