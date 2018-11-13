package jp.kentan.cookpad2018.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import jp.kentan.cookpad2018.data.GroupRepository
import jp.kentan.cookpad2018.data.RecipeRepository
import jp.kentan.cookpad2018.data.UserRepository
import jp.kentan.cookpad2018.ui.viewmodel.ViewModelFactory
import javax.inject.Singleton

@Module
class AppModule(app: Application) {

    private val context = app.applicationContext
    private val userRepository = UserRepository()
//    private val foodRepository = FoodRepository()
    private val groupRepository = GroupRepository()
    private val recipeRepository = RecipeRepository()

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideUserRepository() = userRepository

    @Provides
    @Singleton
    fun provideGroupRepository() = groupRepository

    @Provides
    @Singleton
    fun provideRecipeRepository() = recipeRepository

    @Provides
    @Singleton
    fun provideViewModelFactory() = ViewModelFactory(userRepository, groupRepository)
}