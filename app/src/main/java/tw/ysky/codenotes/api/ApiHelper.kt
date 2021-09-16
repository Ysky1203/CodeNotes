package tw.ysky.codenotes.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import tw.ysky.codenotes.utils.Const
import java.util.concurrent.TimeUnit


class ApiHelper {
    private val retrofit: Retrofit
    private val service: ApiService

    init {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory()).build()

        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(Const.TIMEOUT_REQUEST, TimeUnit.SECONDS)
            .readTimeout(Const.TIMEOUT_READ, TimeUnit.SECONDS)
            .writeTimeout(Const.TIMEOUT_WRITE, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(Const.URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        service = retrofit.create(ApiService::class.java)
    }

    fun getService(): ApiService{
        return service
    }
}