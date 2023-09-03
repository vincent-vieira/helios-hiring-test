package io.vieira.xspeedit.packagesort

interface PackageSortingAlgorithm {

    fun optimize(productsSizes: List<Int>): List<List<Int>>
}