package com.williamfiset.algorithms.sorting

fun insertionSortTest() {
    println(insertionsort(mutableListOf(10, 4, 6, 8, 13, 2, 3)))
    println(insertionsort(mutableListOf(12, 26, 18, 18, 10, 2, 6, 0)))
    println(insertionsort(mutableListOf(-1, 2, -10, 11, 19)))
}

fun main(args: Array<String>) {
    insertionSortTest()
}

