package com.example.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.viewpager2.widget.ViewPager2
import com.example.demo.adapter.OnboardingAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: OnboardingAdapter
    private lateinit var nextButton: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Splash screen
        installSplashScreen()
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)


        val onboardingItems = listOf(
            OnboardingItem(R.drawable.welcome_img_1, getString(R.string.firstWelcomeText)),
            OnboardingItem(R.drawable.welcome_img_2, getString(R.string.secondWelcomeText)),
            OnboardingItem(R.drawable.welcome_img_3, getString(R.string.thirdWelcomeText))
        )

        val adapter = OnboardingAdapter(onboardingItems, object : OnboardingAdapter.OnItemClickListener {
            override fun onNextButtonClick() {

                // Go to next activity
                startActivity(Intent(applicationContext, SelectionActivity::class.java))
                finish()
            }
        })

        // set up view pager and link to adapter
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = adapter



    }
}