package io.github.ragmon.questionanswer

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.ragmon.questionanswer.model.Question
import kotlinx.android.synthetic.main.fragment_create_update_question.*


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM_QUESTION_ID = "question_id"

class CreateUpdateQuestionFragment : Fragment() {

    private var questionId: Int? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            questionId = it.getInt(ARG_PARAM_QUESTION_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_update_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSave.setOnClickListener(onSaveButtonPressed)
    }

    private val onSaveButtonPressed = View.OnClickListener {
        val question = makeQuestion(
            id = questionId,
            title = question_title.text.toString(),
            description = question_description.text.toString()
        )
        listener?.onQuestionChanged(question)
    }

    private fun makeQuestion(id: Int? = null, title: String, description: String? = null): Question {
        val question = Question()
        question.id = id
        question.title = title
        question.description = description
        return question
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onQuestionChanged(question: Question)
    }

    companion object {

        @JvmStatic
        fun newInstance() = CreateUpdateQuestionFragment()

        @JvmStatic
        fun newInstance(questionId: Int) =
            CreateUpdateQuestionFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM_QUESTION_ID, questionId)
                }
            }
    }
}
