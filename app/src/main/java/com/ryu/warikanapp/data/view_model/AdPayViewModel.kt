package com.ryu.warikanapp.data.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryu.warikanapp.data.dao.AdPayDao
import com.ryu.warikanapp.data.model.AdPay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AdPayViewModel(private val adPayDao: AdPayDao) : ViewModel()  {

    val adPays : Flow<List<AdPay>> = adPayDao.getAll()

    fun addAdPay(adPay: AdPay) {
        viewModelScope.launch(Dispatchers.IO) {
            adPayDao.insert(adPay)
        }
    }

}