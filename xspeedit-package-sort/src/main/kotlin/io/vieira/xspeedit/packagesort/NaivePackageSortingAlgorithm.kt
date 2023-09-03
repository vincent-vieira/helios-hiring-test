package io.vieira.xspeedit.packagesort

class NaivePackageSortingAlgorithm(private val packageCapacity: Int = 10) : PackageSortingAlgorithm {

    // As said in the subject, we need to design the algorithm as we're receiving items live, don't know the full
    // list of products and organize them on the fly. Offline heuristics are out of the question, and as we can't sort
    // the items we're in a first-fit kind of situation.
    // Problem is that the naive algorithm has a time complexity of O(n²). We're iterating on each product,
    // and then on each already created bucket to check if a slot is available, or we're adding another bucket otherwise.
    override fun optimize(productsSizes: List<Int>): List<List<Int>> {
        val optimizedPackages = mutableListOf<MutableList<Int>>()

        for (productSize in productsSizes) {
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
