package io.github.ragmon.questionanswer.ui.question

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.ragmon.questionanswer.model.Question
import io.github.ragmon.questionanswer.service.QuestionService
import io.github.ragmon.questionanswer.tools.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionViewModel : ViewModel() {

    private val questionService: QuestionService = Retrofit.build().create(QuestionService::class.java)

    private val questions: MutableLiveData<List<Question>> by lazy {
        MutableLiveData<List<Question>>().also {
            loadQuestions()
        }
    }

    fun getQuestions(): LiveData<List<Question>> {
        return questions
    }

    private fun loadQuestions() {
        questionService.listQuestions().enqueue(object : Callback<List<Question>> {
            override fun onFailure(call: Call<List<Question>>, t: Throwable) {
                Log.e(TAG, "Failure to load questions with error: ${t.message}")
            }

            override fun onResponse(call: Call<List<Question>>, response: Response<List<Question>>) {
                Log.d(TAG, "Success to load questions")
                questions.value = response.body()
            }
        })
    }

    private lateinit var question: MutableLiveData<Question>

    fun getQuestion(id: Int): LiveData<Question> {
        if (question == null) {
            question = MutableLiveData()
            loadQuestion(id)
        }
        return question
    }

    private fun loadQuestion(id: Int) {
        questionService.findQuestion(id).enqueue(object : Callback<Question> {
            override fun onFailure(call: Call<Question>, t: Throwable) {
                Log.e(TAG, "Failure to load question with error: ${t.message}")
            }

            override fun onResponse(call: Call<Question>, response: Response<Question>) {
                Log.d(TAG, "Success to load question")
                question.value = response.body()
            }
        })
    }

    companion object {
        const val TAG = "QuestionViewModel"
    }
}
