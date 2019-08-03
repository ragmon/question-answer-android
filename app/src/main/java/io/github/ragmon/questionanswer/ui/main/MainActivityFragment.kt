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
import io.github.ragmon.questionanswer.model.Question
import io.github.ragmon.questionanswer.ui.question.QuestionViewModel
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A placeholder fragment containing a simple view.
 */
class MainActivityFragment : Fragment() {

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val model = ViewModelProviders.of(this)[QuestionViewModel::class.java]
        model.getQuestions().observe(this, Observer { questions ->
            updateUI(questions)
        })
    }

    private fun updateUI(questions: List<Question>) {
        viewManager = LinearLayoutManager(this.context)
        viewAdapter = MyAdapter(questions)
        question_list.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }
}
