package io.vieira.fizzbuzz.observability

interface FizzBuzzGenerationCounter {

    fun registerNew(limit: Int, replacements: Map<Int, String>)

    fun findMostRequested(): MostRequestedFizzBuzzGeneration?
}