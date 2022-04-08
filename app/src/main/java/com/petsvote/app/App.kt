package com.petsvote.app
import android.app.Application;
import com.petsvote.app.di.AppComponent
import com.petsvote.app.di.DaggerAppComponent
import com.petsvote.register.di.RegisterDeps
import com.petsvote.register.di.RegisterDepsProvider
import com.petsvote.splash.di.SplashDeps
import com.petsvote.splash.di.SplashDepsProvider

class App: Application(), SplashDepsProvider, RegisterDepsProvider {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .build();
    }

    override fun onCreate() {
        super.onCreate()
//        AndroidThreeTen.init(this);
//        XInjectionManager.bindComponentToCustomLifecycle(object : IHasComponent<Navigator> {
//            override fun getComponent(): Navigator = Navigator()
//        })
    }


    override var depsSplash: SplashDeps = appComponent
    override var depsRegister: RegisterDeps = appComponent

//    override var depsRating: RatingDeps = appComponent
//    override var depsFilter: FilterDeps = appComponent
//    override var depsVote: VoteDeps = appComponent
//    override var depsUP: UPDeps = appComponent
//    override var depsPet: PetDeps = appComponent
}