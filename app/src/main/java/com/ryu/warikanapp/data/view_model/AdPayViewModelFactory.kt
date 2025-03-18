package com.ryu.warikanapp.data.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ryu.warikanapp.data.dao.AdPayDao

class AdPayViewModelFactory(private val adPayDao: AdPayDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdPayViewModel::class.java)) {
            return AdPayViewModel(adPayDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}