package io.vieira.fizzbuzz.observability

data class MostRequestedFizzBuzzGeneration(val hits: Long, val limit: Int, val replacements: Map<Int, String>)
