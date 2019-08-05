package io.github.ragmon.questionanswer.model

import com.fasterxml.jackson.annotation.JsonProperty

class Answer {
    @JsonProperty("id")
    var id: Int? = null

    @JsonProperty("question_id")
    var questionId: Int = 0

    @JsonProperty("user_id")
    var userId: String = ""

    @JsonProperty("text")
    var text: String = ""

    @JsonProperty("created_at")
    var createdAt: String = ""

    enum class Type(val value: String) {
        QUESTION("question"),
        ANSWER("answer")
    }
}