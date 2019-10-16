package com.dm.creativerun.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.dm.creativerun.databinding.ActivitySplashBinding
import com.dm.creativerun.ui.main.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.successEvent.observe(this) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        viewModel.fetchData()
    }
}