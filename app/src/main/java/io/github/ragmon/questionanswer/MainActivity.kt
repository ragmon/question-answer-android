package io.github.ragmon.questionanswer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { createNewQuestion() }
    }

    private fun createNewQuestion() {
        startActivity(QuestionActivity.newIntent(this, IntentAction.CREATE))
    }

    companion object {
        const val TAG = "MainActivity"

        @JvmStatic
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

}
