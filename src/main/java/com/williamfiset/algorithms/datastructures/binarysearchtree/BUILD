load("@rules_java//java:defs.bzl", "java_binary", "java_library")

# Binary search tree implementations library
java_library(
    name = "binarysearchtree",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = ["//src/main/java/com/williamfiset/algorithms/datastructures/utils"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/datastructures/binarysearchtree:SplayTree
java_binary(
    name = "SplayTree",
    main_class = "com.williamfiset.algorithms.datastructures.binarysearchtree.SplayTree",
    runtime_deps = [":binarysearchtree"],
)
