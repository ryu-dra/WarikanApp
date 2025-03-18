package com.ryu.warikanapp.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ryu.warikanapp.Warikan
import com.ryu.warikanapp.data.model.User
import com.ryu.warikanapp.data.view_model.AdPayViewModel
import com.ryu.warikanapp.data.view_model.UserViewModel

@Composable
fun HomeScreen(navController: NavController, userViewModel: UserViewModel, adPayViewModel: AdPayViewModel) {

    val users by userViewModel.users.collectAsState(initial = emptyList())
    val adPays by adPayViewModel.adPays.collectAsState(initial = emptyList())
    val warikans = howToWarikan(users)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = { navController.navigate("user_form") }) {
            Text("ユーザー登録/編集")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navController.navigate("value_form") }) {
            Text("値入力フォーム")
        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(modifier = Modifier.padding(8.dp)) {
            Column{
                Text("立て替え履歴", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                LazyColumn {
                    items(adPays) { adPay ->
                        Column(modifier = Modifier.padding(8.dp)
                            .border(1.dp, Color.Gray)
                        ) {
                            Text(text = adPay.name, fontWeight = FontWeight.Bold)
                            Text(text = adPay.from+"->"+adPay.to.joinToString(","))
                            Text(text = adPay.price.toString()+"円")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(30.dp))

            Column{
                Text("割り勘方法", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                LazyColumn {

                    items(warikans) { warikan ->
                        Text(text = warikan.from + "->" + warikan.to + " " + warikan.price.toString() + "円")
                    }
                }

            }
        }
    }
}

//割り勘アルゴリズム:各userのdummyを生成し、各正の人に対し、dummyRestが0になるor負の人がいなくなる
//まで負の人のdummyRestをすいとる。吸い取った額と吸い取った人を記録してWarikanオブジェクトに記録、Listにする。
@Composable
fun howToWarikan(users: List<User>): MutableList<Warikan> {
    val list = mutableListOf<Warikan>()
    val dummyList = users.map{it.copy()}    //userのdummy

    for(posUser in dummyList.filter{it.rest>0}){
        for(negaUser in dummyList.filter{it.rest<0}){
            if(posUser.rest>0 && posUser.rest + negaUser.rest>0){
                list.add(Warikan(from = posUser.name,to = negaUser.name, negaUser.rest*(-1)))
                posUser.rest += negaUser.rest
                negaUser.rest = 0
            }else if(posUser.rest>0 && posUser.rest + negaUser.rest <= 0){
                list.add(Warikan(from = posUser.name, to = negaUser.name, posUser.rest))
                negaUser.rest += posUser.rest
                posUser.rest = 0

            }else {
                continue
            }

        }
    }

    println("did")
    return list
}
