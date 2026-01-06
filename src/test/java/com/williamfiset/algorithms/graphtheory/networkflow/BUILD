load("@rules_java//java:defs.bzl", "java_test")

# Common dependencies for JUnit 5 tests
JUNIT5_DEPS = [
    "@maven//:org_junit_jupiter_junit_jupiter_api",
    "@maven//:org_junit_jupiter_junit_jupiter_engine",
]

JUNIT5_RUNTIME_DEPS = [
    "@maven//:org_junit_platform_junit_platform_console",
]

TEST_DEPS = [
    "//src/main/java/com/williamfiset/algorithms/graphtheory:graphtheory",
    "//src/main/java/com/williamfiset/algorithms/graphtheory/networkflow:networkflow",
    "//src/main/java/com/williamfiset/algorithms/utils:utils",
    "//src/main/java/com/williamfiset/algorithms/utils/graphutils:graphutils",
    "@maven//:com_google_guava_guava",
    "@maven//:com_google_truth_truth",
    "@maven//:org_apache_commons_commons_lang3",
    "@maven//:junit_junit",
] + JUNIT5_DEPS

# Network flow tests

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory/networkflow:BipartiteGraphCheckAdjacencyListTest
java_test(
    name = "BipartiteGraphCheckAdjacencyListTest",
    srcs = ["BipartiteGraphCheckAdjacencyListTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.networkflow.BipartiteGraphCheckAdjacencyListTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory/networkflow:MaxFlowTests
java_test(
    name = "MaxFlowTests",
    srcs = ["MaxFlowTests.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.networkflow.MaxFlowTests"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory/networkflow:MinCostMaxFlowTests
java_test(
    name = "MinCostMaxFlowTests",
    srcs = ["MinCostMaxFlowTests.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.networkflow.MinCostMaxFlowTests"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# Run all tests
# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory/networkflow:all
