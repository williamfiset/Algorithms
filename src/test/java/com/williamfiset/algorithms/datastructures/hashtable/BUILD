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
    "//src/main/java/com/williamfiset/algorithms/datastructures/hashtable:hashtable",
    "//src/main/java/com/williamfiset/algorithms/utils:utils",
    "@maven//:com_google_guava_guava",
    "@maven//:com_google_truth_truth",
    "@maven//:junit_junit",
] + JUNIT5_DEPS

# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/hashtable:HashTableDoubleHashingTest
java_test(
    name = "HashTableDoubleHashingTest",
    srcs = ["HashTableDoubleHashingTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.datastructures.hashtable.HashTableDoubleHashingTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/hashtable:HashTableLinearProbingTest
java_test(
    name = "HashTableLinearProbingTest",
    srcs = ["HashTableLinearProbingTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.datastructures.hashtable.HashTableLinearProbingTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/hashtable:HashTableQuadraticProbingTest
java_test(
    name = "HashTableQuadraticProbingTest",
    srcs = ["HashTableQuadraticProbingTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.datastructures.hashtable.HashTableQuadraticProbingTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/hashtable:HashTableSeparateChainingTest
java_test(
    name = "HashTableSeparateChainingTest",
    srcs = ["HashTableSeparateChainingTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.datastructures.hashtable.HashTableSeparateChainingTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# Run all tests
# bazel test //src/test/java/com/williamfiset/algorithms/datastructures/hashtable:all
