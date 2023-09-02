package io.vieira.fizzbuzz.observability

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/fizz-buzz/statistics")
class FizzBuzzStatisticsController(private val fizzBuzzGenerationCounter: FizzBuzzGenerationCounter) {

    @GetMapping("/most-requested")
    fun getMostRequestedGeneration(): ResponseEntity<MostRequestedFizzBuzzGenerationResponse> {
        return fizzBuzzGenerationCounter
                .findMostRequested()
                ?.let { MostRequestedFizzBuzzGenerationResponse(it) }
                ?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()
    }
}
