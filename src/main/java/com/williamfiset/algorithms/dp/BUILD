load("@rules_java//java:defs.bzl", "java_binary", "java_library")

# Dynamic Programming algorithms library
java_library(
    name = "dp",
    srcs = glob(
        ["*.java"],
        exclude = ["examples/**"],
    ),
    visibility = ["//visibility:public"],
)

# Dynamic Programming examples library
java_library(
    name = "dp_examples",
    srcs = glob(["examples/**/*.java"]),
    visibility = ["//visibility:public"],
    deps = [":dp"],
)

# Binary targets for runnable examples

# bazel run //src/main/java/com/williamfiset/algorithms/dp:CoinChange
java_binary(
    name = "CoinChange",
    main_class = "com.williamfiset.algorithms.dp.CoinChange",
    runtime_deps = [":dp"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/dp:EditDistanceIterative
java_binary(
    name = "EditDistanceIterative",
    main_class = "com.williamfiset.algorithms.dp.EditDistanceIterative",
    runtime_deps = [":dp"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/dp:EditDistanceRecursive
java_binary(
    name = "EditDistanceRecursive",
    main_class = "com.williamfiset.algorithms.dp.EditDistanceRecursive",
    runtime_deps = [":dp"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/dp:JosephusProblem
java_binary(
    name = "JosephusProblem",
    main_class = "com.williamfiset.algorithms.dp.JosephusProblem",
    runtime_deps = [":dp"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/dp:Knapsack_01
java_binary(
    name = "Knapsack_01",
    main_class = "com.williamfiset.algorithms.dp.Knapsack_01",
    runtime_deps = [":dp"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/dp:KnapsackUnbounded
java_binary(
    name = "KnapsackUnbounded",
    main_class = "com.williamfiset.algorithms.dp.KnapsackUnbounded",
    runtime_deps = [":dp"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/dp:LongestCommonSubsequence
java_binary(
    name = "LongestCommonSubsequence",
    main_class = "com.williamfiset.algorithms.dp.LongestCommonSubsequence",
    runtime_deps = [":dp"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/dp:LongestCommonSubstring
java_binary(
    name = "LongestCommonSubstring",
    main_class = "com.williamfiset.algorithms.dp.LongestCommonSubstring",
    runtime_deps = [":dp"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/dp:LongestIncreasingSubsequence
java_binary(
    name = "LongestIncreasingSubsequence",
    main_class = "com.williamfiset.algorithms.dp.LongestIncreasingSubsequence",
    runtime_deps = [":dp"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/dp:LongestPalindromeSubsequence
java_binary(
    name = "LongestPalindromeSubsequence",
    main_class = "com.williamfiset.algorithms.dp.LongestPalindromeSubsequence",
    runtime_deps = [":dp"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/dp:MaximumSubarray
java_binary(
    name = "MaximumSubarray",
    main_class = "com.williamfiset.algorithms.dp.MaximumSubarray",
    runtime_deps = [":dp"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/dp:MinimumWeightPerfectMatching
java_binary(
    name = "MinimumWeightPerfectMatching",
    main_class = "com.williamfiset.algorithms.dp.MinimumWeightPerfectMatching",
    runtime_deps = [":dp"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/dp:WeightedMaximumCardinalityMatchingIterative
java_binary(
    name = "WeightedMaximumCardinalityMatchingIterative",
    main_class = "com.williamfiset.algorithms.dp.WeightedMaximumCardinalityMatchingIterative",
    runtime_deps = [":dp"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/dp:WeightedMaximumCardinalityMatchingRecursive
java_binary(
    name = "WeightedMaximumCardinalityMatchingRecursive",
    main_class = "com.williamfiset.algorithms.dp.WeightedMaximumCardinalityMatchingRecursive",
    runtime_deps = [":dp"],
)

#########################
# Examples binary targets
#########################

# bazel run //src/main/java/com/williamfiset/algorithms/dp:BoardTilingsSolver
java_binary(
    name = "BoardTilingsSolver",
    main_class = "com.williamfiset.algorithms.dp.examples.boardtilings.BoardTilingsSolver",
    runtime_deps = [":dp_examples"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/dp:DominoAndTrominoTiling
java_binary(
    name = "DominoAndTrominoTiling",
    main_class = "com.williamfiset.algorithms.dp.examples.dominoandtrominotiling.Solution",
    runtime_deps = [":dp_examples"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/dp:EditDistance
java_binary(
    name = "EditDistance",
    main_class = "com.williamfiset.algorithms.dp.examples.editdistance.EditDistance",
    runtime_deps = [":dp_examples"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/dp:HouseRobber
java_binary(
    name = "HouseRobber",
    main_class = "com.williamfiset.algorithms.dp.examples.houserobber.HouseRobber",
    runtime_deps = [":dp_examples"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/dp:MagicalCows
java_binary(
    name = "MagicalCows",
    main_class = "com.williamfiset.algorithms.dp.examples.magicalcows.MagicalCows",
    runtime_deps = [":dp_examples"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/dp:NarrowArtGalleryRecursive
java_binary(
    name = "NarrowArtGalleryRecursive",
    main_class = "com.williamfiset.algorithms.dp.examples.narrowartgallery.NarrowArtGalleryRecursive",
    runtime_deps = [":dp_examples"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/dp:Scenes
java_binary(
    name = "Scenes",
    main_class = "com.williamfiset.algorithms.dp.examples.scenes.Scenes",
    runtime_deps = [":dp_examples"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/dp:TilingDominoes
java_binary(
    name = "TilingDominoes",
    main_class = "com.williamfiset.algorithms.dp.examples.tilingdominoes.TilingDominoes",
    runtime_deps = [":dp_examples"],
)
