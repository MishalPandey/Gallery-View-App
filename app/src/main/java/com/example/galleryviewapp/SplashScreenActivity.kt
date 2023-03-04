package com.example.galleryviewapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen)



        val logo = findViewById<ImageView>(R.id.logo)
        val title = findViewById<TextView>(R.id.title)

        val topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        val bottomAnimation= AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        logo.setAnimation(topAnimation);
        title.setAnimation(bottomAnimation);

        val SPLASH_SCREEN = 4300;

        Handler().postDelayed({
            val intent = Intent(this@SplashScreenActivity, Img_Vid_View::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN.toLong())

    }
}