package com.petsvote.app

import android.animation.Animator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.FrameLayout
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.findNavController
import me.vponomarenko.injectionmanager.x.XInjectionManager

class MainActivity : AppCompatActivity() {

    private val ICON_TIME: Long = 5000
    private val TRANSPARENT_ANIMATION: Long = 300L
    private val ANIMATION_ALPHA_NAME = "alpha"

    private val navigator: Navigator by lazy {
        XInjectionManager.findComponent<Navigator>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setUIStart()
    }

    override fun onResume() {
        super.onResume()
        navigator.bind(findNavController(R.id.nav_host_fragment))
    }

    override fun onPause() {
        super.onPause()
        navigator.unbind()
    }

    private fun setUIStart() {

        object : CountDownTimer(ICON_TIME, ICON_TIME) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                val notificationLayout = RemoteViews(packageName, R.layout.notification_collapsed)
                val notificationLayoutExpanded = RemoteViews(packageName, R.layout.notification_expanded)


                val name = getString(com.petsvote.ui.R.string.app_name)
                val descriptionText = getString(R.string.channel_description)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)

// Apply the layouts to the notification
                val customNotification = NotificationCompat.Builder(baseContext, "CHANNEL_ID")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    //.setStyle(NotificationCompat.DecoratedCustomViewStyle())
                    .setCustomContentView(notificationLayout)
                    .setCustomBigContentView(notificationLayoutExpanded)
                    .build()

                val builder = NotificationCompat.Builder(baseContext, "CHANNEL_ID")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("My notification")
                    .setContentText("Hello World!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .addAction(R.drawable.ic_launcher_foreground, getString(R.string.expanded_notification_info), null).build()

                NotificationManagerCompat.from(baseContext).apply {
                    notificationManager.notify(0, customNotification)
                }
            }
        }.start()
    }

    private fun animateSplash() {
        val propertyLP: PropertyValuesHolder =
            PropertyValuesHolder.ofFloat(ANIMATION_ALPHA_NAME, 1f, 0f)
        val animatorLP = ValueAnimator()
        animatorLP.setValues(propertyLP)
        animatorLP.duration = TRANSPARENT_ANIMATION
        animatorLP.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
            var alpha = animation.getAnimatedValue(ANIMATION_ALPHA_NAME) as Float
            findViewById<FrameLayout>(R.id.frame).alpha = alpha
        })
        animatorLP.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {}

            override fun onAnimationEnd(p0: Animator?) {
                findViewById<FrameLayout>(R.id.frame).visibility = View.GONE
            }

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationRepeat(p0: Animator?) {}
        })
        animatorLP.start()
    }

}