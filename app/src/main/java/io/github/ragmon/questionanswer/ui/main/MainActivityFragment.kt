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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.github.ragmon.questionanswer.R
import io.github.ragmon.questionanswer.model.Answer
import io.github.ragmon.questionanswer.model.Question
import io.github.ragmon.questionanswer.model.Rate
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
class MainActivityFragment : Fragment(), QuestionListAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var mQuestionService: QuestionService

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipere_fresh_list.setOnRefreshListener(this)
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

    override fun onRefresh() {
        Log.d(TAG, "onRefresh")

        val model = ViewModelProviders.of(this)[QuestionViewModel::class.java]
        model.updateQuestions()

        swipere_fresh_list.isRefreshing = false
    }

    override fun onBtnRateUpClick(view: View) {
        Log.d(TAG, "onBtnRateUpClick")

        val questionId = view.tag as Int
        val rateUpBtn = (view.parent as View).btn_rate_up
        val rateDownBtn = (view.parent as View).btn_rate_down
        val rateUpLabel = (view.parent as View).rate_up

        mQuestionService.questionRateUp(questionId).enqueue(object : Callback<Rate> {
            override fun onFailure(call: Call<Rate>, t: Throwable) {
                Log.e(TAG, "Failure to rate up question #$questionId with error: ${t.message}")
            }

            override fun onResponse(call: Call<Rate>, response: Response<Rate>) {
                Log.d(TAG, "Success to rate up question #$questionId")

                rateUpBtn.setOnClickListener(null)
                rateDownBtn.setOnClickListener(null)

                rateUpLabel.text = (rateUpLabel.text.toString().toInt() + 1).toString() // +1 rate up
            }
        })
    }

    override fun onBtnRateDownClick(view: View) {
        Log.d(TAG, "onBtnRateDownClick")

        val questionId = view.tag as Int
        val rateUpBtn = (view.parent as View).btn_rate_up
        val rateDownBtn = (view.parent as View).btn_rate_down
        val rateDownLabel = (view.parent as View).rate_down

        mQuestionService.questionRateDown(questionId).enqueue(object : Callback<Rate> {
            override fun onFailure(call: Call<Rate>, t: Throwable) {
                Log.e(TAG, "Failure to rate down question #$questionId with error: ${t.message}")
            }

            override fun onResponse(call: Call<Rate>, response: Response<Rate>) {
                Log.d(TAG, "Success to rate down question #$questionId")

                rateUpBtn.setOnClickListener(null)
                rateDownBtn.setOnClickListener(null)

                rateDownLabel.text = (rateDownLabel.text.toString().toInt() + 1).toString() // +1 rate down
            }
        })
    }

    override fun onBtnAnswerClick(view: View) {
        val questionId = view.tag as Int
        val answerInput = (view.parent as View).answer_input
        val answerTextView = (view.parent as View).answer_text_view
        val answerText = answerInput.text.toString()
        val answer = makeAnswer(questionId, answerText)
        val btnAnswer = (view.parent as View).btn_answer

        mQuestionService.answer(questionId, answer).enqueue(object : Callback<Answer> {
            override fun onFailure(call: Call<Answer>, t: Throwable) {
                Log.e(TAG, "Failure sent answer with error: ${t.message}")

                showErrorNotify("Failure sent answer with error: ${t.message}")
            }

            override fun onResponse(call: Call<Answer>, response: Response<Answer>) {
                Log.d(TAG, "Success sent answer")

                showSuccessNotify("Success sent answer")

                btnAnswer.visibility = View.GONE
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
