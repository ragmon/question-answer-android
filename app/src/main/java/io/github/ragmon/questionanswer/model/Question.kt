package io.github.ragmon.questionanswer.model

import com.fasterxml.jackson.annotation.JsonProperty

class Question {
    @JsonProperty("id")
    var id: Int? = null

    @JsonProperty("user_id")
    var userId: String = ""

    @JsonProperty("title")
    var title: String = ""

    @JsonProperty("description")
    var description: String = ""

    @JsonProperty("rate_up")
    var rateUp: Long = 0

    @JsonProperty("rate_down")
    var rateDown: Long = 0

    @JsonProperty("was_answered")
    var wasAnswered: Boolean = false

    @JsonProperty("was_rated")
    var wasRated: Boolean = false

    @JsonProperty("created_at")
    var createdAt: String = ""
}