package com.ryu.warikanapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ryu.warikanapp.data.view_model.UserViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: UserViewModel = viewModel()) {
    val users by viewModel.users.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = { navController.navigate("user_form") }) {
            Text("ユーザー登録/編集")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navController.navigate("value_form") }) {
            Text("値入力フォーム")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("ユーザーリスト", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        LazyColumn {
            items(users) { user ->
                Text(user.name, modifier = Modifier.padding(8.dp))
            }
        }
    }
}
