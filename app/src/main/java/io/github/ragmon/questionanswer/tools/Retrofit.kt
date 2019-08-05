package io.github.ragmon.questionanswer.tools

import okhttp3.Interceptor
import retrofit2.converter.jackson.JacksonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class Retrofit {
    companion object {
        private lateinit var retrofit: retrofit2.Retrofit
        private lateinit var userId: String

        private fun buildLoggerInterceptor(): HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            return interceptor
        }

        private fun buildAuthInterceptor(): Interceptor {
            return object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request = chain.request()
                    val newRequest: Request

                    newRequest = request.newBuilder()
                        .addHeader("x-user-id", userId)
                        .build()

                    return chain.proceed(newRequest)
                }
            }
        }

        fun build(): retrofit2.Retrofit {
            if ( ! Companion::retrofit.isInitialized) {
                val client = OkHttpClient.Builder()
                    .addInterceptor(buildLoggerInterceptor())
                    .addInterceptor(buildAuthInterceptor())
                    .build()

                retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/api/v1/")
                    .client(client)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

        fun setUserId(userId: String) {
            Retrofit.userId = userId
        }
    }
}