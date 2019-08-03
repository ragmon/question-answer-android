package io.github.ragmon.questionanswer.model

import com.fasterxml.jackson.annotation.JsonProperty

class Question {
    @JsonProperty("id")
    var id: Int = 0

    @JsonProperty("title")
    lateinit var title: String

    @JsonProperty("description")
    lateinit var description: String
}