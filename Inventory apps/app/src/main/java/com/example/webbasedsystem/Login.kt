package com.example.webbasedsystem
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.gson.Gson
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(navController: NavHostController) {
    var acc by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var stringBody: String by remember { mutableStateOf("") }
    if (ViewLoginModel.token=="") {
        Column(
            modifier = Modifier
                .padding(30.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(
                16.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextField(
                placeholder = { Text("User Account") },
                maxLines = 1,
                value = acc,
                onValueChange = { acc = it },
                leadingIcon = { Icon(imageVector = Icons.Default.Person, null) }
            )

            TextField(
                placeholder = { Text("Password") },
                maxLines = 1,
                value = pw,
                onValueChange = { pw = it },
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, null) }

            )


            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    coroutineScope.launch {

                        stringBody = postUserLogin(acc, pw)

                    }
                }) {
                Text(text = "Log In", Modifier.padding(vertical = 8.dp), fontSize = 20.sp)
            }
            if (stringBody.contains("token")) {
                var gson = Gson()
                var loginModel = gson.fromJson(stringBody, LoginModel::class.java)
                ViewLoginModel.firstname = loginModel.firstname
                ViewLoginModel.last_name = loginModel.last_name
                ViewLoginModel.token = loginModel.token

            } else {
                Text(stringBody)
            }

        }
    }else {
        Column {
            Text("You have successfully login")
            Text("Welcome " + ViewLoginModel.firstname)
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    ViewLoginModel.firstname = ""
                    ViewLoginModel.last_name = ""
                    ViewLoginModel.token = ""

                    navController.navigate("main")
                }) {

                Text(text = "Log Out", Modifier.padding(vertical = 8.dp), fontSize = 20.sp)
            }
        }
    }
}
@Composable
fun LoginNav(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "main",
    ) {
        composable("main") { LogInScreen(navController) }

    }
}



