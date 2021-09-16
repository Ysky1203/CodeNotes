package tw.ysky.codenotes.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tw.ysky.codenotes.data.PostItem
import java.io.IOException

class ApiRepo constructor(private val service: ApiService) {

    suspend fun getListData(): Result<List<PostItem>> {
        return withContext(Dispatchers.IO) {

            val response = service.getListDataAsync()
            if (response.isSuccessful) {
                return@withContext Result.Success(response.body()!!)
            } else {
                return@withContext Result.Error(IOException("Cannot get data from server"))
            }
        }
    }

    suspend fun postLogin(): Result<String> {
        return withContext(Dispatchers.IO) {

            val response = service.postLoginDataAsync()
            if (response.isSuccessful) {
                return@withContext Result.Success(response.body()!!)
            } else {
                return@withContext Result.Error(IOException("Cannot get data from server"))
            }
        }
    }

}