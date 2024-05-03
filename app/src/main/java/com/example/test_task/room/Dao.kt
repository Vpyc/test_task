package com.example.test_task.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert(entity = PersonEntity::class)
    fun insertAllPerson(person: List<PersonEntity>)
    @Insert(entity = CompanyEntity::class)
    fun insertAllCompany(company: List<CompanyEntity>)
    @Query("SELECT person.id, first_name, last_name, company_name, img FROM person\n"+
            "INNER JOIN company ON person.company_id = company.id\n")
    fun getAllPerson(): Flow<List<PersonInfo>>
}