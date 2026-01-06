load("@rules_java//java:defs.bzl", "java_binary", "java_library")

# Example library
java_library(
    name = "example_lib",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory/examples:EagerPrimsExample
java_binary(
    name = "EagerPrimsExample",
    main_class = "com.williamfiset.algorithms.graphtheory.examples.EagerPrimsExample",
    runtime_deps = [":example_lib"],
)
