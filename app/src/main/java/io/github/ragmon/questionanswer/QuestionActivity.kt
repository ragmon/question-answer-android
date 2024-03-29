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
import io.github.ragmon.questionanswer.tools.Retrofit
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

                IntentAction.READ.value -> {
                    val questionId = intent.getIntExtra("questionId", -1)
                    if (questionId == -1) {
                        throw RuntimeException("Question ID must be set")
                    }
                    fragment = QuestionFragment.newInstance(questionId)
                }

                IntentAction.UPDATE.value -> {
                    val questionId = intent.getIntExtra("questionId", -1)
                    if (questionId == -1) {
                        throw RuntimeException("Question ID must be set")
                    }
                    fragment = CreateUpdateQuestionFragment.newInstance(questionId)
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

                    showErrorNotify("Creating question request failure with message: ${t.message}")
                }

                override fun onResponse(call: Call<Question>, response: Response<Question>) {
                    Log.d(TAG, "Creating question request success with response with message: ${response.message()}")

                    intent.putExtra("questionId", response.body()?.id)

                    showSuccessNotify("Creating question request success")

                    setResultAndFinish(RC_SUCCESS, intent)
                }
            })
        }
        // Updating exists question
        else {
            mQuestionService.updateQuestion(question.id!!, question).enqueue(object : Callback<Question> {
                override fun onFailure(call: Call<Question>, t: Throwable) {
                    Log.d(TAG, "Updating question request failure with message: ${t.message}")

                    intent.putExtra("error", t.message)

                    setResultAndFinish(RC_ERROR, intent)
                }

                override fun onResponse(call: Call<Question>, response: Response<Question>) {
                    Log.d(TAG, "Updating question request success with response with message: ${response.message()}")

                    showSuccessNotify("Updating question request success")

                    setResultAndFinish(RC_SUCCESS)
                }
            })
        }
    }

    private fun showSuccessNotify(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showErrorNotify(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun setResultAndFinish(resultCode: Int, intent: Intent? = null) {
        setResult(resultCode, intent)
        finish()
    }

    companion object {
        const val TAG = "QuestionActivity"

        const val RC_SUCCESS = 1
        const val RC_ERROR = 2

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
                putExtra("questionId", questionId)
            }
        }
    }

}
