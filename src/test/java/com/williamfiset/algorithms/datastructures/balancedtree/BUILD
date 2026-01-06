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
    "//src/main/java/com/williamfiset/algorithms/datastructures/balancedtree:balancedtree",
    "//src/main/java/com/williamfiset/algorithms/utils:utils",
    "@maven//:com_google_guava_guava",
    "@maven//:com_google_truth_truth",
    "@maven//:junit_junit",
] + JUNIT5_DEPS

# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/balancedtree:AVLTreeTest
java_test(
    name = "AVLTreeTest",
    srcs = ["AVLTreeTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.datastructures.balancedtree.AVLTreeTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/balancedtree:RedBlackTreeTest
java_test(
    name = "RedBlackTreeTest",
    srcs = ["RedBlackTreeTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.datastructures.balancedtree.RedBlackTreeTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/balancedtree:TreapTreeTest
java_test(
    name = "TreapTreeTest",
    srcs = ["TreapTreeTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.datastructures.balancedtree.TreapTreeTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# Run all tests
# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/balancedtree:all
