package com.example.roomtodo.networks

import android.content.Context
import com.example.roomtodo.Utils.Extensions.NoInternetUtils
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import timber.log.Timber
import java.net.HttpURLConnection

class SafeApiRequests {
    suspend fun <T : Any?> apiRequest(context: Context, call: suspend () -> Response<T>): T? {
        try {
            if (!NoInternetUtils.isConnectedToInternet(context.applicationContext)) {
                throw ApiExceptions("No internet connection!")
            }

            val response = call.invoke()

            if (response.isSuccessful &&
                response.code() >= HttpURLConnection.HTTP_OK &&
                response.code() < HttpURLConnection.HTTP_MULT_CHOICE
            ) {
                return response.body()
            } else {
                val error = response.errorBody()?.string()

                val message = StringBuilder()
                error?.let {
                    try {
                        message.append(JSONObject(it).getString("message"))
                    } catch (e: JSONException) {
                        /* no-op */
                    }

                    message.append("\n")
                }

                message.append("Error Code: ${response.code()}")

                Timber.e("SafeApiRequest: ApiException: $message")

                throw ApiExceptions(message.toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()

            throw ApiExceptions(e.message ?: "Unknown Error!")
        }
    }
}