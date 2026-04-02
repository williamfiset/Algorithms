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

# bazel test //src/test/java/com/williamfiset/algorithms/math:PrimeFactorizationTest
java_test(
    name = "PrimeFactorizationTest",
    srcs = ["PrimeFactorizationTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.math.PrimeFactorizationTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/math:EulerTotientFunctionTest
java_test(
    name = "EulerTotientFunctionTest",
    srcs = ["EulerTotientFunctionTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.math.EulerTotientFunctionTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/math:ChineseRemainderTheoremTest
java_test(
    name = "ChineseRemainderTheoremTest",
    srcs = ["ChineseRemainderTheoremTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.math.ChineseRemainderTheoremTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/math:ModPowTest
java_test(
    name = "ModPowTest",
    srcs = ["ModPowTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.math.ModPowTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/math:BinomialCoefficientModPrimeTest
java_test(
    name = "BinomialCoefficientModPrimeTest",
    srcs = ["BinomialCoefficientModPrimeTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.math.BinomialCoefficientModPrimeTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)
