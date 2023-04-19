
## Fenwick Tree

The Fenwick Tree (FT), also called the Binary Indexed Tree (BIT) is an efficient data structure for performing range/point queries/updates. We currently have two flavors of Fenwick trees which support summation queries/updates. In general, you can modify FTs to support [any invertible function](https://www.quora.com/What-are-the-advantage-of-binary-indexed-tree-BIT-or-fenwick-tree-over-segment-tree) not just summation. More specific operations such as min/max queries can be done with a FT but require you to maintain additional information.

### Fenwick Tree - Range updates and point queries

```java

// The values array must be one based
long[] values = {0,+1,-2,+3,-4,+5,-6};
//               ^ first element does not get used
  
FenwickTreeRangeUpdatePointQuery ft = new FenwickTreeRangeUpdatePointQuery(values);

ft.updateRange(1, 4, 10); // Add +10 to interval [1, 4] in O(log(n))
ft.get(1); // 11
ft.get(4); // 6
ft.get(5); // 5

ft.updateRange(3, 6, -20); // Add -20 to interval [3, 6] in O(log(n))
ft.get(3); // -7
ft.get(4); // -14
ft.get(5); // -15
```

### Fenwick Tree - Range queries and point updates

```java

// The values array must be one based
long[] values = {0,1,2,2,4};
//               ^ first element does not get used
  
FenwickTreeRangeQueryPointUpdate ft = new FenwickTreeRangeQueryPointUpdate(values);

ft.sum(1, 4); // 9, sum all numbers in interval [1, 4] in O(log(n))
ft.add(3, 1); // Adds +1 to index 3.

ft.sum(1, 4); // 10, sum all numbers in interval [1, 4]
ft.set(4, 0); // Set index 4  to have value zero.

ft.sum(1, 4); // 6, sum all numbers in interval [1, 4]
ft.get(2);    // 2, Get the value at index 2, this is the same as .sum(2, 2)
```
