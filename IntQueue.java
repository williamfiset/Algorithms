
public class IntQueue {

  private int [] ar;
  private int front, end, sz;

  // max_sz is the maximum number of items
  // that can be in the queue at any given time
  public IntQueue(int max_sz) {
    front = end = 0;
    this.sz = max_sz+1;
    ar = new int[sz];
  }

  public boolean isEmpty() {
    return front == end;
  }

  public void enqueue(int value) {
    ar[end] = value;
    end = (end + 1) % sz;
    if (end == front) throw new RuntimeException("Queue too small!");
  }

  // Make sure you check is the queue is not empty before calling dequeue!
  public int dequeue() {
    int ret_val = ar[front];
    front = (front + 1) % sz;
    return ret_val;
  }

}
