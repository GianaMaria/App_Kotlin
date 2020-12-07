package com.example.app_kotlin.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app_kotlin.R
import com.example.app_kotlin.databinding.ActivitySplashBinding
import com.example.app_kotlin.errors.NoAuthException
import com.example.app_kotlin.presentation.SplashViewModel
import com.example.app_kotlin.presentation.SplashViewState
import com.firebase.ui.auth.AuthUI
import org.koin.android.viewmodel.ext.android.viewModel

private const val RC_SIGN_IN = 458

class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModel<SplashViewModel>()

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(LayoutInflater.from(this))

        setContentView(binding.root)

        viewModel.observeViewState().observe(this) {
            when (it) {
                is SplashViewState.Error -> renderError(it.error)
                SplashViewState.Auth -> renderData()
            }
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when {
            requestCode != RC_SIGN_IN -> return
            resultCode != Activity.RESULT_OK -> finish()
            resultCode == Activity.RESULT_OK -> renderData()
        }
    }

    private fun startMainActivity() {
        startActivity(MainActivity.getStartIntent(this))
        finish()
    }

    private fun renderData() {
        startMainActivity()
    }

    private fun renderError(error: Throwable) {
        when (error) {
            is NoAuthException -> startLoginActivity()
            else -> error.message?.let { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
        }
    }

    private fun startLoginActivity() {
        val providers = listOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.drawable.ic_baseline_person_pin_24)
                .setTheme(R.style.LoginStyle)
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN,
        )
    }
}