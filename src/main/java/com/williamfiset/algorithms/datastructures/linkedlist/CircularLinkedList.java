class CircularLinkedList{
    class Node{
        int data;
        Node next;
        public Node(int data){
            this.data = data;
        }
    }
    Node last = null;
    int length = 0;
    public void print(){
        Node p;
        if(last == null){
            System.out.println("List is empty");
            return;
        }
        p = last.next;
        do{
            System.out.println(p.data);
            p = p.next;
        }while(p != last.next);
        return;
    }
    public void insert(int data){
        Node p;
        Node tmp = new Node(data);
        if(last == null){
            last = tmp;
            last.next = tmp;
            length++;
            return;
        }
        tmp.next = last.next;
        last.next = tmp;
        last = tmp;
        length++;
        return;
    }
    public void insertStart(int data){
        Node tmp = new Node(data);
        if(last == null){
            last = tmp;
            last.next = tmp;
            length++;
            return;
        }
        tmp.next = last.next;
        last.next = tmp;
        length++;
        return;
    }
    public void delete(int data){
        Node tmp,p;
        if(last == null){
            System.out.println("List is empty");
            return;
        }
        if(last.next == last){
            if( last.data == data){
                last.next = null;
                last = null;
                length--;
                return;
            }
            else{
                System.out.println("Element not found");
                return;
            }
        }
        if(last.next.data == data){
            last.next = last.next.next;
            length--;
            return;
        }
        p = last.next;
        while(p.next != last.next){
            if(p.next.data == data ){
                tmp = p.next;
                p.next = tmp.next;
                if(p.next == last){
                    last = p;
                }
                length--;
                return;
            }
            p = p.next;
        }
    }
}

public class Main{
    public static void main(String args[]){
        CircularLinkedList c = new CircularLinkedList();
        c.insert(1);
        c.insert(2);
        c.insert(3);
        c.insert(4);
        c.insert(5);
        c.delete(5);
        c.print();
        
    }
}
