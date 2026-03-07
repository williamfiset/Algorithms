load("@rules_java//java:defs.bzl", "java_test")

JUNIT5_DEPS = [
    "@maven//:org_junit_jupiter_junit_jupiter_api",
    "@maven//:org_junit_jupiter_junit_jupiter_engine",
]

JUNIT5_RUNTIME_DEPS = [
    "@maven//:org_junit_platform_junit_platform_console",
]

java_test(
    name = "IntArrayTest",
    srcs = ["IntArrayTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.datastructures.dynamicarray.IntArrayTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = [
        "//src/main/java/com/williamfiset/algorithms/datastructures/dynamicarray:dynamicarray",
        "@maven//:com_google_guava_guava",
        "@maven//:com_google_truth_truth",
    ] + JUNIT5_DEPS,
)
