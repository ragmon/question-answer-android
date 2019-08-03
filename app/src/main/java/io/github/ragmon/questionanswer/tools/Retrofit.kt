package io.github.ragmon.questionanswer.tools

import retrofit2.converter.jackson.JacksonConverterFactory

class Retrofit {
    companion object {
        private lateinit var retrofit: retrofit2.Retrofit

        fun build(): retrofit2.Retrofit {
            if ( ! Companion::retrofit.isInitialized) {
                retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl("http://localhost/api/v1/")
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }
}