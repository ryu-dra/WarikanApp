package com.ryu.warikanapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ryu.warikanapp.data.model.User
import com.ryu.warikanapp.data.view_model.UserViewModel


@Composable
fun UserFormScreen(navController: NavController, viewModel: UserViewModel = viewModel()) {
    var name by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("ユーザー登録/編集", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("名前") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.addUser(User(name = name))
                navController.popBackStack()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("保存")
        }
    }
}
