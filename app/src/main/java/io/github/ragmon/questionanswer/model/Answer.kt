package io.github.ragmon.questionanswer.model

import com.fasterxml.jackson.annotation.JsonProperty

class Answer {
    @JsonProperty("id")
    var id: Int? = null

    @JsonProperty("question_id")
    var question_id: Int = 0

    @JsonProperty("text")
    lateinit var text: String
}