package com.example.test_task.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PersonDao {
    @Insert(entity = PersonEntity::class)
    fun insertPerson(person: PersonEntity)

    @Query(
        "SELECT person.id, first_name, last_name, company_name, img FROM person\n" +
                "INNER JOIN company ON person.company_id = company.id\n"
    )
    fun getAllPersonsWithCompany(): List<PersonInfo>
}

@Dao
interface CompanyDao {
    @Insert(entity = CompanyEntity::class)
    fun insertCompany(company: CompanyEntity)

    @Query("SELECT * FROM company")
    fun getAllCompanies(): List<CompanyEntity>
}