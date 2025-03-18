package com.ryu.warikanapp.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.ryu.warikanapp.data.model.AdPay
import com.ryu.warikanapp.data.model.User
import com.ryu.warikanapp.data.view_model.AdPayViewModel
import com.ryu.warikanapp.data.view_model.UserViewModel

@Composable
fun ValueFormScreen(navController: NavController, adPayViewModel: AdPayViewModel, userViewModel: UserViewModel) {

    //データベースから得たデータリスト
    val users by userViewModel.users.collectAsState(initial = emptyList())
    //AdPayオブジェクト生成に必要な値
    var name by remember { mutableStateOf<String?>(null) }
    var from by remember { mutableStateOf<User?>(null) }
    val to = remember { mutableStateListOf<User>() }
    var value by remember { mutableStateOf<String?>(null) }

    //警告文のフラグ管理
    var showWarning by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("立て替え情報入力フォーム", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.End),

        ){
            Text(
                text = "戻る",
                fontWeight = FontWeight.Bold
            )
        }

        if (showWarning) { // 警告文を表示
            Text(
                text = "すべて入力してください。",
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        }


        OutlinedTextField(
            value = name?:"",
            onValueChange = { name = it },
            label = { Text("立て替えたものを入力") },
            modifier = Modifier.fillMaxWidth()
        )

        DropdownMenuOfFrom (users){ from = it }

        CheckBoxOfTo(users){ user :User?, isChecked :Boolean ->
            if (user != null){
                if (isChecked) {
                    to.add(user)
                } else {
                    to.remove(user)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = value?:"",
            onValueChange = { value = it },
            label = { Text("金額を入力") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if(name != null && from != null && value != null){
                    adPayViewModel.addAdPay(AdPay(name = name!!, from = from!!.name, to = to.map { it.name }, price = value!!.toInt()))
                    //ここのnull判定が甘いかも
                    //ここでuserの払わないといけない残金を計算する。
                    //非同期処理であるから、実行順序で結果に違いが出ないようにする。
                    //to,fromに追加されたuserは全部usersに含まれるuserオブジェクトであるため、このように実装した。
                    from!!.rest -= value!!.toInt()
                    for(user in to){

                        user.rest += value!!.toInt()/to.size
                    }

                    for(user in users){
                        userViewModel.updateUser(user)  //全部のuserをまとめて更新する
                    }
                    navController.popBackStack()
                }else{
                    showWarning = true
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("保存")
        }
    }
}



@Composable
fun DropdownMenuOfFrom(users: List<User>, onValueChange: (User) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedName by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = selectedName ?: "払った人の名前",
            modifier = Modifier
                .clickable { expanded = true }
                .fillMaxWidth()
                .border(1.dp, Color.Gray)
                .padding(3.dp)

        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            users.forEach { user ->
                DropdownMenuItem(
                    text = { Text(user.name) },
                    onClick = {
                        selectedName = user.name
                        expanded = false
                        onValueChange(user)
                    }
                )
            }
        }
    }
}

@Composable
fun CheckBoxOfTo(users: List<User>, onValueChange: (User, Boolean) -> Unit){

    Text(text = "立て替えてもらった人", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    LazyColumn {
        items(users) { user ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                var isChecked by remember { mutableStateOf(false) }
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        isChecked = it
                        onValueChange(user, it)
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = user.name, fontSize = 18.sp)
            }
        }
    }
}