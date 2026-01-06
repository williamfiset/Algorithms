load("@rules_java//java:defs.bzl", "java_library")

# Data structures library - organized by subdirectories

# Utils for data structures
java_library(
    name = "utils",
    srcs = glob(["utils/*.java"]),
    visibility = ["//visibility:public"],
)

# Balanced tree implementations
java_library(
    name = "balancedtree",
    srcs = glob(["balancedtree/*.java"]),
    visibility = ["//visibility:public"],
    deps = [":utils"],
)

# Binary search tree implementations
java_library(
    name = "binarysearchtree",
    srcs = glob(["binarysearchtree/*.java"]),
    visibility = ["//visibility:public"],
    deps = [":utils"],
)

# Bloom filter
java_library(
    name = "bloomfilter",
    srcs = glob(["bloomfilter/*.java"]),
    visibility = ["//visibility:public"],
)

# Dynamic array
java_library(
    name = "dynamicarray",
    srcs = glob(["dynamicarray/*.java"]),
    visibility = ["//visibility:public"],
)

# Fenwick tree (Binary Indexed Tree)
java_library(
    name = "fenwicktree",
    srcs = glob(["fenwicktree/*.java"]),
    visibility = ["//visibility:public"],
)

# Fibonacci heap
java_library(
    name = "fibonacciheap",
    srcs = glob(["fibonacciheap/*.java"]),
    visibility = ["//visibility:public"],
)

# Hash table implementations
java_library(
    name = "hashtable",
    srcs = glob(["hashtable/*.java"]),
    visibility = ["//visibility:public"],
)

# KD tree
java_library(
    name = "kdtree",
    srcs = glob(["kdtree/*.java"]),
    visibility = ["//visibility:public"],
)

# Linked list
java_library(
    name = "linkedlist",
    srcs = glob(["linkedlist/*.java"]),
    visibility = ["//visibility:public"],
)

# Priority queue implementations
java_library(
    name = "priorityqueue",
    srcs = glob(["priorityqueue/*.java"]),
    visibility = ["//visibility:public"],
)

# Quad tree
java_library(
    name = "quadtree",
    srcs = glob(["quadtree/*.java"]),
    visibility = ["//visibility:public"],
)

# Queue implementations
java_library(
    name = "queue",
    srcs = glob(["queue/*.java"]),
    visibility = ["//visibility:public"],
)

# Segment tree implementations
java_library(
    name = "segmenttree",
    srcs = glob(["segmenttree/*.java"]),
    visibility = ["//visibility:public"],
)

# Set implementations
java_library(
    name = "set",
    srcs = glob(["set/*.java"]),
    visibility = ["//visibility:public"],
)

# Skip list
java_library(
    name = "skiplist",
    srcs = glob(["skiplist/*.java"]),
    visibility = ["//visibility:public"],
)

# Sparse table
java_library(
    name = "sparsetable",
    srcs = glob(["sparsetable/*.java"]),
    visibility = ["//visibility:public"],
)

# Stack implementations
java_library(
    name = "stack",
    srcs = glob(["stack/*.java"]),
    visibility = ["//visibility:public"],
)

# Suffix array
java_library(
    name = "suffixarray",
    srcs = glob(["suffixarray/*.java"]),
    visibility = ["//visibility:public"],
    deps = ["@maven//:com_google_guava_guava"],
)

# Trie
java_library(
    name = "trie",
    srcs = glob(["trie/*.java"]),
    visibility = ["//visibility:public"],
)

# Union find (Disjoint set)
java_library(
    name = "unionfind",
    srcs = glob(["unionfind/*.java"]),
    visibility = ["//visibility:public"],
)
