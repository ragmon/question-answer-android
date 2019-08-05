package io.github.ragmon.questionanswer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders
import io.github.ragmon.questionanswer.ui.main.MainActivityFragment
import io.github.ragmon.questionanswer.ui.question.QuestionViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mQuestionViewModel: QuestionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mQuestionViewModel = ViewModelProviders.of(this)[QuestionViewModel::class.java]

        fab.setOnClickListener { createNewQuestion() }
    }

    private fun createNewQuestion() {
        startActivityForResult(QuestionActivity.newIntent(this, IntentAction.CREATE), REQUEST_CODE_CREATE_QUESTION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_CODE_CREATE_QUESTION -> {
                if (resultCode == QuestionActivity.RC_SUCCESS) {
                    (fragment as MainActivityFragment).onRefresh()
                }
            }
            else -> {
                throw RuntimeException("Unknown request code #$requestCode")
            }
        }
    }

    companion object {
        const val TAG = "MainActivity"

        const val REQUEST_CODE_CREATE_QUESTION = 1

        @JvmStatic
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

}
