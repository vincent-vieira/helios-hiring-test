package io.vieira.fizzbuzz

class KotlinRangesFizzBuzzAlgorithm : FizzBuzzAlgorithm {

    override fun generate(limit: Int, replacements: Map<Int, String>): Sequence<String> {
        return (1..limit).asSequence().map { number ->
            val transformedValue = replacements
                    .entries
                    .filter { number % it.key == 0 }
                    .joinToString("") { it.value }

            return@map when (transformedValue) {
                "" -> number.toString()
                else -> transformedValue
            }
        }
    }
}