package io.vieira.xspeedit.packagesort

interface PackageSortingAlgorithm {

    // As said in the subject, we need to design the algorithm as we're receiving items live, don't know the full
    // list of products and organize them on the fly. That's why we take a Sequence as an input, representing a basic iterator
    // over the product sizes.
    fun optimize(productSizes: Sequence<Int>): List<List<Int>>
}