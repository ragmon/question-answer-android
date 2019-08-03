package io.github.ragmon.questionanswer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import io.github.ragmon.questionanswer.model.Question
import io.github.ragmon.questionanswer.service.QuestionService
import io.github.ragmon.questionanswer.ui.question.QuestionFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException

class QuestionActivity : AppCompatActivity(), CreateUpdateQuestionFragment.OnFragmentInteractionListener {

    private lateinit var mQuestionService: QuestionService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_activity)
        if (savedInstanceState == null) {

            val fragment: Fragment
            when (intent.getStringExtra("action")) {
                IntentAction.CREATE.value ->
                    fragment = CreateUpdateQuestionFragment.newInstance()

                IntentAction.UPDATE.value -> {
                    val questionId = intent.getIntExtra("question_id", -1)
                    if (questionId == -1) {
                        throw RuntimeException("Question ID must be set")
                    }
                    fragment = CreateUpdateQuestionFragment.newInstance(questionId)
                }

                IntentAction.READ.value -> {
                    val questionId = intent.getIntExtra("question_id", -1)
                    if (questionId == -1) {
                        throw RuntimeException("Question ID must be set")
                    }
                    fragment = QuestionFragment.newInstance(questionId)
                }

                else -> throw RuntimeException("Action must be set")
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow()

            mQuestionService = Retrofit.build().create(QuestionService::class.java)
        }
    }

    override fun onQuestionChanged(question: Question) {
        val intent = Intent()

        // Creating new question
        if (question.id == null) {
            mQuestionService.createQuestion(question).enqueue(object : Callback<Question> {
                override fun onFailure(call: Call<Question>, t: Throwable) {
                    Log.d(TAG, "Creating question request failure with message: ${t.message}")

                    intent.run {
                        putExtra("status", "error")
                        putExtra("error", t.message)
                    }

                    showErrorNotify("Creating question request failure with message: ${t.message}")
                }

                override fun onResponse(call: Call<Question>, response: Response<Question>) {
                    Log.d(TAG, "Creating question request success with response with message: ${response.message()}")

                    intent.run {
                        putExtra("status", "success")
                        putExtra("question_id", response.body()?.id)
                    }

                    showSuccessNotify("Creating question request success with response with message: ${response.message()}")
                }
            })
        }
        // Updating exists question
        else {
            mQuestionService.updateQuestion(question.id!!, question).enqueue(object : Callback<Question> {
                override fun onFailure(call: Call<Question>, t: Throwable) {
                    Log.d(TAG, "Updating question request failure with message: ${t.message}")

                    intent.run {
                        putExtra("status", "error")
                        putExtra("error", t.message)
                    }

                    showErrorNotify("Updating question request failure with message: ${t.message}")
                }

                override fun onResponse(call: Call<Question>, response: Response<Question>) {
                    Log.d(TAG, "Updating question request success with response with message: ${response.message()}")

                    intent.run {
                        putExtra("status", "success")
                    }

                    showSuccessNotify("Updating question request success with response with message: ${response.message()}")
                }
            })
        }

        setResult(1, intent)
        finish()
    }

    private fun showSuccessNotify(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showErrorNotify(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val TAG = "QuestionActivity"

        @JvmStatic
        fun newIntent(context: Context, action: IntentAction): Intent {
            return Intent(context, QuestionActivity::class.java).apply {
                putExtra("action", action.value)
            }
        }

        @JvmStatic
        fun newIntent(context: Context, action: IntentAction, questionId: Int): Intent {
            return Intent(context, QuestionActivity::class.java).apply {
                putExtra("action", action.value)
                putExtra("question_id", questionId)
            }
        }
    }

}
