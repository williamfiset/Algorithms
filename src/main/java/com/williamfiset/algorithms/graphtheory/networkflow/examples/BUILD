load("@rules_java//java:defs.bzl", "java_binary", "java_library")

# Network flow algorithms lib
java_library(
    name = "networkflow_examples",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//src/main/java/com/williamfiset/algorithms/utils/graphutils:graphutils",
    ],
)

################################################################################
# Binary targets for network flow examples
################################################################################

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory/networkflow/examples:CapacityScalingExample
java_binary(
    name = "CapacityScalingExample",
    main_class = "com.williamfiset.algorithms.graphtheory.networkflow.examples.CapacityScalingExample",
    runtime_deps = [":networkflow_examples"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory/networkflow/examples:DinicsExample
java_binary(
    name = "DinicsExample",
    main_class = "com.williamfiset.algorithms.graphtheory.networkflow.examples.DinicsExample",
    runtime_deps = [":networkflow_examples"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory/networkflow/examples:EdmondsKarpExample
java_binary(
    name = "EdmondsKarpExample",
    main_class = "com.williamfiset.algorithms.graphtheory.networkflow.examples.EdmondsKarpExample",
    runtime_deps = [":networkflow_examples"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory/networkflow/examples:FordFulkersonExample
java_binary(
    name = "FordFulkersonExample",
    main_class = "com.williamfiset.algorithms.graphtheory.networkflow.examples.FordFulkersonExample",
    runtime_deps = [":networkflow_examples"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory/networkflow/examples:MiceAndOwls
java_binary(
    name = "MiceAndOwls",
    main_class = "com.williamfiset.algorithms.graphtheory.networkflow.examples.MiceAndOwls",
    runtime_deps = [":networkflow_examples"],
)

