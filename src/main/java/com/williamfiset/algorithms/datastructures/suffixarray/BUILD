load("@rules_java//java:defs.bzl", "java_binary", "java_library")

# Suffix array implementations library
java_library(
    name = "suffixarray",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/datastructures/suffixarray:SuffixArrayFast
java_binary(
    name = "SuffixArrayFast",
    main_class = "com.williamfiset.algorithms.datastructures.suffixarray.SuffixArrayFast",
    runtime_deps = [":suffixarray"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/datastructures/suffixarray:SuffixArrayMed
java_binary(
    name = "SuffixArrayMed",
    main_class = "com.williamfiset.algorithms.datastructures.suffixarray.SuffixArrayMed",
    runtime_deps = [":suffixarray"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/datastructures/suffixarray:SuffixArraySlow
java_binary(
    name = "SuffixArraySlow",
    main_class = "com.williamfiset.algorithms.datastructures.suffixarray.SuffixArraySlow",
    runtime_deps = [":suffixarray"],
)
