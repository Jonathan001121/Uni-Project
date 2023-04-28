package com.example.webbasedsystem

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.serialization.Serializable
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Serializable
data class Gift(
    val _id: String,
    val amount: Int,
    val category: String,
    val description: String,
    val donatedBy: String,
    val image: String,
    val location: String,
    val remaining: Int,
    val remark: String,
    val title: String,
    val type: String,
    val unitPrice: Int
)
@Composable
fun LazyListState.isAtBottom(): Boolean {

    return remember(this) {
        derivedStateOf {
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            if (layoutInfo.totalItemsCount == 0) {
                false
            } else {
                val lastVisibleItem = visibleItemsInfo.last()
                val viewportHeight = layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset

                (lastVisibleItem.index + 1 == layoutInfo.totalItemsCount &&
                        lastVisibleItem.offset + lastVisibleItem.size <= viewportHeight)
            }
        }
    }.value
}
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun GiftScreen(gifts: List<Gift>) {
    val state = rememberLazyListState()
    var isAtBottom = state.isAtBottom()

    var isLoading by remember { mutableStateOf(true) }
    var apiResponse by  remember { mutableStateOf(gifts) }
    val coroutineScope2 = rememberCoroutineScope()
    var load by remember { mutableStateOf(false) }

    if(isLoading) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center){
            CustomCircularProgressBar()
        }
    }
    Column {
        LazyColumn(state = state) {
            items(apiResponse) { gift ->
                isLoading = false
                val coroutineScope = rememberCoroutineScope()
                var stringBody: String by remember { mutableStateOf("") }
                var SuccessOpen by remember {
                    mutableStateOf(false)
                }

                if (SuccessOpen) {
                    AlertDialog(
                        onDismissRequest = {
                            // Dismiss the dialog when the user clicks outside the dialog or on the back button.
                            // If you want to disable that functionality, simply leave this block empty.
                            SuccessOpen = false
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    // perform the confirm action and
                                    // close the dialog
                                    SuccessOpen = false
                                }
                            ) {
                                Text(text = "Nice!!!")
                            }
                        },
                        title = {
                            Text(text = "Consume Success")
                        },
                        text = {
                            Text("Thank you for your puchase")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        shape = RoundedCornerShape(5.dp),
                    )
                }
                var FailOpen by remember {
                    mutableStateOf(false)
                }

                if (FailOpen) {
                    AlertDialog(
                        onDismissRequest = {
                            // Dismiss the dialog when the user clicks outside the dialog or on the back button.
                            // If you want to disable that functionality, simply leave this block empty.
                            FailOpen = false
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    // perform the confirm action and
                                    // close the dialog
                                    FailOpen = false
                                }
                            ) {
                                Text(text = "Check other items")
                            }
                        },
                        title = {
                            Text(text = "Sorry!!!")
                        },
                        text = {
                            Text("Item out of stock right now")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        shape = RoundedCornerShape(5.dp),
                    )
                }

                var dialogOpen by remember {
                    mutableStateOf(false)
                }

                if (dialogOpen) {
                    AlertDialog(
                        onDismissRequest = {
                            // Dismiss the dialog when the user clicks outside the dialog or on the back button.
                            // If you want to disable that functionality, simply leave this block empty.
                            dialogOpen = false
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    // perform the confirm action and
                                    // close the dialog
                                    coroutineScope.launch {

                                        stringBody = postUserConsume(gift._id)
                                        if (stringBody.contains("not available") || stringBody.contains(
                                                "error"
                                            )
                                        ) {
                                            FailOpen = true
                                        } else {
                                            SuccessOpen = true
                                        }
                                    }

                                    dialogOpen = false

                                }
                            ) {
                                Text(text = "Buy Now!!!")
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    // close the dialog
                                    dialogOpen = false
                                }
                            ) {
                                Text(text = "May be later...")
                            }
                        },
                        title = {
                            Text(text = "Confirm Purchase")
                        },
                        text = {
                            Text("Title: " + gift.title)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        shape = RoundedCornerShape(5.dp),
                    )
                }
                var AlertOpen by remember {
                    mutableStateOf(false)
                }

                if (AlertOpen) {
                    AlertDialog(
                        onDismissRequest = {
                            // Dismiss the dialog when the user clicks outside the dialog or on the back button.
                            // If you want to disable that functionality, simply leave this block empty.
                            AlertOpen = false
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    // perform the confirm action and
                                    // close the dialog
                                    AlertOpen = false

                                }
                            ) {
                                Text(text = "Notice!")
                            }
                        },
                        title = {
                            Text(text = "Alert!!!")
                        },
                        text = {
                            Text("You have to Login to use this function")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        shape = RoundedCornerShape(5.dp),
                    )
                }

                AsyncImage(
                    model = gift.image,
                    contentDescription = null,
                    modifier = Modifier.size(150.dp)
                )
                Text("Title: " + gift.title)
                Text("Description: " + gift.description)
                Text("Remaining: " + gift.remaining)
                Button(

                    onClick = {
                        if (ViewLoginModel.token == "") {
                            AlertOpen = true
                        } else {
                            dialogOpen = true
                        }
                    },
                    // or shape = CircleShape
                    contentPadding = PaddingValues(10.dp),
                    colors = ButtonDefaults.buttonColors(Color.Green)

                ) {
                    Text("Consume")
                }

                Divider()
            }


        }
        var count by remember { mutableStateOf(1) }

        LaunchedEffect(isAtBottom) {
            if (isAtBottom) {
                load = true
            }

        }


        if (load==true) {

            var vale:List<Gift>
            count += 1;
            coroutineScope2.launch{
                vale = getgift(count.toString())
                apiResponse= (apiResponse+vale)
            }



            load=false
        }

    }


}


