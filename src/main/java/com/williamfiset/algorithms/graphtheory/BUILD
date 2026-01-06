load("@rules_java//java:defs.bzl", "java_library")

# Graph theory algorithms library
java_library(
    name = "graphtheory",
    srcs = glob(
        ["*.java"],
        exclude = [
            "networkflow/**",
            "treealgorithms/**",
            "examples/**",
            "analysis/**",
        ],
    ),
    visibility = ["//visibility:public"],
    deps = ["//src/main/java/com/williamfiset/algorithms/utils/graphutils:graphutils"],
)

# Network flow algorithms
java_library(
    name = "networkflow",
    srcs = glob(["networkflow/*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        ":graphtheory",
        "//src/main/java/com/williamfiset/algorithms/utils/graphutils:graphutils",
    ],
)

# Tree algorithms
java_library(
    name = "treealgorithms",
    srcs = glob(["treealgorithms/*.java"]),
    visibility = ["//visibility:public"],
    deps = [":graphtheory"],
)

# Examples
java_library(
    name = "examples",
    srcs = glob(["examples/**/*.java"]),
    visibility = ["//visibility:public"],
    deps = [":graphtheory"],
)

# Analysis
java_library(
    name = "analysis",
    srcs = glob(["analysis/**/*.java"]),
    visibility = ["//visibility:public"],
    deps = [":graphtheory"],
)
