load("@rules_java//java:defs.bzl", "java_binary", "java_library")

# Queue implementations library
java_library(
    name = "queue",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/datastructures/queue:IntQueue
java_binary(
    name = "IntQueue",
    main_class = "com.williamfiset.algorithms.datastructures.queue.IntQueue",
    runtime_deps = [":queue"],
)
