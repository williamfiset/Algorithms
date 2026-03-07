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
    "//src/main/java/com/williamfiset/algorithms/sorting:sorting",
    "//src/main/java/com/williamfiset/algorithms/utils:utils",
    "@maven//:com_google_guava_guava",
    "@maven//:com_google_truth_truth",
] + JUNIT5_DEPS

java_test(
    name = "BubbleSortTest",
    srcs = ["BubbleSortTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.sorting.BubbleSortTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

java_test(
    name = "CountingSortTest",
    srcs = ["CountingSortTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.sorting.CountingSortTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

java_test(
    name = "HeapsortTest",
    srcs = ["HeapsortTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.sorting.HeapsortTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

java_test(
    name = "InsertionSortTest",
    srcs = ["InsertionSortTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.sorting.InsertionSortTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

java_test(
    name = "MergeSortTest",
    srcs = ["MergeSortTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.sorting.MergeSortTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

java_test(
    name = "QuickSelectTest",
    srcs = ["QuickSelectTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.sorting.QuickSelectTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

java_test(
    name = "RadixSortTest",
    srcs = ["RadixSortTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.sorting.RadixSortTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

java_test(
    name = "QuickSortTest",
    srcs = ["QuickSortTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.sorting.QuickSortTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

java_test(
    name = "QuickSort3Test",
    srcs = ["QuickSort3Test.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.sorting.QuickSort3Test"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

java_test(
    name = "SelectionSortTest",
    srcs = ["SelectionSortTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.sorting.SelectionSortTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

java_test(
    name = "BucketSortTest",
    srcs = ["BucketSortTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.sorting.BucketSortTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

java_test(
    name = "SortingTest",
    srcs = ["SortingTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.sorting.SortingTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)
