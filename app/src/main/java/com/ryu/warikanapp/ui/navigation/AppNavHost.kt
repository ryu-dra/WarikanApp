package com.ryu.warikanapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ryu.warikanapp.data.view_model.AdPayViewModel
import com.ryu.warikanapp.data.view_model.UserViewModel
import com.ryu.warikanapp.ui.screens.HomeScreen
import com.ryu.warikanapp.ui.screens.UserFormScreen
import com.ryu.warikanapp.ui.screens.ValueFormScreen

@Composable
fun AppNavHost(navController: NavHostController, userViewModel: UserViewModel, adPayViewModel: AdPayViewModel) {


    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController, userViewModel, adPayViewModel) }
        composable("user_form") { UserFormScreen(navController, userViewModel) }
        composable("value_form") { ValueFormScreen(navController, adPayViewModel, userViewModel) }
    }
}
