package io.vieira.fizzbuzz

import io.micrometer.core.instrument.MeterRegistry
import io.vieira.fizzbuzz.observability.ActuatorBasedFizzBuzzGenerationCounter
import io.vieira.fizzbuzz.observability.FizzBuzzGenerationCounter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

fun main() {
    SpringApplication.run(FizzBuzzApplication::class.java)
}

@SpringBootApplication
open class FizzBuzzApplication {

    @Bean
    open fun kotlinFizzBuzzAlgorithm(): FizzBuzzAlgorithm {
        return KotlinRangesFizzBuzzAlgorithm()
    }

    @Bean
    open fun actuatorBasedFizzBuzzGenerationCounter(meterRegistry: MeterRegistry): FizzBuzzGenerationCounter {
        return ActuatorBasedFizzBuzzGenerationCounter(meterRegistry)
    }
}