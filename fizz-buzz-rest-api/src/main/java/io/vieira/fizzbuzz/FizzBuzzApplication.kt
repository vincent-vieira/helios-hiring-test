package io.vieira.fizzbuzz

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

fun main() {
    SpringApplication.run(FizzBuzzApplication::class.java)
}

@SpringBootApplication
open class FizzBuzzApplication