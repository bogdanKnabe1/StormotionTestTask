package com.ninpou.stormotiontesttask.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.ninpou.stormotiontesttask.R
import com.ninpou.stormotiontesttask.databinding.ActivityMainBinding
import com.ninpou.stormotiontesttask.view.fragments.MainListFragment


class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainActivityBinding.root
        setContentView(view)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, MainListFragment(), "LIST").commit()
            // later we can find this fragment
        }

        val toolbar = mainActivityBinding.baseToolbar.toolbarLayout
        toolbar.title = "List"
        // a way to set color manually
        /*toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))*/
        setSupportActionBar(toolbar)
        initView()
    }

    //set fragment in container
    private fun initView() {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, MainListFragment(), " LIST")
        transaction.show(MainListFragment())
        transaction.commit()
    }
}