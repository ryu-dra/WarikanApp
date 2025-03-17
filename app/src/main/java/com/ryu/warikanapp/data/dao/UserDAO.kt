package com.ryu.warikanapp.data.dao



import androidx.room.*
import com.ryu.warikanapp.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * FROM user")
    fun getAll(): Flow<List<User>>
}