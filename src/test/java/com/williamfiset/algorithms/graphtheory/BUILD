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
    "//src/main/java/com/williamfiset/algorithms/graphtheory:networkflow",
    "//src/main/java/com/williamfiset/algorithms/graphtheory:treealgorithms",
    "//src/main/java/com/williamfiset/algorithms/utils:utils",
    "//src/main/java/com/williamfiset/algorithms/utils/graphutils:graphutils",
    "@maven//:com_google_guava_guava",
    "@maven//:com_google_truth_truth",
    "@maven//:org_apache_commons_commons_lang3",
    "@maven//:junit_junit",
] + JUNIT5_DEPS

# Core graphtheory tests

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory:ArticulationPointsAdjacencyListTest
java_test(
    name = "ArticulationPointsAdjacencyListTest",
    srcs = ["ArticulationPointsAdjacencyListTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.ArticulationPointsAdjacencyListTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory:BreadthFirstSearchAdjacencyListIterativeTest
java_test(
    name = "BreadthFirstSearchAdjacencyListIterativeTest",
    srcs = ["BreadthFirstSearchAdjacencyListIterativeTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.BreadthFirstSearchAdjacencyListIterativeTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory:BridgesAdjacencyListIterativeTest
java_test(
    name = "BridgesAdjacencyListIterativeTest",
    srcs = ["BridgesAdjacencyListIterativeTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.BridgesAdjacencyListIterativeTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory:BridgesAdjacencyListTest
java_test(
    name = "BridgesAdjacencyListTest",
    srcs = ["BridgesAdjacencyListTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.BridgesAdjacencyListTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory:EulerianPathDirectedEdgesAdjacencyListTest
java_test(
    name = "EulerianPathDirectedEdgesAdjacencyListTest",
    srcs = ["EulerianPathDirectedEdgesAdjacencyListTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.EulerianPathDirectedEdgesAdjacencyListTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory:FloydWarshallSolverTest
java_test(
    name = "FloydWarshallSolverTest",
    srcs = ["FloydWarshallSolverTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.FloydWarshallSolverTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory:KahnsTest
java_test(
    name = "KahnsTest",
    srcs = ["KahnsTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.KahnsTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory:KosarajuTest
java_test(
    name = "KosarajuTest",
    srcs = ["KosarajuTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.KosarajuTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory:SteinerTreeTest
java_test(
    name = "SteinerTreeTest",
    srcs = ["SteinerTreeTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.SteinerTreeTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory:TarjanSccSolverAdjacencyListTest
java_test(
    name = "TarjanSccSolverAdjacencyListTest",
    srcs = ["TarjanSccSolverAdjacencyListTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.TarjanSccSolverAdjacencyListTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory:TravelingSalesmanProblemTest
java_test(
    name = "TravelingSalesmanProblemTest",
    srcs = ["TravelingSalesmanProblemTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.TravelingSalesmanProblemTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory:TwoSatSolverAdjacencyListTest
java_test(
    name = "TwoSatSolverAdjacencyListTest",
    srcs = ["TwoSatSolverAdjacencyListTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.TwoSatSolverAdjacencyListTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# Network flow tests

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory:BipartiteGraphCheckAdjacencyListTest
java_test(
    name = "BipartiteGraphCheckAdjacencyListTest",
    srcs = ["networkflow/BipartiteGraphCheckAdjacencyListTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.networkflow.BipartiteGraphCheckAdjacencyListTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# Tree algorithm tests

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory:LowestCommonAncestorEulerTourTest
java_test(
    name = "LowestCommonAncestorEulerTourTest",
    srcs = ["treealgorithms/LowestCommonAncestorEulerTourTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.treealgorithms.LowestCommonAncestorEulerTourTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory:LowestCommonAncestorTest
java_test(
    name = "LowestCommonAncestorTest",
    srcs = ["treealgorithms/LowestCommonAncestorTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.treealgorithms.LowestCommonAncestorTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory:RootingTreeTest
java_test(
    name = "RootingTreeTest",
    srcs = ["treealgorithms/RootingTreeTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.treealgorithms.RootingTreeTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory:TreeCenterLongestPathImplTest
java_test(
    name = "TreeCenterLongestPathImplTest",
    srcs = ["treealgorithms/TreeCenterLongestPathImplTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.treealgorithms.TreeCenterLongestPathImplTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory:TreeCenterTest
java_test(
    name = "TreeCenterTest",
    srcs = ["treealgorithms/TreeCenterTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.treealgorithms.TreeCenterTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory:TreeIsomorphismTest
java_test(
    name = "TreeIsomorphismTest",
    srcs = ["treealgorithms/TreeIsomorphismTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.treealgorithms.TreeIsomorphismTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory:TreeIsomorphismWithBfsTest
java_test(
    name = "TreeIsomorphismWithBfsTest",
    srcs = ["treealgorithms/TreeIsomorphismWithBfsTest.java"],
    main_class = "org.junit.platform.console.ConsoleLauncher",
    use_testrunner = False,
    args = ["--select-class=com.williamfiset.algorithms.graphtheory.treealgorithms.TreeIsomorphismWithBfsTest"],
    runtime_deps = JUNIT5_RUNTIME_DEPS,
    deps = TEST_DEPS,
)

# Run all tests
# bazel test //src/test/java/com/williamfiset/algorithms/graphtheory:all
