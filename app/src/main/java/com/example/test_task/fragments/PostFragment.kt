package com.example.test_task.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.test_task.ThemeManager
import com.example.test_task.databinding.FragmentPostBinding
import com.example.test_task.retrofit.PostUser
import com.example.test_task.retrofit.RetrofitClient
import com.example.test_task.retrofit.UsersApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostFragment : Fragment() {
    private val themeManager = ThemeManager()
    private lateinit var binding: FragmentPostBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyTheme()
        binding.buttonPost.setOnClickListener {
            post()
        }
    }

    private fun applyTheme() {
        if (themeManager.getSavedTheme(requireContext())) {
            themeManager.setDarkTheme()
        } else {
            themeManager.setLightTheme()
        }
    }

    private fun post() {
        val usersAPI = RetrofitClient.getInstance().create(UsersApi::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = usersAPI.postUser(
                PostUser(
                    binding.firstnameText.text.toString(),
                    binding.secondnameText.text.toString(),
                    binding.ageText.text.toString().toInt()
                )
            )
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@PostFragment.requireActivity(),
                    "${response.id}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}