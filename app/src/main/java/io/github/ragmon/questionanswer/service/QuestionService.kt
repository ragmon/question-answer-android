package io.github.ragmon.questionanswer.service

import io.github.ragmon.questionanswer.model.Question
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface QuestionService {
    @GET("question")
    fun listQuestions(): Call<List<Question>>

    @GET("question/{id}")
    fun findQuestion(@Path("id") id: Int): Call<Question>
}