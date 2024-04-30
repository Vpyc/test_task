package com.example.test_task.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test_task.App
import com.example.test_task.Person
import com.example.test_task.PersonActionListener
import com.example.test_task.PersonAdapter
import com.example.test_task.PersonList
import com.example.test_task.PersonListener
import com.example.test_task.R
import com.example.test_task.ThemeManager
import com.example.test_task.databinding.FragmentListBinding
import com.example.test_task.retrofit.RetrofitClient
import com.example.test_task.retrofit.UsersApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListFragment : Fragment() {
    private val themeManager = ThemeManager()
    private lateinit var adapter: PersonAdapter
    private lateinit var binding: FragmentListBinding

    private lateinit var personList: PersonList
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        personList = (requireActivity().application as App).personList
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyTheme()
        if (!personList.isLoaded) {
            binding.progressBar.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
            takingFromApi()
        } else {
            initRecyclerView()
        }
    }

    private val listener: PersonListener = { adapter.data = it }
    private fun takingFromApi() {
        val usersAPI = RetrofitClient.getInstance().create(UsersApi::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = usersAPI.getPersons(
                100, "firstName,lastName,image,company,id"
            )
            withContext(Dispatchers.Main) {
                val personsResponse = response.users
                personList.addPersons(personsResponse)
                initRecyclerView()
            }
        }
    }
    private fun applyTheme() {
        if (themeManager.getSavedTheme(requireContext())) {
            setDarkTheme()
        } else {
            setLightTheme()
        }
    }

    private fun initRecyclerView() {
        adapter = PersonAdapter(object : PersonActionListener {
            override fun onPersonGetId(person: Person) =
                Toast.makeText(
                    this@ListFragment.requireActivity(),
                    "${person.firstname}: ${person.company}",
                    Toast.LENGTH_SHORT
                ).show()

            override fun onPersonRemove(person: Person) =
                personList.removePerson(person)

            override fun onPersonMove(person: Person, moveBy: Int) =
                personList.movePerson(person, moveBy)
        })
        personList.addListener(listener)
        try {
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        } catch (e: Exception) {
            Log.e("RecyclerView", e.toString())
        }
        binding.recyclerView.adapter = adapter
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun setLightTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        if (binding.recyclerView.itemDecorationCount > 0) {
            binding.recyclerView.removeItemDecoration(binding.recyclerView.getItemDecorationAt(0))
        }
        val dividerItemDecoration = DividerItemDecoration(this@ListFragment.requireActivity(), DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_light))
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun setDarkTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        if (binding.recyclerView.itemDecorationCount > 0) {
            binding.recyclerView.removeItemDecoration(binding.recyclerView.getItemDecorationAt(0))
        }
        val dividerItemDecoration = DividerItemDecoration(this@ListFragment.requireActivity(), DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_dark))
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
    }

}