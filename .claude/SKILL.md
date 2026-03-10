---
name: algorithms-education
description: >
  Skills and conventions for an educational algorithms and data structures repository.
  Use this skill whenever working on algorithm implementations, data structure code,
  LeetCode-style problems, graph theory, dynamic programming, or any Java-based
  educational coding project. Trigger on mentions of: algorithms, data structures,
  graph theory, sorting, searching, trees, DP, BFS, DFS, linked lists, heaps,
  segment trees, union-find, or any request to write, refactor, document, or test
  educational code. Also trigger when the user asks to "clean up", "simplify",
  "document", "refactor" or "add tests" to algorithm code.
---

# Algorithms Education Skills

This skill defines the conventions and standards for an educational algorithms
repository. The goal is to make every algorithm implementation clear, well-tested,
and accessible to learners who may not have deep CS backgrounds.

---

## Skill 1: Code Documentation

**Goal:** Every file should teach, not just implement.

### Method-Level Documentation

Every public method gets a doc comment that explains:
1. **What** the method does (in plain English, one sentence)
2. **How** it works (brief description of the approach/algorithm)
3. **Parameters** — what each input represents
4. **Returns** — what the output means
5. **Time/Space complexity** — always include Big-O

```java
/**
 * Finds the shortest path from a source node to all other nodes
 * using Bellman-Ford's algorithm. Unlike Dijkstra's, this handles
 * negative edge weights and detects negative cycles.
 *
 * @param graph - adjacency list where graph[i] lists edges from node i
 * @param start - the source node index
 * @param n     - total number of nodes in the graph
 * @return dist array where dist[i] = shortest distance from start to i,
 *         or Double.NEGATIVE_INFINITY if node i is in a negative cycle
 *
 * Time:  O(V * E) — relaxes all edges V-1 times
 * Space: O(V)     — stores distance array
 */
```

### Inline Comments on Key Lines

Comment the **why**, not the **what**. Focus on lines where the logic isn't obvious:

```java
// Relax all edges V-1 times. After V-1 passes, shortest paths
// are guaranteed if no negative cycles exist.
for (int i = 0; i < n - 1; i++) {
  for (Edge e : edges) {
    if (dist[e.from] + e.cost < dist[e.to]) {
      dist[e.to] = dist[e.from] + e.cost;
    }
  }
}

// If we can still relax an edge after V-1 passes, that node
// is reachable from a negative cycle — mark it as -infinity.
for (int i = 0; i < n - 1; i++) {
  for (Edge e : edges) {
    if (dist[e.from] + e.cost < dist[e.to]) {
      dist[e.to] = Double.NEGATIVE_INFINITY;
    }
  }
}
```

### File-Level Header

Every file starts with a comment block explaining the algorithm in the file

```java
/**
 * Bellman-Ford Shortest Path Algorithm
 *
 * Computes single-source shortest paths in a weighted graph.
 * Handles negative edge weights and detects negative cycles.
 *
 * Use cases:
 *   - Graphs with negative weights (where Dijkstra fails)
 *   - Detecting negative cycles (e.g., currency arbitrage)
 *
 * Run with:
 *   bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:BellmanFordAdjacencyList
 *
 * @see <a href="https://en.wikipedia.org/wiki/Bellman-Ford_algorithm">Wikipedia</a>
 */
```

---

## Skill 2: Test Coverage

**Goal:** Every algorithm has tests that prove it works and teach edge cases.

### Test File Structure

Place tests alongside source files or in a `tests/` directory. Name test files
to mirror the source: `BellmanFord.java` → `BellmanFordTest.java`.

### What to Test

For every algorithm, cover these categories:

1. **Basic/Happy path** — typical input, expected output
2. **Edge cases** — empty input, single element, duplicates
3. **Boundary conditions** — max/min values, zero, Integer.MAX_VALUE
4. **Known tricky inputs** — cases that commonly break naive implementations
5. **Performance sanity check** — large input doesn't hang or crash (optional)

### Test Naming Convention

Use descriptive names that read like a sentence:

```java
@Test
public void testShortestPathSimpleGraph() { ... }

@Test
public void testDetectsNegativeCycle() { ... }

@Test
public void testSingleNodeGraph() { ... }

@Test
public void testDisconnectedNodes() { ... }
```

