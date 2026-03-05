# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build System

This project uses **Bazel** as its build system (requires JDK 8+). Dependencies are managed via Maven through `rules_jvm_external` and declared in `MODULE.bazel`.

## Commands

**Run all tests:**
```bash
bazel test //src/test/...
```

**Run tests for a specific package:**
```bash
bazel test //src/test/java/com/williamfiset/algorithms/graphtheory:all
bazel test //src/test/java/com/williamfiset/algorithms/sorting:all
```

**Run a single test class:**
```bash
bazel test //src/test/java/com/williamfiset/algorithms/graphtheory:BoruvkasTest
```

**Run a specific algorithm (with `main` method):**
```bash
bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:BinarySearch
bazel run //src/main/java/com/williamfiset/algorithms/search:BinarySearch
```

**Alternative (without Bazel):**
```bash
mkdir classes
javac -sourcepath src/main/java -d classes src/main/java/com/williamfiset/algorithms/search/BinarySearch.java
java -cp classes com.williamfiset.algorithms.search.BinarySearch
```

## Project Structure

```
src/
  main/java/com/williamfiset/algorithms/
    datastructures/   # Data structure implementations
    dp/               # Dynamic programming algorithms + examples/
    geometry/         # Computational geometry
    graphtheory/      # Graph algorithms
      networkflow/    # Max flow, min cost flow, bipartite matching
      treealgorithms/ # Tree-specific algorithms (LCA, rooting, isomorphism)
    linearalgebra/    # Matrix operations
    math/             # Number theory, primes, FFT
    other/            # Miscellaneous (bit manipulation, permutations, etc.)
    search/           # Search algorithms
    sorting/          # Sorting algorithms
    strings/          # String algorithms
    utils/            # Shared utilities
      graphutils/     # Graph construction helpers

  test/java/com/williamfiset/algorithms/
    <mirrors main structure>
```

## Architecture Patterns

**BUILD files:** Every package has a Bazel `BUILD` file. The main source `BUILD` files define a `java_library` target (named after the package, e.g., `graphtheory`) plus individual `java_binary` targets per class. Test `BUILD` files define `java_test` targets per test class using JUnit 5 via `ConsoleLauncher`.

**Test framework:** Tests use JUnit 5 (Jupiter) with some legacy JUnit 4. New tests should use JUnit 5.

**Graph representation:** Most graph algorithms accept adjacency lists built with `List<List<Edge>>` or similar structures. Many solvers are implemented as classes where you construct the solver, add edges, then call a `solve()` method.

**Network flow base:** Flow algorithms in `networkflow/` share a common abstract solver pattern — `NetworkFlowSolverBase` is extended by concrete implementations (Dinic's, Ford-Fulkerson, etc.).

**When adding a new algorithm:**
1. Add the `.java` file in the appropriate `src/main/java/...` package
2. Add a `java_binary` entry in that package's `BUILD` file
3. Add a test file in the corresponding `src/test/java/...` package
4. Add a `java_test` entry in the test `BUILD` file
5. Update `README.md` with the new algorithm entry
