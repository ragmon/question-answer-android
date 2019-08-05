package io.github.ragmon.questionanswer.tools

import com.fasterxml.jackson.databind.ObjectMapper
import retrofit2.converter.jackson.JacksonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient

class Retrofit {
    companion object {
        private lateinit var retrofit: retrofit2.Retrofit

        private fun buildInterceptor(): HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            return interceptor
        }

        private fun buildHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
            OkHttpClient.Builder().addInterceptor(interceptor).build()

        @JvmStatic
        fun build(): retrofit2.Retrofit {
            if ( ! Companion::retrofit.isInitialized) {
                val interceptor = buildInterceptor()
                val client = buildHttpClient(interceptor)

                val objectMapper = ObjectMapper()

                retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/api/v1/")
                    .client(client)
                    .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                    .build()
            }
            return retrofit
        }
    }
}