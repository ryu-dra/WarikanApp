package com.ryu.warikanapp.data.model

import androidx.room.*

@Entity(tableName = "ad_pay")
data class AdPay(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val from: String,
    val to: List<String>,
    val price: Int

)