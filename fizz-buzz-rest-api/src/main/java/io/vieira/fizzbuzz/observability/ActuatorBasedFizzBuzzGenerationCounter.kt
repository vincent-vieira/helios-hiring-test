package io.vieira.fizzbuzz.observability

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.search.MeterNotFoundException

internal const val metricName = "meter.fizz-buzz.generation-requests"

class ActuatorBasedFizzBuzzGenerationCounter(private val meterRegistry: MeterRegistry) : FizzBuzzGenerationCounter {

    override fun registerNew(limit: Int, replacements: Map<Int, String>) = meterRegistry
            .summary(metricName, *generateTags(limit, replacements))
            .record(1.0)

    private fun generateTags(limit: Int, replacements: Map<Int, String>): Array<String> = arrayOf(
            "limit",
            limit.toString(),
            *replacements
                    .entries
                    .flatMapIndexed { index, (key, value) -> listOf("int${index + 1}", key.toString(), "str${index + 1}", value) }
                    .toTypedArray()
    )

    override fun findMostRequested(): MostRequestedFizzBuzzGeneration? {
        return try {
            val meterSearch = meterRegistry.get(metricName)
            val distributionSummaries = meterSearch.summaries()
            val mostRequestedDistributionSummary = distributionSummaries.maxBy { it.count() }
            val tags = mostRequestedDistributionSummary.id.tags

            MostRequestedFizzBuzzGeneration(
                    tags.first { it.key == "limit" }.value.toInt(),
                    tags
                            .filter { it.key.startsWith("str") }
                            // Sorting by str1, str2...
                            .sortedBy { it.key.replace("str", "").toInt() }
                            .withIndex()
                            .associateBy({ (index, _) -> tags.first { it.key == "int${index + 1}" }.value.toInt() }, { (_, tag) -> tag.value }),
                    mostRequestedDistributionSummary.count()
            )
        } catch (notFoundException: MeterNotFoundException) {
            null
        }
    }
}