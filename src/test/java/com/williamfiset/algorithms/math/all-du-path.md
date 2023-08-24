# Test Report for All-DU Path
Source file:
> https://github.com/williamfiset/Algorithms/blob/master/src/main/java/com/williamfiset/algorithms/math/CompressedPrimeSieve.java

Function for testing: primeSieve()
This function do:
> Returns an array of longs with each bit indicating whether a number is prime or not. Use the isNotSet and setBit methods to toggle to bits for each number.

Function code:
```
  public static long[] primeSieve(int limit) {
    final int numChunks = (int) Math.ceil(limit / NUM_BITS);
    final int sqrtLimit = (int) Math.sqrt(limit);
    long[] chunks = new long[numChunks];
    chunks[0] = 1; // 1 as not prime
    for (int i = 3; i <= sqrtLimit; i += 2)
      if (isNotSet(chunks, i))
        for (int j = i * i; j <= limit; j += i)
          if (isNotSet(chunks, j)) {
            setBit(chunks, j);
          }
    return chunks;
  }
```
![alt text](https://drive.google.com/uc?id=12iKHCBtJjex9ZX0XL69J-nCZTHkXsq1y)

## Graphs

### Flow chart
![alt text](https://drive.google.com/uc?id=1DjkmdT-PoequO10YDOr8me2rv2Fyml_N)

### Data flow chart
![alt text](https://drive.google.com/uc?id=1bEj7ITLlfcHAsz3gF_HUin3HIlbKLEnh)

### All DU path coverage testcase

| Path        | Test case  | Expected output             | Actual output               | Result |
|-------------|------------|-----------------------------|-----------------------------|--------|
| 1->2-5      | limit = 10 | numChunks = 0 sqrtLimit = 3 | numChunks = 0 sqrtLimit = 3 | Pass   |
| 1->2-5 -> 8 | limit = 10 | numChunks = 0 sqrtLimit = 3 | numChunks = 0 sqrtLimit = 3 | Pass   |
