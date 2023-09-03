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

    @Test
    fun shouldGenerateWithReplacementsHavingOverlappingDividers() {
        val result = fizzBuzzAlgorithm.generate(limit = 15, replacements = mapOf(3 to "fizz", 5 to "buzz"))

        assertEquals(listOf("1", "2", "fizz", "4", "buzz", "fizz", "7", "8", "fizz", "buzz", "11", "fizz", "13", "14", "io/vieira/fizzbuzz"), result)
    }
}