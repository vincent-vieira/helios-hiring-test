package io.vieira.fizzbuzz.observability

import com.fasterxml.jackson.annotation.JsonProperty

class MostRequestedFizzBuzzGenerationResponse(mostRequestedFizzBuzzGeneration: MostRequestedFizzBuzzGeneration) {

    @field:JsonProperty
    val limit: Int

    @field:JsonProperty
    val int1: Int

    @field:JsonProperty
    val int2: Int

    @field:JsonProperty
    val str1: String

    @field:JsonProperty
    val str2: String

    @field:JsonProperty
    val count: Long

    init {
        this.limit = mostRequestedFizzBuzzGeneration.limit
        this.count = mostRequestedFizzBuzzGeneration.hits
        this.int1 = mostRequestedFizzBuzzGeneration.replacements.keys.first()
        this.int2 = mostRequestedFizzBuzzGeneration.replacements.keys.elementAt(1)
        this.str1 = mostRequestedFizzBuzzGeneration.replacements.values.first()
        this.str2 = mostRequestedFizzBuzzGeneration.replacements.values.elementAt(1)
    }
}