### Test Documentation

Each test method gets a brief comment explaining what scenario it covers and
why that scenario matters:

```java
/**
 * Graph with a negative cycle reachable from the source.
 * Bellman-Ford should mark affected nodes as NEGATIVE_INFINITY.
 *
 *   0 --5--> 1 --(-10)--> 2 --3--> 1
 *                (creates cycle 1→2→1 with net cost -7)
 */
@Test
public void testDetectsNegativeCycle() {
  // ... test body
}
```

### When Modifying Code, Update Tests

Every code change must be accompanied by:
- Running existing tests to check for regressions
- Adding new tests if new behavior is introduced
- Updating existing tests if method signatures or behavior changed
- Removing tests only if the feature they cover was deliberately removed

---

## Skill 3: Refactoring and Code Debt

**Goal:** Keep the codebase clean without losing educational value.

### When to Remove Code

Remove code that is:
- Exact duplicates of another implementation with no added educational value
- Dead code (unreachable, unused helper methods)
- Commented-out blocks with no explanation of why they exist
- Temporary debug/print statements

### When to Keep "Duplicate" Code

Keep alternative implementations when they teach different approaches:

```java
// ✓ KEEP — BFS and DFS solutions to the same problem teach different techniques
public int[] bfsSolve(int[][] grid) { ... }
public int[] dfsSolve(int[][] grid) { ... }

// ✓ KEEP — iterative vs recursive shows tradeoffs
public int fibRecursive(int n) { ... }
public int fibIterative(int n) { ... }

// ✗ REMOVE — identical logic, just different variable names
public int search_v1(int[] arr, int target) { ... }
public int search_v2(int[] arr, int target) { ... }
```

When keeping alternatives, clearly label them with a comment explaining the
educational purpose:

```java
/**
 * Recursive implementation of binary search.
 * Compare with binarySearchIterative() to see the iterative approach.
 * The iterative version avoids stack overhead for large arrays.
 */
```

### Debt Checklist

When refactoring, scan for:
- [ ] Unused imports
- [ ] Unused variables or parameters
- [ ] Methods that can be combined or simplified
- [ ] Magic numbers that should be named constants
- [ ] Inconsistent naming within the same file
- [ ] Copy-pasted blocks that should be extracted into a helper

---

## Skill 4: Code Formatting and Consistency

**Goal:** Uniform style across the entire repository.

### Naming Conventions

Use **short, clear** variable names. Prefer readability through simplicity:

```java
// ✓ GOOD — short and clear
int n = graph.length;
int[] dist = new int[n];
boolean[] vis = new boolean[n];
List<int[]> adj = new ArrayList<>();
Queue<Integer> q = new LinkedList<>();
int src = 0;
int dst = n - 1;

// ✗ BAD — verbose names that clutter algorithm logic
int numberOfNodesInGraph = graph.length;
int[] shortestDistanceFromSource = new int[numberOfNodesInGraph];
boolean[] hasNodeBeenVisited = new boolean[numberOfNodesInGraph];
List<int[]> adjacencyListRepresentation = new ArrayList<>();
Queue<Integer> breadthFirstSearchQueue = new LinkedList<>();
int sourceNodeIndex = 0;
int destinationNodeIndex = numberOfNodesInGraph - 1;
```

Common short names (use consistently across the repo):

| Name   | Meaning                       |
|--------|-------------------------------|
| `n`    | number of elements/nodes      |
| `m`    | number of edges               |
| `i, j` | loop indices                  |
| `from, to` | graph node endpoints      |
| `cost` | edge weight                   |
| `dist` | distance array                |
| `vis`  | visited array                 |
| `adj`  | adjacency list                |
| `q`    | queue                         |
| `pq`   | priority queue                |
| `st`   | stack                         |
| `dp`   | dynamic programming table     |
| `ans`  | result/answer                 |
| `lo`   | low pointer/bound             |
| `hi`   | high pointer/bound            |
| `mid`  | midpoint                      |
| `src`  | source node                   |
| `dst`  | destination node              |
| `cnt`  | counter                       |
| `sz`   | size                          |
| `cur`  | current element               |
| `prev` | previous element              |
| `next` | next element (use `nxt` if shadowing keyword) |

