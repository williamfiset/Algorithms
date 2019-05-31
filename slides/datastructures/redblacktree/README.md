
## Red-Black Tree

The Red-Black Tree is a form of self balancing binary search tree. This type of tree will adjust itself in order to maintain a low (logarithmic) height allowing for faster insertions and deletions.

### Red-Black Tree example

```java
RedBlackTree<Integer> tree = new RedBlackTree<>();

int numElements = 15;
for (int i = 0; i < numElements; i++) {
  int randNumber = (int)(Math.random() * 100);
  tree.insert(randNumber);
}

// Remove element 5 if it exists in the tree
tree.remove(5);

// Check if element 5 is inside the tree
System.out.println("Tree contains element 5: " + tree.contains(5));
```


