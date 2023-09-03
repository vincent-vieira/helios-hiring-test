package io.vieira.fizzbuzz.observability

import io.vieira.fizzbuzz.observability.ActuatorBasedFizzBuzzGenerationCounter
import io.vieira.fizzbuzz.observability.MostRequestedFizzBuzzGeneration
import io.vieira.fizzbuzz.observability.metricName
import io.micrometer.core.instrument.DistributionSummary
import io.micrometer.core.instrument.Meter
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tags
import io.micrometer.core.instrument.search.RequiredSearch
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ActuatorBasedFizzBuzzGenerationCounterTest {

    @MockK
    private lateinit var meterRegistry: MeterRegistry

    @InjectMockKs
    private lateinit var generationCounter: ActuatorBasedFizzBuzzGenerationCounter

    @Test
    fun shouldUseTheCorrectMetricWhenRegisteringAHit() {
        val distributionSummary: DistributionSummary = mockk<DistributionSummary>()
        every { meterRegistry.summary(any(), *anyVararg()) } returns distributionSummary
        every { distributionSummary.record(any()) } just runs

        generationCounter.registerNew(limit = 10, replacements = mapOf(3 to "fizz"))

        verifySequence {
            meterRegistry.summary(metricName, "limit", "10", "int1", "3", "str1", "fizz")
            distributionSummary.record(1.0)
        }
        confirmVerified(meterRegistry, distributionSummary)
    }

    @Test
    fun shouldProcessHighestCountDistributionSummaryToReturnStatistics() {
        val highestDistributionSummary: DistributionSummary = mockk<DistributionSummary>()
        val lowestDistributionSummary: DistributionSummary = mockk<DistributionSummary>()
        val requiredSearch: RequiredSearch = mockk<RequiredSearch>()

        every { highestDistributionSummary.count() } returns 10
        every { highestDistributionSummary.id } returns Meter.Id(metricName, Tags.of("limit", "100", "int1", "3", "str1", "fizz"), "", "", Meter.Type.DISTRIBUTION_SUMMARY)
        every { lowestDistributionSummary.count() } returns 5
        every { lowestDistributionSummary.id } returns Meter.Id(metricName, Tags.of("limit", "30", "int1", "3", "str1", "fizz"), "", "", Meter.Type.DISTRIBUTION_SUMMARY)
        every { meterRegistry.get(any()) } returns requiredSearch
        every { requiredSearch.summaries() } returns listOf(lowestDistributionSummary, highestDistributionSummary)

        val result = generationCounter.findMostRequested()

        assertEquals(MostRequestedFizzBuzzGeneration(limit = 100, replacements = mapOf(3 to "fizz"), hits = 10), result)

        verifySequence {
            meterRegistry.get(metricName)
            requiredSearch.summaries()
            lowestDistributionSummary.count()
            highestDistributionSummary.count()
            highestDistributionSummary.id
            highestDistributionSummary.count()
        }
        confirmVerified(meterRegistry, requiredSearch, lowestDistributionSummary, highestDistributionSummary)
    }
}