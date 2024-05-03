package com.example.test_task

import com.example.test_task.retrofit.UsersApi
import com.example.test_task.room.CompanyEntity
import com.example.test_task.room.MainDb
import com.example.test_task.room.PersonEntity
import com.example.test_task.room.PersonInfo

class PersonRepository(private val mainDb: MainDb, private val usersApi: UsersApi) {
    private val personDao = mainDb.getPersonDao()
    private val companyDao = mainDb.getCompanyDao()
    suspend fun fetchDataAndSaveToDb() {
        val response = usersApi.getPersons(
            100, "firstName,lastName,image,company"
        )

        val companies = response.users.map { it.company }
        companies.forEach { company ->
            companyDao.insertCompany(CompanyEntity(companyName = company.name))
        }

        // Сохранение пользователей в базу данных
        val persons = response.users.map { user ->
            PersonEntity(
                firstName = user.firstName,
                lastName = user.lastName,
                companyId = companyDao.getAllCompanies()
                    .firstOrNull { it.companyName == user.company.name }?.id ?: 0,
                img = user.image
            )
        }
        persons.forEach { person ->
            personDao.insertPerson(person)
        }
    }

    fun getAllPersons(): List<PersonInfo> {
        return personDao.getAllPersonsWithCompany()
    }
}