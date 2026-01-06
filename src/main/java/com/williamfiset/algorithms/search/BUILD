load("@rules_java//java:defs.bzl", "java_binary", "java_library")

# Search algorithms library
java_library(
    name = "search",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/search:BinarySearch
java_binary(
    name = "BinarySearch",
    main_class = "com.williamfiset.algorithms.search.BinarySearch",
    runtime_deps = [":search"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/search:InterpolationSearch
java_binary(
    name = "InterpolationSearch",
    main_class = "com.williamfiset.algorithms.search.InterpolationSearch",
    runtime_deps = [":search"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/search:TernarySearch
java_binary(
    name = "TernarySearch",
    main_class = "com.williamfiset.algorithms.search.TernarySearch",
    runtime_deps = [":search"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/search:TernarySearchDiscrete
java_binary(
    name = "TernarySearchDiscrete",
    main_class = "com.williamfiset.algorithms.search.TernarySearchDiscrete",
    runtime_deps = [":search"],
)
