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
    "//src/main/java/com/williamfiset/algorithms/geometry:geometry",
    "//src/main/java/com/williamfiset/algorithms/utils:utils",
    "@maven//:com_google_guava_guava",
    "@maven//:com_google_truth_truth",
] + JUNIT5_DEPS

# bazel test //src/test/java/com/williamfiset/algorithms/geometry:ConvexHullMonotoneChainsAlgorithmTest
java_test(
    name = "ConvexHullMonotoneChainsAlgorithmTest",
    srcs = ["ConvexHullMonotoneChainsAlgorithmTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.geometry.ConvexHullMonotoneChainsAlgorithmTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/geometry:MinimumCostConvexPolygonTriangulationTest
java_test(
    name = "MinimumCostConvexPolygonTriangulationTest",
    srcs = ["MinimumCostConvexPolygonTriangulationTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.geometry.MinimumCostConvexPolygonTriangulationTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# Run all tests
# bazel test //src/test/java/com/williamfiset/algorithms/geometry:all
