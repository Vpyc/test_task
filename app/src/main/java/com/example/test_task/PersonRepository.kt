package com.example.test_task

import com.example.test_task.retrofit.UsersApi
import com.example.test_task.room.CompanyEntity
import com.example.test_task.room.MainDb
import com.example.test_task.room.PersonEntity
import com.example.test_task.room.PersonInfo

class PersonRepository(private val mainDb: MainDb) {
    private val personDao = mainDb.getPersonDao()
    private val companyDao = mainDb.getCompanyDao()
    suspend fun fetchDataAndSaveToDb(usersApi: UsersApi) {
        val response = usersApi.getPersons(
            100, "firstName,lastName,image,company,id"
        )

        response.users.forEach { user ->
            val company = companyDao.getCompanyByName(user.company.name)
            if (company == null) {
                companyDao.insertCompany(CompanyEntity(companyName = user.company.name))
            }
        }

        // Сохранение пользователей в базу данных
        val persons = response.users.map { user ->
            PersonEntity(
                id = user.id.toLong(),
                firstName = user.firstName,
                lastName = user.lastName,
                companyId = companyDao.getCompanyByName(user.company.name)?.id ?: 0,
                img = user.image
            )
        }
        persons.forEach { person ->
            personDao.insertPerson(person)
        }
    }
    suspend fun deletePerson(personId: Long) {
        personDao.deletePersonById(personId)
    }
    fun getAllPersons(): List<PersonInfo> {
        return personDao.getAllPersonsWithCompany()
    }
}