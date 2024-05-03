package com.example.test_task.room

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class PersonInfo(
    val id: Long,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "company_name") val companyName: Long,
    val img: String
)
