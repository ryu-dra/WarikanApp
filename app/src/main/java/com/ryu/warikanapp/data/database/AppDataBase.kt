package com.ryu.warikanapp.data.database

import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ryu.warikanapp.data.dao.UserDao
import com.ryu.warikanapp.data.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

}