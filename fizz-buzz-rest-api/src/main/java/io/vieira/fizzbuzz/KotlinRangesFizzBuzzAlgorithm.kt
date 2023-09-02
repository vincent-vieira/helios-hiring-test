package io.vieira.fizzbuzz

class KotlinRangesFizzBuzzAlgorithm : FizzBuzzAlgorithm {

    override fun generate(limit: Int, replacements: Map<Int, String>): List<String> {
        return (1..limit).map { number ->
            replacements
                    .entries
                    .firstOrNull { number % it.key == 0 }?.value ?: number.toString()
        }
    }
}