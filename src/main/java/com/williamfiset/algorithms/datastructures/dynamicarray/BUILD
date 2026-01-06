load("@rules_java//java:defs.bzl", "java_binary", "java_library")

# Dynamic array implementations library
java_library(
    name = "dynamicarray",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/datastructures/dynamicarray:IntArray
java_binary(
    name = "IntArray",
    main_class = "com.williamfiset.algorithms.datastructures.dynamicarray.IntArray",
    runtime_deps = [":dynamicarray"],
)
