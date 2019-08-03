package io.github.ragmon.questionanswer.ui.question

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
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<List<Question>>, response: Response<List<Question>>) {
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
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<Question>, response: Response<Question>) {
                question.value = response.body()
            }
        })
    }
}
