package io.vieira.fizzbuzz.observability

data class MostRequestedFizzBuzzGeneration(val limit: Int, val replacements: Map<Int, String>, val hits: Long)
