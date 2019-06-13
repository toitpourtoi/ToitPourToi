package com.example.housesfinder.Activities


import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.housesfinder.RegisterFragment
import android.animation.AnimatorSet
import com.example.housesfinder.R



class WelcomeSplashActivity : AppCompatActivity() {
    lateinit var applogo : ImageView
    lateinit var containerview : ConstraintLayout
    private var content: FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_splash)

        applogo = findViewById(R.id.logoview)
        containerview = findViewById(R.id.container)
        content = findViewById<FrameLayout>(R.id.containerForm)

        var myanim : Animation = AnimationUtils.loadAnimation(this, R.anim.welcome_transition)
        containerview.startAnimation(myanim)



        applogo.animate().rotationBy(360f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(300)
        myanim.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                val fragment = RegisterFragment()
                addFragment(fragment)
            }

            override fun onAnimationStart(animation: Animation?) {

            }

        })
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.design_bottom_sheet_slide_in,
                R.anim.design_bottom_sheet_slide_out
            )
            .replace(R.id.containerForm, fragment, fragment.javaClass.getSimpleName())
            .addToBackStack(fragment.javaClass.getSimpleName())
            .commit()
    }
}
