package io.vieira.fizzbuzz

class KotlinRangesFizzBuzzAlgorithm : FizzBuzzAlgorithm {

    override fun generate(limit: Int, replacements: Map<Int, String>): List<String> {
        return (1..limit).map { number ->
            val transformedValue = replacements
                    .entries
                    .filter { number % it.key == 0 }
                    .map { it.value }
                    .joinToString("")

            return@map when (transformedValue) {
                "" -> number.toString()
                else -> transformedValue
            }
        }
    }
}