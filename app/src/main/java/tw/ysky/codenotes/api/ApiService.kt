package tw.ysky.codenotes.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import tw.ysky.codenotes.data.PostItem

interface ApiService {

    @GET("/posts")
    suspend fun getListDataAsync(): Response<List<PostItem>>

    @POST("/login")
    suspend fun postLoginDataAsync(): Response<String>
}