## Work done in mt_branch1:

The `ArrayStack` class contains the `push` function [here](src/main/java/com/williamfiset/algorithms/datastructures/stack/ArrayStack.java). <br>
The `StackTest` class contains the test for push, called: `testPush` [here](src/test/java/com/williamfiset/algorithms/datastructures/stack/StackTest.java).
<br><br>
In the image below, we’re checking coverage for testPush through the intellij coverage tester. <br><br>
![no alt](readMeImages/branch1img1.png)

We can see initially it's 2/3rds covered. as size == capacity is not covered (this can be seen with the red and green lines)
<br><br>
In the image below, we’re checking coverage with our own tool, and I have the same output 2/3rds.
![no alt](readMeImages/branch1img2.png)
<br> <br>
In the image below, we altered the testPush function to improve it. We do this by checking whether the  size == capacity which wasn’t checked before. This increases coverage to 3/3 (100%)<br>
![no alt](readMeImages/branch1img3.png)
<br><br>
In the image below, we can see that the built-in tool also states 100% coverage
![no alt](readMeImages/branch1img4.png)

## Work done in mt_branch2:

The `SteinerTree` class contains the `floydWarshall` function [here](src/main/java/com/williamfiset/algorithms/graphtheory/SteinerTree.java). <br>
The `SteinerTreeTest` class contains the tests for the Steiner Trees [here](src/test/java/com/williamfiset/algorithms/datastructures/stack/StackTest.java).
<br><br>
Within the floydWarshall function we need to check whether the steiner tree has a negative cycle, since negative cycles can invalidate any distance calculation.
<br><br>
as seen in the image below, when tested with intellij coverage testing, the entire function is covered, except for the check for the negative cycle.
<br>
![no alt](readMeImages/branch2img1.png)
<br>
In the image below, we’re checking coverage with our own tool
<br>