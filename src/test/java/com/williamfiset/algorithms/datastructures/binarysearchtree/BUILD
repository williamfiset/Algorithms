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
    "//src/main/java/com/williamfiset/algorithms/datastructures:binarysearchtree",
    "//src/main/java/com/williamfiset/algorithms/utils:utils",
    "@maven//:com_google_guava_guava",
    "@maven//:com_google_truth_truth",
    "@maven//:junit_junit",
] + JUNIT5_DEPS

# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/binarysearchtree:BinarySearchTreeTest
java_test(
    name = "BinarySearchTreeTest",
    srcs = ["BinarySearchTreeTest.java"],
    args = ["--select-class=com.williamfiset.algorithms.datastructures.binarysearchtree.BinarySearchTreeTest"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/binarysearchtree:SplayTreeTest
java_test(
    name = "SplayTreeTest",
    srcs = ["SplayTreeTest.java"],
    args = ["--select-class=com.williamfiset.algorithms.datastructures.binarysearchtree.SplayTreeTest"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS + ["//src/main/java/com/williamfiset/algorithms/datastructures:utils"],
)

# Run all tests
# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/binarysearchtree:all
