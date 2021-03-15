package com.ninpou.stormotiontesttask.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import com.ninpou.stormotiontesttask.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainActivityBinding.root
        setContentView(view)

        //for ads
        MobileAds.initialize(
            this
        ) {
            // SOMETHING
        }
    }
}
