load("@rules_java//java:defs.bzl", "java_binary", "java_library")

# Segment tree implementations library
java_library(
    name = "segmenttree",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/datastructures/segmenttree:CompactSegmentTree
java_binary(
    name = "CompactSegmentTree",
    main_class = "com.williamfiset.algorithms.datastructures.segmenttree.CompactSegmentTree",
    runtime_deps = [":segmenttree"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/datastructures/segmenttree:GenericSegmentTree
java_binary(
    name = "GenericSegmentTree",
    main_class = "com.williamfiset.algorithms.datastructures.segmenttree.GenericSegmentTree",
    runtime_deps = [":segmenttree"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/datastructures/segmenttree:GenericSegmentTree2
java_binary(
    name = "GenericSegmentTree2",
    main_class = "com.williamfiset.algorithms.datastructures.segmenttree.GenericSegmentTree2",
    runtime_deps = [":segmenttree"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/datastructures/segmenttree:GenericSegmentTree3
java_binary(
    name = "GenericSegmentTree3",
    main_class = "com.williamfiset.algorithms.datastructures.segmenttree.GenericSegmentTree3",
    runtime_deps = [":segmenttree"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/datastructures/segmenttree:MaxQuerySumUpdateSegmentTree
java_binary(
    name = "MaxQuerySumUpdateSegmentTree",
    main_class = "com.williamfiset.algorithms.datastructures.segmenttree.MaxQuerySumUpdateSegmentTree",
    runtime_deps = [":segmenttree"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/datastructures/segmenttree:MinQueryAssignUpdateSegmentTree
java_binary(
    name = "MinQueryAssignUpdateSegmentTree",
    main_class = "com.williamfiset.algorithms.datastructures.segmenttree.MinQueryAssignUpdateSegmentTree",
    runtime_deps = [":segmenttree"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/datastructures/segmenttree:MinQuerySumUpdateSegmentTree
java_binary(
    name = "MinQuerySumUpdateSegmentTree",
    main_class = "com.williamfiset.algorithms.datastructures.segmenttree.MinQuerySumUpdateSegmentTree",
    runtime_deps = [":segmenttree"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/datastructures/segmenttree:RangeQueryPointUpdateSegmentTree
java_binary(
    name = "RangeQueryPointUpdateSegmentTree",
    main_class = "com.williamfiset.algorithms.datastructures.segmenttree.RangeQueryPointUpdateSegmentTree",
    runtime_deps = [":segmenttree"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/datastructures/segmenttree:SumQueryAssignUpdateSegmentTree
java_binary(
    name = "SumQueryAssignUpdateSegmentTree",
    main_class = "com.williamfiset.algorithms.datastructures.segmenttree.SumQueryAssignUpdateSegmentTree",
    runtime_deps = [":segmenttree"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/datastructures/segmenttree:SumQueryMultiplicationUpdateSegmentTree
java_binary(
    name = "SumQueryMultiplicationUpdateSegmentTree",
    main_class = "com.williamfiset.algorithms.datastructures.segmenttree.SumQueryMultiplicationUpdateSegmentTree",
    runtime_deps = [":segmenttree"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/datastructures/segmenttree:SumQuerySumUpdateSegmentTree
java_binary(
    name = "SumQuerySumUpdateSegmentTree",
    main_class = "com.williamfiset.algorithms.datastructures.segmenttree.SumQuerySumUpdateSegmentTree",
    runtime_deps = [":segmenttree"],
)
