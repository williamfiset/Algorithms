package btrees;

/**
 *
 * 
 */
public class Btrees {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        BTree a = new BTree();
        Node h = new Node();
        a.insert(1, h);
        a.insert(2, h);
        a.insert(3, h);
        a.insert(4, h);
        a.insert(5, h);
        a.insert(16, h);
        a.insert(17, h);
        a.insert(18, h);
        a.insert(19, h);
        a.insert(6, h);
        a.insert(15, h);
        a.insert(24, h);
        a.insert(25, h);
        a.insert(20, h);
        a.insert(7, h);
        a.insert(14, h);
        a.insert(23, h);
        a.insert(22, h);
        a.insert(21, h);
        a.insert(8, h);
        a.insert(13, h);
        a.insert(12, h);
        a.insert(11, h);
        a.insert(10, h);
        a.insert(9, h);

//        for(int i = 0; i < 10000; i++){
//            a.insert(i*i, h);
//        }
//        final long startTime = System.nanoTime();
        System.out.println(a.search(h, 4));
//        final long duration = System.nanoTime() - startTime;
//        System.out.println(duration);
////        System.out.println(h.getNumberofPointers());
        System.out.println(a.heightTree);
    }
}

class BTree {

    int heightTree = 1;

    BTree() {
    }

    void insert(int key, Node n) {
        //if the node is a leaf and is empty, then add the key        
        if (n.getNumberofPointers() == 0) {
            //else if it is a leaf and not empty, then split the node and add the key to appropriate new node 
            if (n.isEmpty()) {
                n.setKey(key);
            } else {
                //this statement only applies if the entire tree is only one node. then only that node will be splitted.
                if (heightTree == 1) {
                    split1(n);
                    insert(key, n);
                    //this split will happen if the node is a part of a tree and has a parent.
                } else {
                    split2(n);
                    insert(key, n.parent);
                }
            }
            // if the node is not a leaf
        } else {
            //run a loop to find the correct node child to go to.
            for (int i = 0; i < n.getNumberOfKeys(); i++) {
                // if the key is smaller than key[i], then go to the corresponding node child
                if (key <= n.keys[i]) {
                    // recall itself
                    insert(key, n.next[i]);
                    return;
                }
            }
            // if the key is bigger than the exisiting keys in the parent, then, go to the last pointer.
            insert(key, n.next[n.getNumberOfKeys()]);

        }
    }

    //this method is only when the height of the tree is 1
    void split1(Node h) {
        //These nodes are what are what h splits too.
        Node leftPartition = new Node();
        Node rightPartition = new Node();
        // copies all the keys into the appropriate node.
        for (int i = 0; i < h.getNumberOfKeys() / 2; i++) {
            leftPartition.setKey(h.keys[i]);
            rightPartition.setKey(h.keys[(h.getNumberOfKeys() - (1 + i))]);
            h.keys[i] = 0;
            h.keys[h.getNumberOfKeys() - (1 + i)] = 0;
        }
        //changes the appropriate values such as number of keys.
        h.setNumberofKeys(h.getNumberOfKeys() - (leftPartition.getNumberOfKeys() + rightPartition.getNumberOfKeys()));
        h.keys[0] = h.keys[h.keys.length / 2];
        h.keys[h.keys.length / 2] = 0;
        h.next[0] = leftPartition;
        h.next[1] = rightPartition;
        h.setNumberofPointers(2);
        heightTree++;
        leftPartition.parent = rightPartition.parent = h;

    }

