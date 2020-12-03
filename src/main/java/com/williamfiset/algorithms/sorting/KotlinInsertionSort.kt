package com.williamfiset.algorithms.sorting

import kotlin.jvm.JvmStatic

class KotlinInsertionSort : InplaceSort {
    override fun sort(values: IntArray) {
        insertionSort(values)
    }

    companion object {
        private fun insertionSort(ar: IntArray?) {
            if (ar == null) {
                return
            }
            for (i in 1 until ar.size) {
                var j = i
                while (j > 0 && ar[j] < ar[j - 1]) {
                    swap(ar, j - 1, j)
                    j--
                }
            }
        }

        private fun swap(ar: IntArray, i: Int, j: Int) {
            val tmp = ar[i]
            ar[i] = ar[j]
            ar[j] = tmp
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val sorter: InplaceSort = InsertionSort()
            val array = intArrayOf(10, 4, 6, 8, 13, 2, 3)
            sorter.sort(array)
            println(array.contentToString())
        }
    }
}