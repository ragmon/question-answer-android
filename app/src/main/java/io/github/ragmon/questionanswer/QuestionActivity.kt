package io.github.ragmon.questionanswer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import io.github.ragmon.questionanswer.ui.question.QuestionFragment
import java.lang.RuntimeException

class QuestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_activity)
        if (savedInstanceState == null) {

            val fragment: Fragment
            when (intent.getStringExtra("action")) {
                IntentAction.READ.toString() ->
                    fragment = QuestionFragment.newInstance(intent.getIntExtra("question_id", -1))
                else -> throw RuntimeException("Action must be set")
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow()
        }
    }

    companion object {
        fun newIntent(context: Context, action: IntentAction): Intent {
            return Intent(context, QuestionActivity::class.java)
                .putExtra("action", action)
        }

        fun newIntent(context: Context, action: IntentAction, questionId: Int): Intent {
            return Intent(context, QuestionActivity::class.java)
                .putExtra("action", action)
                .putExtra("question_id", questionId)
        }
    }

}
