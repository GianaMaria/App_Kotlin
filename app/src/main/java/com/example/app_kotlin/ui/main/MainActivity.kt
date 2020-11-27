package com.example.app_kotlin.ui.main

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.app_kotlin.R
import com.example.app_kotlin.ui.fragments.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    fun navigateTo(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .replace(R.id.fragment_container_view, fragment)
            .addToBackStack("note")
            .commit()
    }

    fun navigateToMain(fragment: MainFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .replace(R.id.fragment_container_view, fragment)
            .commit()
    }

}