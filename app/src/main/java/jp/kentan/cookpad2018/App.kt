package jp.kentan.cookpad2018

import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import jp.kentan.cookpad2018.di.ActivityModule
import jp.kentan.cookpad2018.di.AppModule
import javax.inject.Singleton

open class App : DaggerApplication() {

    private val component: App.Component by lazy(LazyThreadSafetyMode.NONE) {
        DaggerApp_Component.builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun applicationInjector(): AndroidInjector<App> = component

    @Singleton
    @dagger.Component(modules = [
        (AndroidSupportInjectionModule::class),
        (AppModule::class),
        (ActivityModule::class)])
    interface Component : AndroidInjector<App>
}