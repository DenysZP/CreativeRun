package com.dm.creativerun.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.dm.creativerun.R
import com.dm.creativerun.databinding.ActivityMainBinding
import com.dm.creativerun.manager.ImageDownloadManager
import com.dm.creativerun.manager.ThemePreferencesManager
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val themePreferencesManager: ThemePreferencesManager by inject()
    private val imageDownloadManager: ImageDownloadManager by inject()
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        themePreferencesManager.applyThemeMode()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycle.addObserver(imageDownloadManager)

        setupNavigation()
    }

    override fun onSupportNavigateUp() = navController?.navigateUp() ?: false

    private fun setupNavigation() {
        navController = findNavController(R.id.hostFragment).also {
            NavigationUI.setupActionBarWithNavController(this, it)
            binding.bottomNavigation.setupWithNavController(it)
        }
    }
}
