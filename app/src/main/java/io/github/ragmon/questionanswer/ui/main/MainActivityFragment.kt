package io.github.ragmon.questionanswer.ui.main

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.ragmon.questionanswer.R
import io.github.ragmon.questionanswer.model.Answer
import io.github.ragmon.questionanswer.model.Question
import io.github.ragmon.questionanswer.service.QuestionService
import io.github.ragmon.questionanswer.tools.Retrofit
import io.github.ragmon.questionanswer.ui.question.QuestionListAdapter
import io.github.ragmon.questionanswer.ui.question.QuestionViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.question_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A placeholder fragment containing a simple view.
 */
class MainActivityFragment : Fragment(), QuestionListAdapter.OnItemClickListener {

    private lateinit var mQuestionService: QuestionService

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mQuestionService = Retrofit.build().create(QuestionService::class.java)

        val model = ViewModelProviders.of(this)[QuestionViewModel::class.java]
        model.getQuestions().observe(this, Observer { questions ->
            updateUI(questions)
        })
    }

    private fun updateUI(questions: List<Question>) {
        Log.d(TAG, "updateUI")

        viewManager = LinearLayoutManager(this.context)
        viewAdapter = QuestionListAdapter(questions, this)

        question_list.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onBtnRateUpClick(view: View) {
        val questionId = view.tag as Int
        val rateUpBtn = (view.parent as View).btn_rate_up
        val rateDownBtn = (view.parent as View).btn_rate_down
        val rateUpLabel = (view.parent as View).rate_up

        mQuestionService.questionRateUp(questionId).enqueue(object : Callback<Question> {
            override fun onFailure(call: Call<Question>, t: Throwable) {
                Log.e(TAG, "Failure to rate up question #$questionId with error: ${t.message}")
            }

            override fun onResponse(call: Call<Question>, response: Response<Question>) {
                Log.d(TAG, "Success to rate up question #$questionId")

                rateUpBtn.setOnClickListener(null)
                rateDownBtn.setOnClickListener(null)

                rateUpLabel.text = (rateUpLabel.text.toString().toInt() + 1).toString() // +1 rate up
            }
        })
    }

    override fun onBtnRateDownClick(view: View) {
        val questionId = view.tag as Int
        val rateUpBtn = (view.parent as View).btn_rate_up
        val rateDownBtn = (view.parent as View).btn_rate_down
        val rateDownLabel = (view.parent as View).rate_down

        mQuestionService.questionRateUp(questionId).enqueue(object : Callback<Question> {
            override fun onFailure(call: Call<Question>, t: Throwable) {
                Log.e(TAG, "Failure to rate down question #$questionId with error: ${t.message}")
            }

            override fun onResponse(call: Call<Question>, response: Response<Question>) {
                Log.d(TAG, "Success to rate down question #$questionId")

                rateUpBtn.setOnClickListener(null)
                rateDownBtn.setOnClickListener(null)

                rateDownLabel.text = (rateDownLabel.text.toString().toInt() + 1).toString() // +1 rate up
            }
        })
    }

    override fun onBtnAnswerClick(view: View) {
        val questionId = view.tag as Int
        val answerInput = (view.parent as View).answer_input
        val answerTextView = (view.parent as View).answer_text_view
        val answerText = answerInput.text.toString()
        val answer = makeAnswer(questionId, answerText)

        mQuestionService.answer(questionId, answer).enqueue(object : Callback<Answer> {
            override fun onFailure(call: Call<Answer>, t: Throwable) {
                showErrorNotify("Failure sent answer with error: ${t.message}")
            }

            override fun onResponse(call: Call<Answer>, response: Response<Answer>) {
                showSuccessNotify("Success sent answer")

                answerInput.visibility = View.GONE

                answerTextView.text = answerText
                answerTextView.visibility = View.VISIBLE
            }
        })
    }

    private fun makeAnswer(questionId: Int, text: String): Answer {
        val answer = Answer()
        answer.question_id = questionId
        answer.text = text

        return answer
    }

    private fun showSuccessNotify(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
    }

    private fun showErrorNotify(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val TAG = "MainActivityFragment"
    }

}
