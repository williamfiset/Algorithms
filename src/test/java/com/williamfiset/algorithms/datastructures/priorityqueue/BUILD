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
    "//src/main/java/com/williamfiset/algorithms/datastructures/priorityqueue:priorityqueue",
    "//src/main/java/com/williamfiset/algorithms/utils:utils",
    "@maven//:com_google_guava_guava",
    "@maven//:com_google_truth_truth",
    "@maven//:junit_junit",
] + JUNIT5_DEPS

# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/priorityqueue:BinaryHeapQuickRemovalsTest
java_test(
    name = "BinaryHeapQuickRemovalsTest",
    srcs = ["BinaryHeapQuickRemovalsTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.datastructures.priorityqueue.BinaryHeapQuickRemovalsTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/priorityqueue:BinaryHeapTest
java_test(
    name = "BinaryHeapTest",
    srcs = ["BinaryHeapTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.datastructures.priorityqueue.BinaryHeapTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/priorityqueue:MinDHeapTest
java_test(
    name = "MinDHeapTest",
    srcs = ["MinDHeapTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.datastructures.priorityqueue.MinDHeapTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/priorityqueue:MinIndexedBinaryHeapTest
java_test(
    name = "MinIndexedBinaryHeapTest",
    srcs = ["MinIndexedBinaryHeapTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.datastructures.priorityqueue.MinIndexedBinaryHeapTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# Run all tests
# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/priorityqueue:all
