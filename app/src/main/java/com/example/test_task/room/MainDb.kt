package com.example.test_task.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        PersonEntity::class,
        CompanyEntity::class],
    version = 1
)
abstract class MainDb : RoomDatabase() {
    abstract fun getPersonDao(): PersonDao
    abstract fun getCompanyDao(): CompanyDao


    companion object {

        fun getDb(context: Context): MainDb {
            return Room.databaseBuilder(
                context.applicationContext,
                MainDb::class.java,
                name = "test.db"
            ).build()
        }
    }
}