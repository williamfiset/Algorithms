load("@rules_java//java:defs.bzl", "java_binary", "java_library")

# String algorithms library
java_library(
    name = "strings",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/strings:BoothsAlgorithm
java_binary(
    name = "BoothsAlgorithm",
    main_class = "com.williamfiset.algorithms.strings.BoothsAlgorithm",
    runtime_deps = [":strings"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/strings:BoyerMooreStringSearch
java_binary(
    name = "BoyerMooreStringSearch",
    main_class = "com.williamfiset.algorithms.strings.BoyerMooreStringSearch",
    runtime_deps = [":strings"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/strings:KMP
java_binary(
    name = "KMP",
    main_class = "com.williamfiset.algorithms.strings.KMP",
    runtime_deps = [":strings"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/strings:LongestCommonPrefixArray
java_binary(
    name = "LongestCommonPrefixArray",
    main_class = "com.williamfiset.algorithms.strings.LongestCommonPrefixArray",
    runtime_deps = [":strings"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/strings:LongestCommonSubstring
java_binary(
    name = "LongestCommonSubstring",
    main_class = "com.williamfiset.algorithms.strings.LongestCommonSubstring",
    runtime_deps = [":strings"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/strings:LongestRepeatedSubstring
java_binary(
    name = "LongestRepeatedSubstring",
    main_class = "com.williamfiset.algorithms.strings.LongestRepeatedSubstring",
    runtime_deps = [":strings"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/strings:ManachersAlgorithm
java_binary(
    name = "ManachersAlgorithm",
    main_class = "com.williamfiset.algorithms.strings.ManachersAlgorithm",
    runtime_deps = [":strings"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/strings:RabinKarp
java_binary(
    name = "RabinKarp",
    main_class = "com.williamfiset.algorithms.strings.RabinKarp",
    runtime_deps = [":strings"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/strings:SubstringVerificationSuffixArray
java_binary(
    name = "SubstringVerificationSuffixArray",
    main_class = "com.williamfiset.algorithms.strings.SubstringVerificationSuffixArray",
    runtime_deps = [":strings"],
)
