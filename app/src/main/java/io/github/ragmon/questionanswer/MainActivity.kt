package io.github.ragmon.questionanswer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { createNewQuestion() }
    }

    private fun createNewQuestion() {
        startActivityForResult(QuestionActivity.newIntent(this, IntentAction.CREATE), REQUEST_CODE_CREATE_QUESTION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_CODE_CREATE_QUESTION -> {
                if (resultCode == QuestionActivity.RESULT_CODE_SUCCESS) {
                    // TODO: add new question to list and show notify
                }
            }
            else -> {
                throw RuntimeException("Unknown request code #$requestCode")
            }
        }
    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)

        //
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
