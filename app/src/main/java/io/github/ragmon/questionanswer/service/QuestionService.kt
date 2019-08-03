package io.github.ragmon.questionanswer.service

import io.github.ragmon.questionanswer.model.Question
import retrofit2.Call
import retrofit2.http.*

interface QuestionService {
    @GET("question")
    fun listQuestions(): Call<List<Question>>

    @GET("question/{id}")
    fun findQuestion(@Path("id") id: Int): Call<Question>

    @POST("question")
    fun createQuestion(@Body question: Question): Call<Question>

    @PUT("question/{id}")
    fun updateQuestion(@Path("id") id: Int, @Body question: Question): Call<Question>
}