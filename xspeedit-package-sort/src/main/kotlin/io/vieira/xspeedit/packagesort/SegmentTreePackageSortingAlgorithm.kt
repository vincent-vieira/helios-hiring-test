package io.vieira.xspeedit.packagesort

import java.util.*

class SegmentTreePackageSortingAlgorithm(private val packageCapacity: Int = 10) : PackageSortingAlgorithm {

    // After many researches, I've been able to figure out that I need to represent a package as a binary tree node, each node
    // containing its child, but also the index of the package (in order to return them ordered properly to the user),
    // the list of products inside the package but also the remaining capacity for each package. The goal is to be able to,
    // from the root of the tree, be able to identify on which side of the tree we need to dive in order to fill an existing package.
    // Segment trees seem to be a good candidate for that, I've unfortunately not been able to complete a working implementation properly.
    override fun optimize(productSizes: Sequence<Int>): List<List<Int>> {
        val iterator = productSizes.iterator()
        if (!iterator.hasNext()) return listOf()

        val index = 0;
        val productSize = iterator.next()
        var rootNode = PackageNode(
                index = index,
                remainingCapacity = this.packageCapacity,
                bestRemainingCapacityInSubtree = this.packageCapacity,
                left = null,
                right = null
        )
        rootNode.addProduct(productSize)

        return extractProducts(rootNode)
    }

    private fun extractProducts(rootNode: PackageNode): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        val stack = Stack<PackageNode>()
        stack.push(rootNode)

        while (stack.isNotEmpty()) {
            val currentNode = stack.pop()
            result += currentNode.products

            if (currentNode.right != null) {
                stack.push(currentNode.right)
            }

            if (currentNode.left != null) {
                stack.push(currentNode.left)
            }
        }
        return result
    }
}

data class PackageNode(val index: Int,
                       var remainingCapacity: Int,
                       var bestRemainingCapacityInSubtree: Int,
                       var left: PackageNode?,
                       var right: PackageNode?) {

    private var productSizes: List<Int> = mutableListOf()

    val products: List<Int>
        get() = productSizes.toList()

    fun addProduct(productSize: Int) {
        if (remainingCapacity >= productSize) {
            remainingCapacity -= productSize
            productSizes += productSize
        } else {

        }
    }
}