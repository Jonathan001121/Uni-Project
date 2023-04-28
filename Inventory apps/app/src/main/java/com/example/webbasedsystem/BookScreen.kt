package com.example.webbasedsystem

import android.annotation.SuppressLint
import android.view.View
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState


import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch

@Serializable
data class Book(
    val _id: String,
    val author: String,
    val borrower: String,
    val category: String,
    val description: String,
    val image: String,
    val isbn: String,
    val location: String,
    val publisher: String,
    val remark: String,
    val title: String,
    val type: String,
    val year: String
)

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun BookScreen(books: List<Book>) {
    val state = rememberLazyListState()
    var isAtBottom = state.isAtBottom()

    var isLoading by remember { mutableStateOf(true) }
    var apiResponse by  remember { mutableStateOf(books) }
    val coroutineScope2 = rememberCoroutineScope()
    var load by remember { mutableStateOf(false) }
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CustomCircularProgressBar()
        }
    }
    LazyColumn(state = state) {
        items(apiResponse) { book ->
            var isBorrowed by remember { if(book.borrower=="me")mutableStateOf(true)else{mutableStateOf(false)} }
            var borrower by remember {
                mutableStateOf(book.borrower) }
            isLoading = false
            val coroutineScope = rememberCoroutineScope()
            var stringBody: String by remember { mutableStateOf("") }
            val padding = 10.dp

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
                        Text(text = "Borrow Success")
                    },
                    text = {
                        Text("Thank you")
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

                                    stringBody = postUserBorrow(book._id)
                                    if (stringBody.contains("not available") || stringBody.contains(
                                            "error"
                                        )
                                    ) {
                                        FailOpen = true
                                    } else {
                                        SuccessOpen = true
                                        isBorrowed=true
                                        borrower="me"
                                    }
                                }

                                dialogOpen = false

                            }
                        ) {
                            Text(text = "Borrow Now!!!")
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
                        Text(text = "Confirm Borrow")
                    },
                    text = {
                        Text("Title: " + book.title)
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
            //////////////////////////////////////////////////
            var Success2Open by remember {
                mutableStateOf(false)
            }

            if (Success2Open) {
                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back button.
                        // If you want to disable that functionality, simply leave this block empty.
                        Success2Open = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                // perform the confirm action and
                                // close the dialog
                                Success2Open = false
                            }
                        ) {
                            Text(text = "Nice!!!")
                        }
                    },
                    title = {
                        Text(text = "Return Success")
                    },
                    text = {
                        Text("Thank you")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    shape = RoundedCornerShape(5.dp),
                )
            }
            var Fail2Open by remember {
                mutableStateOf(false)
            }
            if (Fail2Open) {
                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back button.
                        // If you want to disable that functionality, simply leave this block empty.
                        Fail2Open = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                // perform the confirm action and
                                // close the dialog
                                Fail2Open = false
                            }
                        ) {
                            Text(text = "Ok")
                        }
                    },
                    title = {
                        Text(text = "Sorry!!!")
                    },
                    text = {
                        Text("Item not found")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    shape = RoundedCornerShape(5.dp),
                )
            }

            var dialog2Open by remember {
                mutableStateOf(false)
            }

            if (dialog2Open) {
                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back button.
                        // If you want to disable that functionality, simply leave this block empty.
                        dialog2Open = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                // perform the confirm action and
                                // close the dialog
                                coroutineScope.launch {

                                    stringBody = postUserReturn(book._id)
                                    if (stringBody.contains("not find") || stringBody.contains(
                                            "error"
                                        )
                                    ) {
                                        Fail2Open = true
                                    } else {
                                        Success2Open = true
                                        isBorrowed=false
                                        borrower="none"
                                    }
                                }

                                dialog2Open = false

                            }
                        ) {
                            Text(text = "Return Now!!!")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                // close the dialog
                                dialog2Open = false
                            }
                        ) {
                            Text(text = "May be later...")
                        }
                    },
                    title = {
                        Text(text = "Confirm Return")
                    },
                    text = {
                        Text("Title: " + book.title)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    shape = RoundedCornerShape(5.dp),
                )
            }
///////////////////////////////////////


            AsyncImage(
                model = book.image,
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
            Text("Title: " + book.title)
            Text("Description: " + book.description)
            Text("borrow by: $borrower")

            if (!isBorrowed) {
                Button(
                    onClick = {
                        if (ViewLoginModel.token == "") {
                            AlertOpen = true
                        } else {
                            dialogOpen = true
                        }

                    },
                    contentPadding = PaddingValues(10.dp),
                    colors = ButtonDefaults.buttonColors(Color.Green)

                ) {
                    Text("Borrow")
                }
            }
            if(isBorrowed ){
                Button(

                    onClick = {
                        if (ViewLoginModel.token == "") {
                            AlertOpen = true
                        } else {
                            dialog2Open = true
                        }
                    },
                    // or shape = CircleShape
                    contentPadding = PaddingValues(10.dp),
                    colors = ButtonDefaults.buttonColors(Color.Green)

                ) {
                    Text("Return")
                }
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

        var vale:List<Book>
        count += 1;
        coroutineScope2.launch{
            vale = getbook(count.toString())
            apiResponse= (apiResponse+vale)
        }



        load=false
    }


}