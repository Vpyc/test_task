package com.example.test_task

import android.os.Bundle

import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.test_task.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var switchTheme: SwitchCompat
    private val themeManager = ThemeManager()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        binding.navView.setupWithNavController(navController)
        binding.navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> {
                    navController.navigate(R.id.listFragment)

                }
                R.id.page_2 -> {
                    navController.navigate(R.id.postFragment)
                }
            }
            true
        }

        switchTheme = binding.switch1
        switchTheme.isChecked = themeManager.getSavedTheme(this)
        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                themeManager.setDarkTheme(this)
            } else {
                themeManager.setLightTheme(this)
            }
            themeManager.saveTheme(this, isChecked)
        }
        if (switchTheme.isChecked) {
            themeManager.setDarkTheme(this)
        } else {
            themeManager.setLightTheme(this)
        }
    }

    override fun onResume() {
        super.onResume()
        if (themeManager.getSavedTheme(this)) {
            themeManager.setDarkTheme(this)
            switchTheme.isChecked = true
        } else {
            themeManager.setLightTheme(this)
            switchTheme.isChecked = false
        }
    }


}
