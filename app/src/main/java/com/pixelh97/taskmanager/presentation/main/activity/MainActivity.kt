package com.pixelh97.taskmanager.presentation.main.activity

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.pixelh97.taskmanager.R
import com.pixelh97.taskmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private var _listener: NavController.OnDestinationChangedListener? = null
    private val listener get() = _listener!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the BottomNavigationView with the NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.home_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.contentMain.bottomNav, navController)

        _listener =
            NavController.OnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.calenderFragmentNav -> {
                        binding.mainAppBar.setBackgroundColor(Color.WHITE)
                    }
                    else -> {
                        binding.mainAppBar.setBackgroundColor(Color.TRANSPARENT)
                    }
                }
            }
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(listener)
    }

    override fun onDestroy() {
        super.onDestroy()
        _listener = null
    }
}
