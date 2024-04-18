package com.example.test_task

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test_task.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PersonAdapter
    private val personList: PersonList
        get() = (applicationContext as App).personList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val layoutManager = LinearLayoutManager(this)
        adapter = PersonAdapter(object : PersonActionListener {
            override fun onPersonGetId(person: Person) =
                Toast.makeText(this@MainActivity, "${person.firstname}: ${person.company}", Toast.LENGTH_SHORT).show()

            override fun onPersonRemove(person: Person) = personList.removePerson(person)

            override fun onPersonMove(person: Person, moveBy: Int) = personList.movePerson(person, moveBy)

        })
        personList.addListener(listener)

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }
    private val listener: PersonListener = {adapter.data = it}
}