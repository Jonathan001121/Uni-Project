package com.example.webbasedsystem
import androidx.compose.material3.ExperimentalMaterial3Api
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.webbasedsystem.ui.theme.WebBasedSystemTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val dataStore = UserPreferences(LocalContext.current)
            val mode by dataStore.getMode.collectAsState(initial = false)

            WebBasedSystemTheme(darkTheme = mode ?: false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScaffoldScreen()
                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WebBasedSystemTheme {
        ScaffoldScreen()
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldScreen() {
    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Home", "Search", "LogIn")

    val iconList= listOf(Icons.Filled.List,Icons.Filled.Search,Icons.Filled.Lock)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {   Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = R.drawable.logo),
                        contentDescription = stringResource(id = R.string.logo),
                        modifier = Modifier.size(60.dp)
                    )
                    Column {
                        Text("Inventory System", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                    }
                    SettingList()
                }
                },
                navigationIcon = {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    if (navBackStackEntry?.arguments?.getBoolean("topLevel") == false) {
                        IconButton(
                            onClick = { navController.navigateUp() }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    } else {
                    }
                }

            )
        },
        bottomBar = {
            NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = { Icon(iconList[index], contentDescription = item) },
                    label = { Text(item) },
                    selected = selectedItem == index,
                    onClick = { selectedItem = index }
                )
            }
        }},
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding),
            ) {when (selectedItem) {
                0 -> DeptNav(navController)
                1 -> SearchNav(navController)
                2 -> LoginNav(navController)

            }}
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingList() {
    val dataStore = UserPreferences(LocalContext.current)
    val coroutineScope = rememberCoroutineScope()
    val checked by dataStore.getMode.collectAsState(initial = false)

    ListItem(

        headlineText = {  },
        trailingContent = {
            Switch(
                modifier = Modifier.semantics { contentDescription = "Demo" },
                checked = checked?:true,
                onCheckedChange = {
                    coroutineScope.launch {
                        dataStore.saveMode(it)
                    }

                })
        }

    )
}
