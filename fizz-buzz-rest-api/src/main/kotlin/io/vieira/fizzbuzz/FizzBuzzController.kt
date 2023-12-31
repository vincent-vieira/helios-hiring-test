package io.vieira.fizzbuzz

import com.fasterxml.jackson.annotation.JsonCreator
import io.vieira.fizzbuzz.observability.FizzBuzzGenerationCounter
import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/fizz-buzz")
class FizzBuzzController(private val fizzBuzzAlgorithm: FizzBuzzAlgorithm,
                         private val fizzBuzzGenerationCounter: FizzBuzzGenerationCounter) {

    @PostMapping
    fun generateFizzBuzz(@RequestBody @Valid request: FizzBuzzGenerationRequest): List<String> {
        val replacements = mapOf(request.int1 to request.str1, request.int2 to request.str2)

        val result = fizzBuzzAlgorithm.generate(limit = request.limit, replacements = replacements)
        fizzBuzzGenerationCounter.registerNew(limit = request.limit, replacements = replacements)

        return result
    }
}

data class FizzBuzzGenerationRequest @JsonCreator constructor(
        @field:Min(1) @field:Max(100000) val limit: Int = 100,
        @field:Min(1) @field:Max(100000) val int1: Int = 3,
        @field:Min(1) @field:Max(100000) val int2: Int = 5,
        @field:NotNull val str1: String = "fizz",
        @field:NotNull val str2: String = "buzz"
)
