package com.ryu.warikanapp.data.view_model


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryu.warikanapp.data.dao.UserDao
import com.ryu.warikanapp.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class UserViewModel(private val userDao: UserDao) : ViewModel() {

    val users : Flow<List<User>> = userDao.getAll()

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.insert(user)
        }
    }

    fun findUserByName(name: String): User? {
        var user: User? = null
        viewModelScope.launch(Dispatchers.IO) {
            user = userDao.getByName(name)
        }
        return user
    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.update(user)
        }
    }


}