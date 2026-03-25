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
    "//src/main/java/com/williamfiset/algorithms/math:math",
    "@maven//:com_google_truth_truth",
] + JUNIT5_DEPS

# bazel test //src/test/java/com/williamfiset/algorithms/math:CompressedPrimeSieveTest
java_test(
    name = "CompressedPrimeSieveTest",
    srcs = ["CompressedPrimeSieveTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.math.CompressedPrimeSieveTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)
