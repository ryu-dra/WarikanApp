package com.ryu.warikanapp.data.model

import androidx.room.*

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String

)
