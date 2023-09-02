package io.vieira.fizzbuzz

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/fizz-buzz")
class FizzBuzzController(private val fizzBuzzAlgorithm: FizzBuzzAlgorithm) {

    @PostMapping
    fun generateFizzBuzz(@RequestBody request: FizzBuzzGenerationRequest): List<String> {
        return fizzBuzzAlgorithm.generate(limit = request.limit, replacements = request.replacements)
    }
}

data class FizzBuzzGenerationRequest(val limit: Int = 100, val replacements: Map<Int, String> = mapOf(
        3 to "fizz", 5 to "buzz", 15 to "fizzbuzz"
))
