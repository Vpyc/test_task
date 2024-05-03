package com.example.test_task.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "person",
    indices = [Index("id")],
    foreignKeys = [
        ForeignKey(
        entity = CompanyEntity::class,
        parentColumns = ["id"],
        childColumns = ["company_id"]
        )
    ]
)
data class PersonEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "company_id") val companyId: Long,
    val img: String
)
