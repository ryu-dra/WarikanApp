package com.ryu.warikanapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ryu.warikanapp.data.model.User
import com.ryu.warikanapp.ui.screens.HomeScreen
import com.ryu.warikanapp.ui.screens.UserFormScreen
import com.ryu.warikanapp.ui.screens.ValueFormScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    val userList = remember { mutableStateListOf<User>() }
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("user_form") { UserFormScreen(navController) }
        composable("value_form") { ValueFormScreen(navController) }
    }
}