### Formatting Rules

- Braces: opening brace on the same line (`if (...) {`)
- Indentation: 2 spaces (no tabs)
- Blank lines: one blank line between methods, none inside short methods
- Max line length: 100 characters (soft limit)
- Imports: group by package, alphabetize within groups, no wildcard imports

### Big-O Notation Convention

Always use explicit multiplication and parentheses in Big-O expressions for clarity:

```java
// ✓ GOOD — explicit and unambiguous
// Time:  O(n*log(n))
// Time:  O(n*log^2(n))
// Time:  O(n^2*log(n))

// ✗ BAD — missing multiplication and parentheses
// Time:  O(n log n)
// Time:  O(n log^2 n)
// Time:  O(n^2 log n)

// Simple expressions without multiplication are fine as-is
// Time:  O(n)
// Time:  O(n^2)
// Time:  O(log(n))
// Space: O(n)
```

### Avoid Java Streams

Streams hurt readability for learners. Use plain loops instead:

```java
// ✗ AVOID — streams obscure the logic for beginners
int sum = Arrays.stream(arr).filter(x -> x > 0).reduce(0, Integer::sum);

// ✓ PREFER — a loop is immediately readable
int sum = 0;
for (int x : arr) {
  if (x > 0) sum += x;
}
```

---

## Skill 5: Simplification

**Goal:** The simplest correct code teaches the best.

### Simplification Strategies

1. **Reduce nesting** — invert conditions, return early

```java
// ✗ AVOID — deep nesting
if (node != null) {
  if (node.left != null) {
    if (node.left.val == target) {
      return true;
    }
  }
}
return false;

// ✓ PREFER — early returns keep code flat
if (node == null) return false;
if (node.left == null) return false;
return node.left.val == target;
```

2. **Extract repeated logic** — but only if it genuinely reduces complexity

3. **Use standard library where it clarifies** — `Arrays.sort()`, `Collections.swap()`,
   `Math.min()`, etc. are fine because learners need to know these exist

4. **Remove unnecessary wrappers** — don't wrap a single method call in another method

5. **Prefer arrays over complex data structures** when the problem allows it —
   `int[]` is clearer than `ArrayList<Integer>` when the size is known

### What NOT to Simplify

- Don't merge two clearly distinct algorithm phases into one loop just to save lines
- Don't replace clear if/else chains with ternary operators if it reduces readability
- Don't remove intermediate variables that give a name to a complex expression

---

## Skill 6: Bug Detection

**Goal:** Catch bugs proactively whenever touching code.

### Bug Scan Checklist

When modifying any lines of code, actively check for and report:

- [ ] **Off-by-one errors** — loop bounds, array indices, fence-post problems
- [ ] **Integer overflow** — multiplication or addition that could exceed int range
- [ ] **Null/empty checks** — missing guards for null arrays, empty collections
- [ ] **Uninitialized values** — using variables before assignment (especially in dp arrays)
- [ ] **Wrong comparison** — `==` vs `<=`, `<` vs `<=` in loop conditions
- [ ] **Infinite loops** — conditions that never become false, missing increments
- [ ] **Array out of bounds** — indexing with `i+1`, `i-1` without range checks
- [ ] **Graph issues** — missing visited check (infinite loop in cycles), wrong direction in directed graph
- [ ] **Incorrect base cases** — dp[0], recursion base case, empty graph
- [ ] **Mutation bugs** — modifying input that caller expects unchanged
- [ ] **Copy vs reference** — shallow copy when deep copy needed
- [ ] **Return value misuse** — ignoring return value, returning wrong variable

### How to Report Bugs

When a bug is found, report it clearly:

```
🐛 BUG FOUND in BellmanFord.java line 42:
   Loop runs `i < n` but should be `i < n - 1`.
   The extra iteration incorrectly marks reachable nodes as
   being in a negative cycle.
   FIX: Change `i < n` to `i < n - 1`
```

---

## Skill 7: Algorithm Explanation Comments

**Goal:** Help learners understand the *why* behind each algorithm.

---

## Skill 8: Place main method at the bottom

**Goal:** The main java method should be near the bottom of the Java file for consistency throughout the project
