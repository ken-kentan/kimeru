package jp.kentan.cookpad2018.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import jp.kentan.cookpad2018.ui.MainActivity
import jp.kentan.cookpad2018.ui.group.create.GroupCreateActivity
import jp.kentan.cookpad2018.ui.group.detail.GroupDetailActivity
import jp.kentan.cookpad2018.ui.group.recipe.RecommendRecipeActivity
import jp.kentan.cookpad2018.ui.link.LinkActivity
import jp.kentan.cookpad2018.ui.user.entry.UserEntryActivity

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeUserEntryActivity(): UserEntryActivity

    @ContributesAndroidInjector
    abstract fun contributeGroupCreateActivity(): GroupCreateActivity

    @ContributesAndroidInjector
    abstract fun contributeGroupDetailActivity(): GroupDetailActivity

    @ContributesAndroidInjector
    abstract fun contributeRecommendRecipeActivity(): RecommendRecipeActivity

    @ContributesAndroidInjector
    abstract fun contributeLinkActivity(): LinkActivity
}