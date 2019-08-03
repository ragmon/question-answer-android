package io.github.ragmon.questionanswer.ui.question

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import io.github.ragmon.questionanswer.R
import io.github.ragmon.questionanswer.model.Question
import kotlinx.android.synthetic.main.question_list_item.view.*

class QuestionListAdapter(private val mQuestionList: List<Question>, onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<QuestionListAdapter.QuestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.question_list_item, parent, false) as ConstraintLayout

        return QuestionViewHolder(view)
    }

    override fun getItemCount(): Int = mQuestionList.size

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question: Question = mQuestionList[position]

        holder.itemView.apply {
            question_title.text = question.title
            question_description.text = question.description
            rate_up.text = question.rateUp.toString()
            rate_down.text = question.rateDown.toString()

            btn_rate_up.tag = question.id
            btn_rate_down.tag = question.id
            btn_answer.tag = question.id

            btn_rate_up.setOnClickListener(onBtnRateUpClickListener)
            btn_rate_down.setOnClickListener(onBtnRateDownClickListener)
            btn_answer.setOnClickListener(onBtnAnswerClickListener)
        }
    }

    private val onBtnRateUpClickListener = View.OnClickListener {
        onItemClickListener.onBtnRateUpClick(it)
    }

    private val onBtnRateDownClickListener = View.OnClickListener {
        onItemClickListener.onBtnRateDownClick(it)
    }

    private val onBtnAnswerClickListener = View.OnClickListener {
        onItemClickListener.onBtnAnswerClick(it)
    }

    interface OnItemClickListener {
        fun onBtnRateUpClick(view: View)
        fun onBtnRateDownClick(view: View)
        fun onBtnAnswerClick(view: View)
    }

    class QuestionViewHolder(private val view: ConstraintLayout): RecyclerView.ViewHolder(view)
}