package io.vieira.fizzbuzz

interface FizzBuzzAlgorithm {

    fun generate(limit: Int, replacements: Map<Int, String>): List<String>
}
