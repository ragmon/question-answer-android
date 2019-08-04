package io.github.ragmon.questionanswer.model

import com.fasterxml.jackson.annotation.JsonProperty

class Question {
    @JsonProperty("id")
    var id: Int? = null

    @JsonProperty("title")
    var title: String = ""

    @JsonProperty("description")
    var description: String = ""

    @JsonProperty("rate_up")
    var rateUp: Long = 0

    @JsonProperty("rate_down")
    var rateDown: Long = 0

    @JsonProperty("created_at")
    var created_at: String = ""
}