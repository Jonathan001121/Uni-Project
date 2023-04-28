package com.example.webbasedsystem

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class Material(
    val _id: String,
    val category: String,
    val description: String,
    val image: String,
    val location: String,
    val quantity: Int,
    val remaining: Int,
    val remark: String,
    val title: String,
    val type: String
)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MaterialScreen(materials: List<Material>) {
    val state = rememberLazyListState()
    var isAtBottom = state.isAtBottom()

    var isLoading by remember { mutableStateOf(true) }
    var apiResponse by  remember { mutableStateOf(materials) }
    val coroutineScope2 = rememberCoroutineScope()
    var load by remember { mutableStateOf(false) }
    if(isLoading) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center){
            CustomCircularProgressBar()
        }
    }
    LazyColumn(state = state) {
        items(apiResponse) { material ->

            isLoading=false
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
                        Text("Thank you for your purchase" )
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
                        Text("Item out of stock right now" )
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

                                    stringBody =postUserConsume(material._id)
                                    if(stringBody.contains("not available")|| stringBody.contains("error")) {
                                        FailOpen=true
                                    }else{
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
                        Text("Title: "+material.title )
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
                        Text("You have to Login to use this function" )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    shape = RoundedCornerShape(5.dp),
                )
            }

            AsyncImage(
                model=material.image,
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
            Text("Title: "+material.title)
            Text("Description: "+material.description)
            Text("Remaining: "+material.remaining)
            Button(

                    onClick = { if(ViewLoginModel.token==""){AlertOpen=true }else{dialogOpen = true }},
                // or shape = CircleShape
                contentPadding = PaddingValues(10.dp),
                colors= ButtonDefaults.buttonColors(Color.Green)

            ){
                Text("Consume" )
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

        var vale:List<Material>
        count += 1;
        coroutineScope2.launch{
            vale = getmaterial(count.toString())
            apiResponse= (apiResponse+vale)
        }



        load=false
    }




}