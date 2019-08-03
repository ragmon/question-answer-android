package io.github.ragmon.questionanswer.model

import com.fasterxml.jackson.annotation.JsonProperty

class Question {
    @JsonProperty("id")
    var id: Int? = null

    @JsonProperty("title")
    lateinit var title: String

    @JsonProperty("description")
    var description: String? = null

    @JsonProperty("rate_up")
    var rateUp: Long = 0

    @JsonProperty("rate_down")
    var rateDown: Long = 0
}