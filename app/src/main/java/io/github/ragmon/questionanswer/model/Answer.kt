package io.github.ragmon.questionanswer.model

import com.fasterxml.jackson.annotation.JsonProperty

class Answer {
    @JsonProperty("id")
    var id: Int? = null

    @JsonProperty("question_id")
    var question_id: Int = 0

    @JsonProperty("text")
    var text: String = ""

    @JsonProperty("created_at")
    var created_at: String = ""

    enum class Type(val value: String) {
        QUESTION("question"),
        ANSWER("answer")
    }
}