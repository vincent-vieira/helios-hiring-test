package io.vieira.xspeedit.packagesort

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PackageSortingAlgorithmShould {

    @Test
    fun workWithDefaults() {
        val result = PackageSortingAlgorithm()
                .optimize(listOf(1, 6, 3, 8, 4, 1, 6, 8, 9, 5, 2, 5, 7, 7, 3))

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
}