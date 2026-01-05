load("@rules_java//java:defs.bzl", "java_binary", "java_library")

# Geometry algorithms library
java_library(
    name = "geometry",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
)

# Binary targets for runnable examples

# bazel run //src/main/java/com/williamfiset/algorithms/geometry:AngleBetweenVectors2D
java_binary(
    name = "AngleBetweenVectors2D",
    main_class = "com.williamfiset.algorithms.geometry.AngleBetweenVectors2D",
    runtime_deps = [":geometry"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/geometry:AngleBetweenVectors3D
java_binary(
    name = "AngleBetweenVectors3D",
    main_class = "com.williamfiset.algorithms.geometry.AngleBetweenVectors3D",
    runtime_deps = [":geometry"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/geometry:CircularSegmentArea
java_binary(
    name = "CircularSegmentArea",
    main_class = "com.williamfiset.algorithms.geometry.CircularSegmentArea",
    runtime_deps = [":geometry"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/geometry:CollinearPoints
java_binary(
    name = "CollinearPoints",
    main_class = "com.williamfiset.algorithms.geometry.CollinearPoints",
    runtime_deps = [":geometry"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/geometry:ConvexHullGrahamScan
java_binary(
    name = "ConvexHullGrahamScan",
    main_class = "com.williamfiset.algorithms.geometry.ConvexHullGrahamScan",
    runtime_deps = [":geometry"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/geometry:ConvexPolygonArea
java_binary(
    name = "ConvexPolygonArea",
    main_class = "com.williamfiset.algorithms.geometry.ConvexPolygonArea",
    runtime_deps = [":geometry"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/geometry:ConvexPolygonCutWithLineSegment
java_binary(
    name = "ConvexPolygonCutWithLineSegment",
    main_class = "com.williamfiset.algorithms.geometry.ConvexPolygonCutWithLineSegment",
    runtime_deps = [":geometry"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/geometry:CoplanarPoints
java_binary(
    name = "CoplanarPoints",
    main_class = "com.williamfiset.algorithms.geometry.CoplanarPoints",
    runtime_deps = [":geometry"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/geometry:LineCircleIntersection
java_binary(
    name = "LineCircleIntersection",
    main_class = "com.williamfiset.algorithms.geometry.LineCircleIntersection",
    runtime_deps = [":geometry"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/geometry:LineSegmentLineSegmentIntersection
java_binary(
    name = "LineSegmentLineSegmentIntersection",
    main_class = "com.williamfiset.algorithms.geometry.LineSegmentLineSegmentIntersection",
    runtime_deps = [":geometry"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/geometry:LineSegmentToGeneralForm
java_binary(
    name = "LineSegmentToGeneralForm",
    main_class = "com.williamfiset.algorithms.geometry.LineSegmentToGeneralForm",
    runtime_deps = [":geometry"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/geometry:PointCircleTangent
java_binary(
    name = "PointCircleTangent",
    main_class = "com.williamfiset.algorithms.geometry.PointCircleTangent",
    runtime_deps = [":geometry"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/geometry:PointInsideTriangle
java_binary(
    name = "PointInsideTriangle",
    main_class = "com.williamfiset.algorithms.geometry.PointInsideTriangle",
    runtime_deps = [":geometry"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/geometry:PointRotation
java_binary(
    name = "PointRotation",
    main_class = "com.williamfiset.algorithms.geometry.PointRotation",
    runtime_deps = [":geometry"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/geometry:TriangleArea
java_binary(
    name = "TriangleArea",
    main_class = "com.williamfiset.algorithms.geometry.TriangleArea",
    runtime_deps = [":geometry"],
)
