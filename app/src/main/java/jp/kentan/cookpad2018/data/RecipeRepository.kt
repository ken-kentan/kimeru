package jp.kentan.cookpad2018.data

import jp.kentan.cookpad2018.data.component.Food
import jp.kentan.cookpad2018.data.model.Recipe

class RecipeRepository {

    private val sampleRecipes: List<Recipe>

    init {
        val gyouza = Recipe(
                id = "gyouza",
                name = "餃子",
                url = "https://cookpad.com/recipe/5089526",
                imageUrl = "https://blog.kentan.jp/wp-content/uploads/2018/09/gyouza.jpg",
                foods = listOf(Food.NIRA, Food.SYOUGA, Food.BUTANIKU),
                difficult = 30f)

        val takoyaki = Recipe(
                id = "takoyaki",
                name = "たこ焼き",
                url = "https://cookpad.com/recipe/5023472",
                imageUrl = "https://blog.kentan.jp/wp-content/uploads/2018/09/takoyaki.jpg",
                foods = listOf(Food.NEGI, Food.KYABETU, Food.TAKO),
                difficult = 50f)

        val ramu = Recipe(
                id = "ramu",
                name = "ラムと野菜のオーブン焼き",
                url = "https://cookpad.com/recipe/5168672",
                imageUrl = "https://blog.kentan.jp/wp-content/uploads/2018/09/ramu.jpg",
                foods = listOf(Food.GREEN_PEPPER, Food.RAMU, Food.ASUPARA),
                difficult = 20f)

        val chickenPizza = Recipe(
                id = "chikien_pizza",
                name = "チキンピザ",
                url = "https://cookpad.com/recipe/5128053",
                imageUrl = "https://blog.kentan.jp/wp-content/uploads/2018/09/chikinpizza.jpg",
                foods = listOf(Food.GREEN_PEPPER, Food.TORINIKU, Food.TAMANEGI),
                difficult = 70f)

        val kaisendon = Recipe(
                id = "kaisendon",
                name = "海鮮丼",
                url = "https://cookpad.com/recipe/5119676",
                imageUrl = "https://blog.kentan.jp/wp-content/uploads/2018/09/kaisendon.jpg",
                foods = listOf(Food.MAGURO, Food.SA_MON, Food.HOTATE),
                difficult = 10f)

        val rorukyabetu = Recipe(
                id = "rorukyabetu",
                name = "ロールキャベツ",
                url = "https://cookpad.com/recipe/4417721",
                imageUrl = "https://blog.kentan.jp/wp-content/uploads/2018/09/rollkyabetu.jpg",
                foods = listOf(Food.NINJIN, Food.KYABETU, Food.EGG),
                difficult = 80f)

        val chizu = Recipe(
                id = "chizu",
                name = "チーズフォンデュ",
                url = "https://cookpad.com/recipe/5139345",
                imageUrl = "https://blog.kentan.jp/wp-content/uploads/2018/09/chi-zu.jpg",
                foods = listOf(Food.CHI_ZU, Food.BUROXTUKORI, Food.NINJIN),
                difficult = 0f)

        val okonomiyaki = Recipe(
                id = "okonomiyaki",
                name = "お好み焼き",
                url = "https://cookpad.com/recipe/5246872",
                imageUrl = "https://blog.kentan.jp/wp-content/uploads/2018/09/okonomiyaki.jpg",
                foods = listOf(Food.YAMAIMO, Food.KYABETU, Food.BUTANIKU),
                difficult = 40f)

        val chikenCarry = Recipe(
                id = "chikencare",
                name = "チキンカレー",
                url = "https://cookpad.com/recipe/5240538",
                imageUrl = "https://blog.kentan.jp/wp-content/uploads/2018/09/chikinkare-.jpg",
                foods = listOf(Food.TORINIKU, Food.TAMANEGI, Food.YO_GURUTO),
                difficult = 60f)

        val aqua = Recipe(
                id = "aqua",
                name = "アクアパッツァ",
                url = "https://cookpad.com/recipe/5020950",
                imageUrl = "https://blog.kentan.jp/wp-content/uploads/2018/09/aqua.jpg",
                foods = listOf(Food.TOMATO),
                difficult = 90f)

        val nikuman = Recipe(
                id = "nikuman",
                name = "肉まん",
                url = "https://cookpad.com/recipe/5039129",
                imageUrl = "https://blog.kentan.jp/wp-content/uploads/2018/09/nikuman.jpg",
                foods = listOf(Food.BUTANIKU, Food.TAMANEGI, Food.SYOUGA),
                difficult = 100f)

        sampleRecipes = listOf(
                gyouza,
                takoyaki,
                ramu,
                chickenPizza,
                kaisendon,
                rorukyabetu,
                chizu,
                okonomiyaki,
                chikenCarry,
                aqua,
                nikuman
        )
    }

    fun getRecipes() = sampleRecipes
}