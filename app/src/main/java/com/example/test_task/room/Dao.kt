package com.example.test_task.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PersonDao {
    @Insert(entity = PersonEntity::class,
        onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(person: PersonEntity)
    @Query(
        "SELECT person.id, first_name, last_name, company_name, img FROM person\n" +
                "INNER JOIN company ON person.company_id = company.id\n"
    )
    fun getAllPersonsWithCompany(): List<PersonInfo>
    @Query("DELETE FROM person WHERE id = :id")
    suspend fun deletePersonById(id: Long)
}

@Dao
interface CompanyDao {
    @Insert(entity = CompanyEntity::class,
            onConflict = OnConflictStrategy.REPLACE)
    fun insertCompany(company: CompanyEntity)

    @Query("SELECT * FROM company")
    fun getAllCompanies(): List<CompanyEntity>
    @Query("SELECT * FROM company WHERE company_name = :companyName")
    suspend fun getCompanyByName(companyName: String): CompanyEntity?
}