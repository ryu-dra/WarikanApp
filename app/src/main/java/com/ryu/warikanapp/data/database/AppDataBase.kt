package com.ryu.warikanapp.data.database

import androidx.room.*
import com.ryu.warikanapp.data.dao.AdPayDao
import com.ryu.warikanapp.data.dao.UserDao
import com.ryu.warikanapp.data.model.AdPay
import com.ryu.warikanapp.data.model.Converters
import com.ryu.warikanapp.data.model.User

@Database(entities = [User::class, AdPay::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun adPayDao(): AdPayDao


}