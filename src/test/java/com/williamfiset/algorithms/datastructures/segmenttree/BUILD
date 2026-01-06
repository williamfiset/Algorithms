load("@rules_java//java:defs.bzl", "java_test")

# Common dependencies for JUnit 5 tests
JUNIT5_DEPS = [
    "@maven//:org_junit_jupiter_junit_jupiter_api",
    "@maven//:org_junit_jupiter_junit_jupiter_engine",
    "@maven//:org_junit_jupiter_junit_jupiter_params",
]

JUNIT5_RUNTIME_DEPS = [
    "@maven//:org_junit_platform_junit_platform_console",
]

TEST_DEPS = [
    "//src/main/java/com/williamfiset/algorithms/datastructures:segmenttree",
    "//src/main/java/com/williamfiset/algorithms/utils:utils",
    "@maven//:com_google_guava_guava",
    "@maven//:com_google_truth_truth",
    "@maven//:junit_junit",
] + JUNIT5_DEPS

# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/segmenttree:GenericSegmentTree2Test
java_test(
    name = "GenericSegmentTree2Test",
    srcs = ["GenericSegmentTree2Test.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.datastructures.segmenttree.GenericSegmentTree2Test"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/segmenttree:GenericSegmentTreeTest
java_test(
    name = "GenericSegmentTreeTest",
    srcs = ["GenericSegmentTreeTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.datastructures.segmenttree.GenericSegmentTreeTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/segmenttree:MaxQuerySumUpdateSegmentTreeTest
java_test(
    name = "MaxQuerySumUpdateSegmentTreeTest",
    srcs = ["MaxQuerySumUpdateSegmentTreeTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.datastructures.segmenttree.MaxQuerySumUpdateSegmentTreeTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/segmenttree:MinQueryAssignUpdateSegmentTreeTest
java_test(
    name = "MinQueryAssignUpdateSegmentTreeTest",
    srcs = ["MinQueryAssignUpdateSegmentTreeTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.datastructures.segmenttree.MinQueryAssignUpdateSegmentTreeTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/segmenttree:MinQuerySumUpdateSegmentTreeTest
java_test(
    name = "MinQuerySumUpdateSegmentTreeTest",
    srcs = ["MinQuerySumUpdateSegmentTreeTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.datastructures.segmenttree.MinQuerySumUpdateSegmentTreeTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/segmenttree:SegmentTreeWithPointersTest
java_test(
    name = "SegmentTreeWithPointersTest",
    srcs = ["SegmentTreeWithPointersTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.datastructures.segmenttree.SegmentTreeWithPointersTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/segmenttree:SumQueryAssignUpdateSegmentTreeTest
java_test(
    name = "SumQueryAssignUpdateSegmentTreeTest",
    srcs = ["SumQueryAssignUpdateSegmentTreeTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.datastructures.segmenttree.SumQueryAssignUpdateSegmentTreeTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/segmenttree:SumQueryMultiplicationUpdateSegmentTreeTest
java_test(
    name = "SumQueryMultiplicationUpdateSegmentTreeTest",
    srcs = ["SumQueryMultiplicationUpdateSegmentTreeTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.datastructures.segmenttree.SumQueryMultiplicationUpdateSegmentTreeTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/segmenttree:SumQuerySumUpdateSegmentTreeTest
java_test(
    name = "SumQuerySumUpdateSegmentTreeTest",
    srcs = ["SumQuerySumUpdateSegmentTreeTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.datastructures.segmenttree.SumQuerySumUpdateSegmentTreeTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# Run all tests
# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/segmenttree:all
