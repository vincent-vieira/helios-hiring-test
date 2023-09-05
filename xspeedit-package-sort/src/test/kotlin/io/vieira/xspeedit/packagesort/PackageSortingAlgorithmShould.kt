package io.vieira.xspeedit.packagesort

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class PackageSortingAlgorithmShould {

    @ParameterizedTest
    @MethodSource("sortingAlgorithms")
    fun workWithDefaults(packageSortingAlgorithm: PackageSortingAlgorithm) {
        val result = packageSortingAlgorithm.optimize(sequenceOf(1, 6, 3, 8, 4, 1, 6, 8, 9, 5, 2, 5, 7, 7, 3))

        assertEquals(listOf(
                listOf(1, 6, 3),
                listOf(8, 1),
                listOf(4, 6),
                listOf(8, 2),
                listOf(9),
                listOf(5, 5),
                listOf(7, 3),
                listOf(7)
        ), result)
    }

    @ParameterizedTest
    @MethodSource("sortingAlgorithmsWithCustomSize")
    fun workWithACustomPackageSize(packageSortingAlgorithm: PackageSortingAlgorithm) {
        val result = packageSortingAlgorithm.optimize(sequenceOf(3, 1, 1, 2, 1, 1, 1))

        assertEquals(listOf(
                listOf(3),
                listOf(1, 1, 1),
                listOf(2, 1),
                listOf(1)
        ), result)
    }

    companion object {
        @JvmStatic
        fun sortingAlgorithms(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(BruteforcePackageSortingAlgorithm()),
            )
        }

        @JvmStatic
        fun sortingAlgorithmsWithCustomSize(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(BruteforcePackageSortingAlgorithm(packageCapacity = 3))
            )
        }
    }
}