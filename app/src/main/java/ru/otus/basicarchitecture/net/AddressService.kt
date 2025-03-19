package ru.otus.basicarchitecture.net

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.otus.basicarchitecture.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressService @Inject constructor() {
    private val BASE_URL = "https://suggestions.dadata.ru/suggestions/"
    private val client = OkHttpClient()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(DaDataService::class.java)
    suspend fun request(query: String, callback: (response: AddressData?) -> Unit) {
        val response = service.getAddressHint(AddressQuery(query))
        if (response.isSuccessful) {
            callback(response.body())
        } else {
            Log.e("Error", "${response.code()} ${response.message()}")
        }
    }

    interface DaDataService {
        @POST("api/4_1/rs/suggest/address")
        @Headers(
            "Content-Type:application/json",
            "Accept:application/json",
            "Authorization:Token ${BuildConfig.API_KEY}"
        )
        suspend fun getAddressHint(@Body query: AddressQuery): Response<AddressData>
    }
}