package io.github.ragmon.questionanswer.ui.question

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.ragmon.questionanswer.model.Question

class QuestionViewModel : ViewModel() {
    private val questions: MutableLiveData<List<Question>> by lazy {
        MutableLiveData<List<Question>>().also {
            loadQuestions()
        }
    }

    fun getQuestions(): LiveData<List<Question>> {
        return questions
    }

    private fun loadQuestions() {
        // TODO: Do an asynchronous operation to fetch questions
    }

    private lateinit var question: MutableLiveData<Question>

    fun getQuestion(id: Int): LiveData<Question> {
        if (question == null) {
            question = MutableLiveData()
            loadQuestion(id)
        }
    }

    private fun loadQuestion(id: Int) {
        // TODO: Do an asynchronous operation to fetch question by ID
    }
}
