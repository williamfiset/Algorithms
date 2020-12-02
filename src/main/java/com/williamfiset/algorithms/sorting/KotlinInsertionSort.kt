package com.williamfiset.algorithms.sorting

fun insertionsort(list:MutableList<Int>):List<Int> {
    if (list.isEmpty() || list.size < 2){
        return list
    }
    for (i in 1..list.size - 1){
        val item = list[i]
        var j = i
        while (j > 0 && item < list[j - 1]){
            list[j] = list[j - 1]
            j -= 1
        }
        list[j] = item
    }
    return list

}