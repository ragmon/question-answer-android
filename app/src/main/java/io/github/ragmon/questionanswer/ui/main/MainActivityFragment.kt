package io.github.ragmon.questionanswer.ui.main

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        viewManager = LinearLayoutManager(this.context)
        viewAdapter = QuestionListAdapter(questions, this)

        question_list.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onBtnRateUpClick(view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBtnRateDownClick(view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBtnAnswerClick(view: View) {
        val questionId = view.tag as Int

        //
    }
}
