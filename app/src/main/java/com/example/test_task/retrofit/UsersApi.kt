package com.example.test_task.retrofit

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UsersApi {
    @GET("users")
    suspend fun getPersons(
        @Query("limit") limit: Int,
        @Query("select") select: String
    ): Persons

    @POST("users/add")
    suspend fun postUser(@Body postUser: PostUser): User
}