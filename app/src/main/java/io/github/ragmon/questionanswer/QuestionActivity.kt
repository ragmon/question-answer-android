package io.github.ragmon.questionanswer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.ragmon.questionanswer.ui.question.QuestionFragment

class QuestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, QuestionFragment.newInstance())
                .commitNow()
        }
    }

}
