package io.vieira.xspeedit.packagesort

class NaivePackageSortingAlgorithm(private val packageCapacity: Int = 10) : PackageSortingAlgorithm {

    // Offline heuristics are out of the question, and as we can't sort
    // the items we're in a first-fit kind of situation.
    // Problem is that the naive algorithm has a time complexity of O(n²). We're iterating on each product,
    // and then on each already created bucket to check if a slot is available, or we're adding another bucket otherwise.
    override fun optimize(productSizes: Sequence<Int>): List<List<Int>> {
        val optimizedPackages = mutableListOf<MutableList<Int>>()

        for (productSize in productSizes) {
            when (val index = findRoomInAnExistingPackage(optimizedPackages, productSize)) {
                -1 -> {
                    optimizedPackages += mutableListOf(productSize)
                }

                else -> {
                    optimizedPackages[index] += productSize
                }
            }
        }

        return optimizedPackages
    }

    private fun findRoomInAnExistingPackage(sortedPackages: List<List<Int>>, productSize: Int): Int {
        return sortedPackages
                .indexOfFirst { `package` -> `package`.sum() + productSize <= this.packageCapacity }
    }
}
