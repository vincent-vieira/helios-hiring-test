package io.vieira.fizzbuzz

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
}