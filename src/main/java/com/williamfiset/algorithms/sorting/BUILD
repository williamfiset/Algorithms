load("@rules_java//java:defs.bzl", "java_binary", "java_library")

# Sorting algorithms library
java_library(
    name = "sorting",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/sorting:BubbleSort
java_binary(
    name = "BubbleSort",
    main_class = "com.williamfiset.algorithms.sorting.BubbleSort",
    runtime_deps = [":sorting"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/sorting:BucketSort
java_binary(
    name = "BucketSort",
    main_class = "com.williamfiset.algorithms.sorting.BucketSort",
    runtime_deps = [":sorting"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/sorting:CountingSort
java_binary(
    name = "CountingSort",
    main_class = "com.williamfiset.algorithms.sorting.CountingSort",
    runtime_deps = [":sorting"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/sorting:Heapsort
java_binary(
    name = "Heapsort",
    main_class = "com.williamfiset.algorithms.sorting.Heapsort",
    runtime_deps = [":sorting"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/sorting:InsertionSort
java_binary(
    name = "InsertionSort",
    main_class = "com.williamfiset.algorithms.sorting.InsertionSort",
    runtime_deps = [":sorting"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/sorting:MergeSort
java_binary(
    name = "MergeSort",
    main_class = "com.williamfiset.algorithms.sorting.MergeSort",
    runtime_deps = [":sorting"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/sorting:QuickSelect
java_binary(
    name = "QuickSelect",
    main_class = "com.williamfiset.algorithms.sorting.QuickSelect",
    runtime_deps = [":sorting"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/sorting:QuickSort
java_binary(
    name = "QuickSort",
    main_class = "com.williamfiset.algorithms.sorting.QuickSort",
    runtime_deps = [":sorting"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/sorting:QuickSort3
java_binary(
    name = "QuickSort3",
    main_class = "com.williamfiset.algorithms.sorting.QuickSort3",
    runtime_deps = [":sorting"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/sorting:RadixSort
java_binary(
    name = "RadixSort",
    main_class = "com.williamfiset.algorithms.sorting.RadixSort",
    runtime_deps = [":sorting"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/sorting:SelectionSort
java_binary(
    name = "SelectionSort",
    main_class = "com.williamfiset.algorithms.sorting.SelectionSort",
    runtime_deps = [":sorting"],
)
