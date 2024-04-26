package com.example.test_task

import android.os.Bundle

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test_task.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PersonAdapter
    private lateinit var switchTheme: SwitchCompat
    private val personList: PersonList
        get() = (applicationContext as App).personList


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        switchTheme = binding.switch1
        switchTheme.isChecked = getSavedTheme()
        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setDarkTheme()
            } else {
                setLightTheme()
            }
            saveTheme(isChecked)
            }
        if (switchTheme.isChecked) {
            setDarkTheme()
        } else {
            setLightTheme()
        }
        val layoutManager = LinearLayoutManager(this)
        adapter = PersonAdapter(object : PersonActionListener {
            override fun onPersonGetId(person: Person) =
                Toast.makeText(
                    this@MainActivity,
                    "${person.firstname}: ${person.company}",
                    Toast.LENGTH_SHORT
                ).show()

            override fun onPersonRemove(person: Person) = personList.removePerson(person)

            override fun onPersonMove(person: Person, moveBy: Int) =
                personList.movePerson(person, moveBy)

        })
        personList.addListener(listener)

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }
    private val listener: PersonListener = { adapter.data = it }
    private fun setLightTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        if(binding.recyclerView.itemDecorationCount > 0) {
            binding.recyclerView.removeItemDecoration(binding.recyclerView.getItemDecorationAt(0))
        }
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_light))
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun setDarkTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        if(binding.recyclerView.itemDecorationCount > 0) {
            binding.recyclerView.removeItemDecoration(binding.recyclerView.getItemDecorationAt(0))
        }
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_dark))
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun getSavedTheme(): Boolean {
        val sharedPreferences = getSharedPreferences("Theme", MODE_PRIVATE)
        return sharedPreferences.getBoolean("IsDarkTheme", false)
    }

    private fun saveTheme(isDarkTheme: Boolean) {
        val sharedPreferences = getSharedPreferences("Theme", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("IsDarkTheme", isDarkTheme)
        editor.apply()
    }

    override fun onResume() {
        super.onResume()
        if (getSavedTheme()) {
            setDarkTheme()
            switchTheme.isChecked = true
        } else {
            setLightTheme()
            switchTheme.isChecked = false
        }
    }
}
