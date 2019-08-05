package io.github.ragmon.questionanswer.service

import io.github.ragmon.questionanswer.model.Answer
import io.github.ragmon.questionanswer.model.Question
import io.github.ragmon.questionanswer.model.Rate
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

    @DELETE("question/{id}")
    fun deleteQuestion(@Path("id") id: Int): Call<Question>

    @POST("question/{id}/rate_up")
    fun questionRateUp(@Path("id") id: Int): Call<Rate>

    @POST("question/{id}/rate_down")
    fun questionRateDown(@Path("id") id: Int): Call<Rate>

    @POST("question/{id}/answer")
    fun answer(@Path("id") id: Int, @Body answer: Answer): Call<Answer>
}