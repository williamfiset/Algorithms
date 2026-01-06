load("@rules_java//java:defs.bzl", "java_binary", "java_library")

# Analysis library
java_library(
    name = "analysis",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = ["//src/main/java/com/williamfiset/algorithms/utils/graphutils:graphutils"],
)

################################################################################
# Binary targets for analysis
################################################################################

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory/analysis:PrimsGraphRepresentationAnaylsis
java_binary(
    name = "PrimsGraphRepresentationAnaylsis",
    main_class = "com.williamfiset.algorithms.graphtheory.analysis.PrimsGraphRepresentationAnaylsis",
    runtime_deps = [":analysis"],
)
