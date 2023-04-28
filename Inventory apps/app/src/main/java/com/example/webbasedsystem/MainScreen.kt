package com.example.webbasedsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.webbasedsystem.ui.theme.WebBasedSystemTheme
import androidx.compose.ui.Alignment
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material3.*
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
@Composable
fun CustomCircularProgressBar() {
    CircularProgressIndicator(
        modifier = Modifier.size(100.dp),
        color = Color.Green,
        strokeWidth = 10.dp
    )
}
@Composable
fun CategoryList(navController: NavHostController) {
    val padding = 5.dp
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Category.data.forEach(){ item->
        Row(verticalAlignment = Alignment.CenterVertically) {



                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = item.image),
                        contentDescription = stringResource(id = item.title),
                        modifier = Modifier.size(100.dp).clickable {
                            navController.navigate(item.route)
                        }
                    )
                }



            }
            Text(text = item.text)
            Spacer(Modifier.size(padding))
        }

    }
}


@Preview(showBackground = true)
@Composable
fun InfoPreview() {
    WebBasedSystemTheme{


            DeptNav(rememberNavController())


    }
}
data class Category(val image: Int, val title: Int, val text:String, val route:String) {
    companion object {
        val data = listOf(
            Category(R.drawable.book, R.string.book,"Books","book"),
            Category(R.drawable.game, R.string.game,"Games","game"),
            Category(R.drawable.gift, R.string.gift,"Gifts","gift"),
            Category(R.drawable.material, R.string.material,"Materials","material")
        )
    }
}

@Composable
fun DeptNav(navController: NavHostController) {


    val books = produceState(
        initialValue = listOf<Book>(),
        producer = {

            value = getbooks()
        }
    )
    val games = produceState(
        initialValue = listOf<Game>(),
        producer = {
            value = getgames()
        }
    )
    val gifts = produceState(
        initialValue = listOf<Gift>(),
        producer = {
            value = getgifts()
        }
    )
    val materials = produceState(
        initialValue = listOf<Material>(),
        producer = {
            value = getmaterials()
        }
    )
    NavHost(
        navController = navController,
        startDestination = "main",
    ) {
        composable("main") {Column{ Text("You need to login if you want to consume, borrow or return items"); CategoryList(navController) }}
        composable("book") { BookScreen(books.value) }
        composable("game") { GameScreen(games.value) }
        composable("gift") { GiftScreen(gifts.value) }
        composable("material") { MaterialScreen(materials.value) }
    }
}



