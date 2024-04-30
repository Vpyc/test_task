package com.example.test_task

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class ThemeManager {
    fun setLightTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        /*if (binding.recyclerView.itemDecorationCount > 0) {
            binding.recyclerView.removeItemDecoration(binding.recyclerView.getItemDecorationAt(0))
        }
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_light))
        binding.recyclerView.addItemDecoration(dividerItemDecoration)*/
    }

    fun setDarkTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        /*if (context.recyclerView.itemDecorationCount > 0) {
            binding.recyclerView.removeItemDecoration(binding.recyclerView.getItemDecorationAt(0))
        }
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_dark))
        binding.recyclerView.addItemDecoration(dividerItemDecoration)*/
    }

    fun getSavedTheme(context: Context): Boolean {
        val sharedPreferences =
            context.getSharedPreferences("Theme", AppCompatActivity.MODE_PRIVATE)
        return sharedPreferences.getBoolean("IsDarkTheme", false)
    }

    fun saveTheme(context: Context, isDarkTheme: Boolean) {
        val sharedPreferences =
            context.getSharedPreferences("Theme", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("IsDarkTheme", isDarkTheme)
        editor.apply()
    }
}