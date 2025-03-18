package com.ryu.warikanapp.data.dao

import androidx.room.*
import com.ryu.warikanapp.data.model.AdPay
import kotlinx.coroutines.flow.Flow


@Dao
interface AdPayDao {
    @Insert
    fun insert(adPay: AdPay)

    @Update
    fun update(adPay: AdPay)

    @Delete
    fun delete(adPay: AdPay)

    @Query("SELECT * FROM ad_pay")
    fun getAll(): Flow<List<AdPay>>
}