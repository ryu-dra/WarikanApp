package com.ryu.warikanapp.data.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.*
import com.ryu.warikanapp.data.database.AppDatabase
import com.ryu.warikanapp.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserViewModel(application : Application) : AndroidViewModel(application) {
    private val db : AppDatabase = try {
        Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "user.db"
        )
            .build()
    } catch(e :Exception){
        Log.e("UserViewModel", "Failed to create database", e)
        throw e
    }

    private val userDao = db.userDao()

    val users : Flow<List<User>> = userDao.getAll()

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.insert(user)
        }
    }
}