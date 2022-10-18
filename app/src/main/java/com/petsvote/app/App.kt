package com.petsvote.app
import android.app.Application;
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.core.os.bundleOf
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import com.petsvote.app.di.AppComponent
import com.petsvote.app.di.DaggerAppComponent
import com.petsvote.dialog.di.DialogDeps
import com.petsvote.dialog.di.DialogDepsProvider
import com.petsvote.filter.di.FilterDeps
import com.petsvote.filter.di.FilterDepsProvider
import com.petsvote.legal.di.TermsDeps
import com.petsvote.legal.di.TermsDepsProvider
import com.petsvote.rating.di.RatingDeps
import com.petsvote.rating.di.RatingDepsProvider
import com.petsvote.register.di.RegisterDeps
import com.petsvote.register.di.RegisterDepsProvider
import com.petsvote.room.RoomDeps
import com.petsvote.room.RoomDepsProvider
import com.petsvote.splash.di.SplashDeps
import com.petsvote.splash.di.SplashDepsProvider
import com.petsvote.user.di.UserDeps
import com.petsvote.user.di.UserDepsProvider
import com.petsvote.vote.di.VoteDeps
import com.petsvote.vote.di.VoteDepsProvider
import com.petswote.pet.di.PetDeps
import com.petswote.pet.di.PetDepsProvider
import me.vponomarenko.injectionmanager.IHasComponent
import me.vponomarenko.injectionmanager.x.XInjectionManager

class App: Application(), SplashDepsProvider, RegisterDepsProvider, RoomDepsProvider,
    TermsDepsProvider, RatingDepsProvider, VoteDepsProvider, UserDepsProvider, FilterDepsProvider,
    DialogDepsProvider, PetDepsProvider {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .build();
    }

    override fun onCreate() {
        super.onCreate()
        XInjectionManager.bindComponentToCustomLifecycle(object : IHasComponent<Navigator> {
            override fun getComponent(): Navigator = Navigator()
        })

        //Firebase.initialize(this, Fire)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            Log.d(TAG, "token = $token")
        })
    }
    override var depsSplash: SplashDeps = appComponent
    override var depsRegister: RegisterDeps = appComponent
    override var depsRoom: RoomDeps = appComponent
    override var depsTerms: TermsDeps = appComponent
    override var depsRating: RatingDeps = appComponent
    override var depsVote: VoteDeps = appComponent
    override var depsUser: UserDeps = appComponent
    override var depsFilter: FilterDeps = appComponent
    override var depsDialog: DialogDeps = appComponent
    override var depsPet: PetDeps = appComponent

    companion object{
        const val TAG = "App"
    }
}