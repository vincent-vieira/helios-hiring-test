package io.vieira.xspeedit.packagesort

class PackageSortingAlgorithm(private val packageCapacity: Int = 10) {

    fun optimize(productsSizes: List<Int>): List<List<Int>> {
        val sortedPackages = mutableListOf<MutableList<Int>>()

        for (productSize in productsSizes) {
            when(val index = findRoomInAnExistingPackage(sortedPackages, productSize)) {
                -1 -> {
                    sortedPackages += mutableListOf(productSize)
                }
                else -> {
                    sortedPackages[index] += productSize
                }
            }
        }

        return sortedPackages
    }

    private fun findRoomInAnExistingPackage(sortedPackages: List<List<Int>>, productSize: Int): Int {
        return sortedPackages
                .indexOfFirst { `package` -> `package`.sum() + productSize <= this.packageCapacity }
    }
}
