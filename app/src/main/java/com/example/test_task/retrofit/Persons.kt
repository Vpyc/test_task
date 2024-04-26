package com.example.test_task.retrofit

data class Persons(
    val users: List<User>
)
data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val company: Company,
    val image: String
)
data class Company(
    val name: String
)
