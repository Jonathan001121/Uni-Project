package com.example.webbasedsystem

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
class holder{
    companion object {
        var keyword: String=""
        var type: String =""
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchItemList(navController: NavHostController) {
    var k by remember {
        mutableStateOf("")
    }
    var t by remember {
        mutableStateOf("")
    }
    var selectedOption by remember {
        mutableStateOf("")
    }
    val onSelectionChange = { text: String ->
        selectedOption = text
    }

    val padding = 10.dp
    Column(){
        Text("Search(Choose one):", fontSize = 20.sp, fontWeight = FontWeight.Bold,modifier = Modifier.padding(5.dp,20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {

            Category.data.forEach() { item ->
                val color = if (selectedOption==item.text) Color.Black else Color.Gray
                Button(

                    onClick = { t=item.text; holder.type=t;onSelectionChange(item.text) },
                    contentPadding = PaddingValues(20.dp),
                    colors= ButtonDefaults.buttonColors(containerColor = color)

                ){
                    Text(text = item.text )
                }
                Spacer(Modifier.size(padding))

            }

        }
        Spacer(Modifier.size(padding))
        Row(modifier = Modifier.fillMaxWidth(),) {
            TextField(
                placeholder = { Text("Keyword") },
                maxLines = 1,
                value = k,
                onValueChange = { k = it ;holder.keyword=k}
            )
            Button(
                onClick = {

                    holder.type=t
                    when(holder.type){
                        "Books"-> navController.navigate("book")
                        "Games"->navController.navigate("game")
                        "Gifts"->navController.navigate("gift")
                        "Materials"->navController.navigate("material")
                        else->""
                    }

                          },
                // or shape = CircleShape
                contentPadding = PaddingValues(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) {
                Text(text = "Search")
            }
        }

    }

}

@Composable
fun SearchNav(navController: NavHostController) {



    NavHost(
        navController = navController,
        startDestination = "main",
    ) {
        composable("main") { SearchItemList(navController) }
        composable("book") {
            val books = produceState(
            initialValue = listOf<Book>(),
            producer = {
                value = getSearchBooks(holder.keyword)
            }
        )
            BookScreen(books.value) }
        composable("game") {
            val games = produceState(
            initialValue = listOf<Game>(),
            producer = {
                value = getSearchGames(holder.keyword)
            }
        )
            GameScreen(games.value) }
        composable("gift") {
            val gifts = produceState(
            initialValue = listOf<Gift>(),
            producer = {
                value = getSearchGifts(holder.keyword)
            }
        )
            GiftScreen(gifts.value) }
        composable("material") {
            val materials = produceState(
                initialValue = listOf<Material>(),
                producer = {


                    value = getSearchMaterials(holder.keyword)

                }
            )
            MaterialScreen(materials.value) }
    }
}
