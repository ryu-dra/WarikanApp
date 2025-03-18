package com.ryu.warikanapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.ryu.warikanapp.data.database.AppDatabase
import com.ryu.warikanapp.data.view_model.AdPayViewModel
import com.ryu.warikanapp.data.view_model.AdPayViewModelFactory
import com.ryu.warikanapp.data.view_model.UserViewModel
import com.ryu.warikanapp.data.view_model.UserViewModelFactory
import com.ryu.warikanapp.ui.theme.WarikanAppTheme
import com.ryu.warikanapp.ui.navigation.AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app_database"
        ).build()

        val userDao = db.userDao()
        val adPayDao = db.adPayDao()
        val userViewModelFactory = UserViewModelFactory(userDao)
        val adPayViewModelFactory = AdPayViewModelFactory(adPayDao)
        val userViewModel = userViewModelFactory.create(UserViewModel::class.java)
        val adPayViewModel = adPayViewModelFactory.create(AdPayViewModel::class.java)

        setContent {
            WarikanAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavHost(navController, userViewModel, adPayViewModel) // ナビゲーションのセットアップ
                }
            }
        }
    }
}
