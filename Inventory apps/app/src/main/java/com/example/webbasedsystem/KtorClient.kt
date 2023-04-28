package com.example.webbasedsystem

import com.example.webbasedsystem.KtorClient.httpClient
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable

object KtorClient {

    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json() // enable the client to perform JSON serialization
        }
        install(Logging)
        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            header("Authorization", "Bearer " + ViewLoginModel.token)
        }
        expectSuccess = true
    }
}
suspend fun getbooks(): List<Book> {
    return httpClient.get("http://comp4107.azurewebsites.net/inventory?type=book").body()
}
suspend fun getgift(page:String ): List<Gift> {
    return httpClient.get("http://comp4107.azurewebsites.net/inventory?page=$page&type=gift").body()
}
suspend fun getmaterial(page:String ): List<Material> {
    return httpClient.get("http://comp4107.azurewebsites.net/inventory?page=$page&type=material").body()
}
suspend fun getgame(page:String ): List<Game> {
    return httpClient.get("http://comp4107.azurewebsites.net/inventory?page=$page&type=game").body()
}
suspend fun getbook(page:String ): List<Book> {
    return httpClient.get("http://comp4107.azurewebsites.net/inventory?page=$page&type=book").body()
}
suspend fun getgifts(): List<Gift> {
    return httpClient.get("http://comp4107.azurewebsites.net/inventory?type=gift").body()
}
suspend fun getmaterials(): List<Material> {

    return httpClient.get("http://comp4107.azurewebsites.net/inventory?type=material").body()
}
suspend fun getgames(): List<Game> {
    return httpClient.get("http://comp4107.azurewebsites.net/inventory?type=game").body()
}
suspend fun getSearchBooks(keyword:String): List<Book>  {
    print(keyword)
    return httpClient.get("http://comp4107.azurewebsites.net/inventory?type=book&keyword=$keyword").body()
}
suspend fun getSearchGames(keyword:String): List<Game>  {
    return httpClient.get("http://comp4107.azurewebsites.net/inventory?type=game&keyword=$keyword").body()
}
suspend fun getSearchGifts(keyword:String): List<Gift>  {
    return httpClient.get("http://comp4107.azurewebsites.net/inventory?type=gift&keyword=$keyword").body()
}
suspend fun getSearchMaterials(keyword:String): List<Material>  {
    return httpClient.get("http://comp4107.azurewebsites.net/inventory?type=material&keyword=$keyword").body()
}
suspend fun postUserLogin(acc:String, pw:String):String{

    var response: String
    try {
        response = httpClient.submitForm(
            "https://comp4107.azurewebsites.net/user/login",
            formParameters = Parameters.build {
                append("email", acc)
                append("password", pw)

            }).body()

    }catch (err:Exception){
        response="wrong password or user account"
    }
    return response
}
suspend fun postUserConsume(itemId:String):String{
    var response: String
    try {
        response = httpClient.post("http://comp4107.azurewebsites.net/user/consume/$itemId").body()


    }catch (err:Exception){
        return "item not available for consume"
    }
    return response
}
suspend fun postUserBorrow(itemId:String):String{
    var response: String
    try {
        response = httpClient.post("http://comp4107.azurewebsites.net/user/borrow/$itemId").body()


    }catch (err:Exception){
        return err.toString()
    }
    return response
}
suspend fun postUserReturn(itemId:String):String{
    var response: String
    try {
        response = httpClient.post("http://comp4107.azurewebsites.net/user/return/$itemId").body()


    }catch (err:Exception){
        return err.toString()
    }
    return response
}
@Serializable
data class HttpBinResponse(
    val args: Map<String, String>,
    val data: String,
    val files: Map<String, String>,
    val form: Map<String, String>,
    val headers: Map<String, String>,
    val json: String?,
    val origin: String,
    val url: String
)
