load("@rules_java//java:defs.bzl", "java_binary", "java_library")

# Stack implementations library
java_library(
    name = "stack",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/datastructures/stack:IntStack
java_binary(
    name = "IntStack",
    main_class = "com.williamfiset.algorithms.datastructures.stack.IntStack",
    runtime_deps = [":stack"],
)
