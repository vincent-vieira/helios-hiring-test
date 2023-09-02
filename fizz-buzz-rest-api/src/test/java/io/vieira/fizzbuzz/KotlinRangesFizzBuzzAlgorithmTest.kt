package io.vieira.fizzbuzz

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class KotlinRangesFizzBuzzAlgorithmTest {

    private val fizzBuzzAlgorithm = KotlinRangesFizzBuzzAlgorithm()

    @Test
    fun shouldGenerateASimpleRangeWithBasicReplacements() {
        val result = fizzBuzzAlgorithm.generate(limit = 3, replacements = mapOf(3 to "fizz"))

        assertEquals(listOf("1", "2", "fizz"), result)
    }
}