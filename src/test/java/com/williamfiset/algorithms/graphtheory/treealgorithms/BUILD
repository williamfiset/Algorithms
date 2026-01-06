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
    "//src/main/java/com/williamfiset/algorithms/graphtheory/treealgorithms:treealgorithms",
    "//src/main/java/com/williamfiset/algorithms/utils:utils",
    "//src/main/java/com/williamfiset/algorithms/utils/graphutils:graphutils",
    "@maven//:com_google_guava_guava",
    "@maven//:com_google_truth_truth",
    "@maven//:org_apache_commons_commons_lang3",
    "@maven//:junit_junit",
] + JUNIT5_DEPS

# Tree algorithm tests

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory/treealgorithms:LowestCommonAncestorEulerTourTest
java_test(
    name = "LowestCommonAncestorEulerTourTest",
    srcs = ["LowestCommonAncestorEulerTourTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.treealgorithms.LowestCommonAncestorEulerTourTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory/treealgorithms:LowestCommonAncestorTest
java_test(
    name = "LowestCommonAncestorTest",
    srcs = ["LowestCommonAncestorTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.treealgorithms.LowestCommonAncestorTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory/treealgorithms:RootingTreeTest
java_test(
    name = "RootingTreeTest",
    srcs = ["RootingTreeTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.treealgorithms.RootingTreeTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory/treealgorithms:TreeCenterLongestPathImplTest
java_test(
    name = "TreeCenterLongestPathImplTest",
    srcs = ["TreeCenterLongestPathImplTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.treealgorithms.TreeCenterLongestPathImplTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory/treealgorithms:TreeCenterTest
java_test(
    name = "TreeCenterTest",
    srcs = ["TreeCenterTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.treealgorithms.TreeCenterTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory/treealgorithms:TreeIsomorphismTest
java_test(
    name = "TreeIsomorphismTest",
    srcs = ["TreeIsomorphismTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.treealgorithms.TreeIsomorphismTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory/treealgorithms:TreeIsomorphismWithBfsTest
java_test(
    name = "TreeIsomorphismWithBfsTest",
    srcs = ["TreeIsomorphismWithBfsTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.treealgorithms.TreeIsomorphismWithBfsTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# Run all tests
# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory/treealgorithms:all
