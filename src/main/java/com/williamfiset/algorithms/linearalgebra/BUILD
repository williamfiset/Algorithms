load("@rules_java//java:defs.bzl", "java_binary", "java_library")

# Linear algebra algorithms library
java_library(
    name = "linearalgebra",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/linearalgebra:GaussianElimination
java_binary(
    name = "GaussianElimination",
    main_class = "com.williamfiset.algorithms.linearalgebra.GaussianElimination",
    runtime_deps = [":linearalgebra"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/linearalgebra:LinearRecurrenceSolver
java_binary(
    name = "LinearRecurrenceSolver",
    main_class = "com.williamfiset.algorithms.linearalgebra.LinearRecurrenceSolver",
    runtime_deps = [":linearalgebra"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/linearalgebra:MatrixDeterminantLaplaceExpansion
java_binary(
    name = "MatrixDeterminantLaplaceExpansion",
    main_class = "com.williamfiset.algorithms.linearalgebra.MatrixDeterminantLaplaceExpansion",
    runtime_deps = [":linearalgebra"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/linearalgebra:MatrixInverse
java_binary(
    name = "MatrixInverse",
    main_class = "com.williamfiset.algorithms.linearalgebra.MatrixInverse",
    runtime_deps = [":linearalgebra"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/linearalgebra:MatrixMultiplication
java_binary(
    name = "MatrixMultiplication",
    main_class = "com.williamfiset.algorithms.linearalgebra.MatrixMultiplication",
    runtime_deps = [":linearalgebra"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/linearalgebra:MatrixPower
java_binary(
    name = "MatrixPower",
    main_class = "com.williamfiset.algorithms.linearalgebra.MatrixPower",
    runtime_deps = [":linearalgebra"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/linearalgebra:ModularLinearAlgebra
java_binary(
    name = "ModularLinearAlgebra",
    main_class = "com.williamfiset.algorithms.linearalgebra.ModularLinearAlgebra",
    runtime_deps = [":linearalgebra"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/linearalgebra:RotateSquareMatrixInplace
java_binary(
    name = "RotateSquareMatrixInplace",
    main_class = "com.williamfiset.algorithms.linearalgebra.RotateSquareMatrixInplace",
    runtime_deps = [":linearalgebra"],
)
