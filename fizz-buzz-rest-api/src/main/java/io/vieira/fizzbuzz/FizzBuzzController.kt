package io.vieira.fizzbuzz

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import io.vieira.fizzbuzz.observability.FizzBuzzGenerationCounter
import jakarta.validation.constraints.Min
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/fizz-buzz")
class FizzBuzzController(private val fizzBuzzAlgorithm: FizzBuzzAlgorithm,
                         private val fizzBuzzGenerationCounter: FizzBuzzGenerationCounter) {

    @PostMapping
    fun generateFizzBuzz(@RequestBody request: FizzBuzzGenerationRequest): List<String> {
        val result = fizzBuzzAlgorithm.generate(limit = request.limit, replacements = request.replacements)
        fizzBuzzGenerationCounter.registerNew(limit = request.limit, replacements = request.replacements)
        return result
    }
}

data class FizzBuzzGenerationRequest @JsonCreator constructor(
        @JsonProperty("limit") @Min(1) val limit: Int = 100,
        @JsonProperty("replacements", required = false) val replacements: Map<Int, String> = mapOf(3 to "fizz", 5 to "buzz")
)
