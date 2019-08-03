package io.github.ragmon.questionanswer.ui.question

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import io.github.ragmon.questionanswer.R
import io.github.ragmon.questionanswer.model.Question

class QuestionFragment(questionId: Int) : Fragment() {

    private var mQuestionId: Int = questionId

    companion object {
        fun newInstance(questionId: Int): QuestionFragment {
            return QuestionFragment(questionId)
        }
    }

    private lateinit var viewModel: QuestionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.question_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuestionViewModel::class.java)
        viewModel.getQuestion(mQuestionId).observe(this, Observer { question ->
            updateUI(question)
        })
    }

    private fun updateUI(question: Question) {

    }

}
