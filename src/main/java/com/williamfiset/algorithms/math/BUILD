load("@rules_java//java:defs.bzl", "java_binary", "java_library")

# Math algorithms library
java_library(
    name = "math",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/math:CompressedPrimeSieve
java_binary(
    name = "CompressedPrimeSieve",
    main_class = "com.williamfiset.algorithms.math.CompressedPrimeSieve",
    runtime_deps = [":math"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/math:EulerTotientFunction
java_binary(
    name = "EulerTotientFunction",
    main_class = "com.williamfiset.algorithms.math.EulerTotientFunction",
    runtime_deps = [":math"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/math:EulerTotientFunctionWithSieve
java_binary(
    name = "EulerTotientFunctionWithSieve",
    main_class = "com.williamfiset.algorithms.math.EulerTotientFunctionWithSieve",
    runtime_deps = [":math"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/math:FastFourierTransform
java_binary(
    name = "FastFourierTransform",
    main_class = "com.williamfiset.algorithms.math.FastFourierTransform",
    runtime_deps = [":math"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/math:GCD
java_binary(
    name = "GCD",
    main_class = "com.williamfiset.algorithms.math.GCD",
    runtime_deps = [":math"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/math:IsPrime
java_binary(
    name = "IsPrime",
    main_class = "com.williamfiset.algorithms.math.IsPrime",
    runtime_deps = [":math"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/math:LCM
java_binary(
    name = "LCM",
    main_class = "com.williamfiset.algorithms.math.LCM",
    runtime_deps = [":math"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/math:ModPow
java_binary(
    name = "ModPow",
    main_class = "com.williamfiset.algorithms.math.ModPow",
    runtime_deps = [":math"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/math:ModularInverse
java_binary(
    name = "ModularInverse",
    main_class = "com.williamfiset.algorithms.math.ModularInverse",
    runtime_deps = [":math"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/math:NChooseRModPrime
java_binary(
    name = "NChooseRModPrime",
    main_class = "com.williamfiset.algorithms.math.NChooseRModPrime",
    runtime_deps = [":math"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/math:PrimeFactorization
java_binary(
    name = "PrimeFactorization",
    main_class = "com.williamfiset.algorithms.math.PrimeFactorization",
    runtime_deps = [":math"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/math:RelativelyPrime
java_binary(
    name = "RelativelyPrime",
    main_class = "com.williamfiset.algorithms.math.RelativelyPrime",
    runtime_deps = [":math"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/math:SieveOfEratosthenes
java_binary(
    name = "SieveOfEratosthenes",
    main_class = "com.williamfiset.algorithms.math.SieveOfEratosthenes",
    runtime_deps = [":math"],
)
