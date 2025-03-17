package com.ryu.warikanapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.ryu.warikanapp.data.model.User

@Composable
fun ValueFormScreen(navController: NavController) {
    var selectedUser by remember { mutableStateOf<User?>(null) }
    var value by remember { mutableStateOf("") }
    val users = remember { mutableStateListOf<User>() } // ユーザーリストのデータ

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("値入力フォーム", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        // ユーザー選択
        DropdownMenu(
            expanded = true,
            onDismissRequest = { }
        ) {
            users.forEach { user ->
                DropdownMenuItem(
                    text = {Text(user.name)},
                    onClick = { selectedUser = user })
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = value,
            onValueChange = { value = it },
            label = { Text("値を入力") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // ここで値を保存する処理を実装
                navController.popBackStack()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("保存")
        }
    }
}
