package io.github.ragmon.questionanswer.model

import com.fasterxml.jackson.annotation.JsonProperty

class Rate {
    @JsonProperty("id")
    var id: Int? = null

    @JsonProperty("resource_id")
    var resource_id: Int = 0

    @JsonProperty("resource_type")
    var resource_type: String = ""

    @JsonProperty("user_id")
    var userId: String = ""

    @JsonProperty("action")
    var action: String = ""

    @JsonProperty("created_at")
    var created_at: String = ""
}