    void split2(Node h) {
        // checks if the parent of this node exists or not
        if (h.parent != null) {
            //if parent is not empty
            if (!h.parent.isEmpty()) {
                //this is the confusing recursion part.
                //it goes up to the parent and splits it.
                split2(h.parent);
                //then it goes back to the lower level and splits it. if the node is not the lowest
                //then, the node will go down again to split2(h.parent) and run split2(h).
                split2(h);
            } else {
                //one node is created which will be given the second half keys and pointers of node h
                Node newNode = new Node();
                //transfers the second have of the keys to the newnode
                for (int i = h.getNumberOfKeys() / 2 + 1; i < h.getNumberOfKeys(); i++) {
                    newNode.setKey(h.keys[i]);
                    h.keys[i] = 0;
                }
                // the median is transfered to the parent node.
                h.parent.setKey(h.keys[h.getNumberOfKeys() / 2]);
                h.keys[h.getNumberOfKeys() / 2] = 0;
                h.setNumberofKeys(h.getNumberOfKeys() - newNode.getNumberOfKeys() - 1);
                int newNodePointers = 0;
                //transfers the second half of the pointers to the newnodes
                for (int i = 0; i < h.getNumberofPointers() / 2; i++) {
                    newNode.next[i] = h.next[h.getNumberofPointers() / 2 + i];
                    newNode.next[i].parent = newNode;
                    h.next[h.getNumberofPointers() / 2 + i] = null;
                    newNodePointers++;
                }
                // the newnode is given the pointers and those pointers and the parent of those child has been changed to newnode
                newNode.setNumberofPointers(newNodePointers);
                h.setNumberofPointers(h.getNumberofPointers() - newNodePointers);
                h.parent.setPointer(newNode, h);
            }
        } else {
            //left and right partitions will be the two new nodes which will contain the h's elements.
            Node leftPartition = new Node();
            Node rightPartition = new Node();
            //the elements will be transferred in the following couple of lines.
            for (int i = 0; i < h.getNumberOfKeys() / 2; i++) {
                leftPartition.setKey(h.keys[i]);
                rightPartition.setKey(h.keys[(h.getNumberOfKeys() - (1 + i))]);
                h.keys[i] = 0;
                h.keys[h.getNumberOfKeys() - (1 + i)] = 0;
            }
            h.setNumberofKeys(h.getNumberOfKeys() - (leftPartition.getNumberOfKeys() + rightPartition.getNumberOfKeys()));
            h.keys[0] = h.keys[h.keys.length / 2];
            h.keys[h.keys.length / 2] = 0;
            for (int i = 0; i < h.getNumberofPointers() / 2; i++) {
                leftPartition.next[i] = h.next[i];
                leftPartition.next[i].parent = leftPartition;
                rightPartition.next[i] = h.next[h.getNumberofPointers() / 2 + i];
                rightPartition.next[i].parent = rightPartition;
                h.next[h.getNumberofPointers() / 2 + i] = null;
                h.next[i] = null;
            }
            leftPartition.setNumberofPointers(h.getNumberofPointers() / 2);
            rightPartition.setNumberofPointers(h.getNumberofPointers() / 2);
            h.next[0] = leftPartition;
            h.next[1] = rightPartition;
            h.setNumberofPointers(2);
            //the height of the tree will increase at this phase.
            heightTree++;
            leftPartition.parent = rightPartition.parent = h;
        }
    }
    int height = 1;

    int search(Node h, int key) {
        if (h.isKeyFound(key)) {
            return height;
        } else {
            boolean found = false;
            for (int i = 0; i < h.getNumberOfKeys(); i++) {
                if (key < h.keys[i]) {
                    found = true;
                    if (h.next[i] != null) {
                        height++;
                        return search(h.next[i], key);
                    } else {
                        return -1;
                    }
                }
            }
            if (found == false) {
                if (h.next[h.getNumberOfKeys()] != null) {
                    height++;
                    return search(h.next[h.getNumberOfKeys()], key);
                } else {
                    return -1;
                }
            }
        }
        return 0;
    }

    public void print(Node h) {
        if (h.next[0] == null) {

        } else {
            for (int i = 0; i < h.getNumberOfKeys(); i++){
                
            }
        }
    }
}

class Node {

    private static final int t = 2;
    private int numberOfKeys;
    private int numberOfPointers;
    Node parent;
    int[] keys = new int[2 * t - 1];
    Node[] next = new Node[2 * t];

    public int getNumberOfKeys() {
        return numberOfKeys;
    }

    boolean isKeyFound(int key) {
        for (int i = 0; i < numberOfKeys; i++) {
            if (key == keys[i]) {
                return true;
            }
        }
        return false;
    }

    //this method takes a key and inserts it in order into the appropriate spot in the b tree
    public void setKey(int key) {
        if (!isEmpty()) {
            System.out.println("not possible");
        } else {
            if (numberOfKeys == 0) {
                keys[0] = key;
            } else {
                // this boolean identifier works like a flag signal which says if the value has been inserted or not. 
                boolean IsthereBigger = false;
                for (int i = 0; i < numberOfKeys; i++) {
                    if (keys[i] > key) {
                        IsthereBigger = true;
                        for (int k = numberOfKeys; k > i; k--) {
                            keys[k] = keys[k - 1];
                        }
                        keys[i] = key;
                        // breaking the loop is important because otherwise, the loop would overwrite the key to all the elements in the array
                        break;
                    }
                }
                // this would run if the key has not been inserted. this will then add the element in the end of the program.
                if (IsthereBigger == false) {
                    keys[numberOfKeys] = key;
                }
            }
            numberOfKeys++;
        }

    }

    public void setPointer(Node newNode, Node currentNode) {
        if (next[numberOfPointers - 1] == currentNode) {
            next[numberOfPointers] = newNode;
            newNode.parent = this;
        } else {
            for (int i = 0; i < numberOfPointers; i++) {
                if (next[i] == currentNode) {
                    for (int j = numberOfPointers; j > i + 1; j--) {
                        next[j] = next[j - 1];
                    }
                    next[i + 1] = newNode;
                    newNode.parent = this;
                    break;
                }
            }
        }
        numberOfPointers++;
    }

    public boolean isEmpty() {
        return numberOfKeys < keys.length;
    }

    public int getNumberofPointers() {
        return numberOfPointers;
    }

    public void printNode() {
        for (int i = 0; i < keys.length; i++) {
            System.out.println(keys[i]);
        }
    }

    public void setNumberofKeys(int NumberOfKeys) {
        numberOfKeys = NumberOfKeys;
    }

    public void setNumberofPointers(int NumberOfPointers) {
        numberOfPointers = NumberOfPointers;
    }
}
