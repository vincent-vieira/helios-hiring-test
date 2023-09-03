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
        val replacements = mapOf(request.int1 to request.str1, request.int2 to request.str2)

        val result = fizzBuzzAlgorithm.generate(limit = request.limit, replacements = replacements)
        fizzBuzzGenerationCounter.registerNew(limit = request.limit, replacements = replacements)
        return result
    }
}

data class FizzBuzzGenerationRequest @JsonCreator constructor(
        @JsonProperty("limit") @Min(1) val limit: Int = 100,
        val int1: Int = 3,
        val int2: Int = 5,
        val str1: String = "fizz",
        val str2: String = "buzz"
)
