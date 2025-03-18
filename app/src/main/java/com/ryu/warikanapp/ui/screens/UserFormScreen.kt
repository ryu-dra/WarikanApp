package com.ryu.warikanapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ryu.warikanapp.data.model.User
import com.ryu.warikanapp.data.view_model.UserViewModel


@Composable
fun UserFormScreen(navController: NavController, viewModel: UserViewModel) {

    val users by viewModel.users.collectAsState(initial = emptyList())

    var name by remember { mutableStateOf<String?>(null) }
    var selectedUser by remember { mutableStateOf<User?>(null) }

    //警告文のフラグ管理
    var showWarning by remember { mutableStateOf(false) }
    var showDuplicateWarning by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("ユーザー登録/編集", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.End),

            ){
            Text(
                text = "戻る",
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))



        LazyColumn {
            items(users) { user ->
                Text(
                    text = user.name,
                    modifier = Modifier.clickable{
                        selectedUser = user
                    }.padding(8.dp)
                )
            }
        }

        if (selectedUser != null) {
            EditUserDialog(
                user = selectedUser!!,
                onDismiss = { selectedUser = null },
                onConfirm = {
                    viewModel.updateUser(selectedUser!!.copy(name = it))
                    selectedUser = null // ✅ ポップアップを閉じる
                }
            )
        }

        if (showWarning) { // 警告文を表示
            Text(
                text = "すべて入力してください。",
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        }else if(showDuplicateWarning){
            Text(
                text = "同じ名前のユーザーが存在します。",
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        }

        OutlinedTextField(
            value = name?:"",
            onValueChange = { name = it },
            label = { Text("名前") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (name == null) {
                    showWarning = true
                }else if(users.any{it.name == name}){
                    showDuplicateWarning = true
                }else {
                    viewModel.addUser(User(name = name!!,rest = 0))
                    navController.popBackStack()
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("保存")
        }
    }
}

@Composable
fun EditUserDialog(
    user: User,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var newName by remember { mutableStateOf(user.name) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("名前を編集") },
        text = {
            OutlinedTextField(
                value = newName,
                onValueChange = { newName = it },
                singleLine = true
            )
        },
        confirmButton = {
            Button(onClick = { onConfirm(newName) }) {
                Text("保存")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("キャンセル")
            }
        }
    )
}
