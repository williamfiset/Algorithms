#include <stdio.h>
#include <string.h>

struct Order {
    int id;
    char name[50];
    int price;
};

typedef struct Order Order;

void acceptOrder(Order* order) {
    printf("\nMENU:\n1. Pizza 1 - Rs. 100\n2. Pizza 2 - Rs.100\nEnter Order Choice: ");
    scanf("%d", &order->id);
    switch(order->id) {
        case 1:{
            strcpy(order->name, "Pizza 1");
            order->price = 100;
            break;
        }
        case 2:{
            strcpy(order->name, "Pizza 2");
            order->price = 100;
            break;
        }
    }
    printf("\n");
}

void printOrder(Order order) {
    printf("Order Id: %d\nOrder Name: %s", order.id, order.name);
}

void addOrder(Order queue[], int* size, Order order) {
    if((*size) == 5) {
        printf("Queue is full\n");
    } 
    else {
        queue[*size] = order;
        (*size)++;
    }
}

void addOrderCircular(Order queue[], int size, int* front, int* rear, Order order) {
    if(((*rear)+1)%size == (*front)) {
        printf("Queue is full\n");
    } 
    else {
        if((*front) == -1) 
            *front = 0;
        *rear = ((*rear)+1)%size;
        queue[*rear] = order;
    }
}

Order* completeOrder(Order queue[], int* size) {
    if((*size) == 0) {
        printf("Queue is empty");
        return NULL;
    }
    else {
        Order* order = &queue[0];
        for (int i = 1; i < *size; i++) {
            queue[i-1] = queue[i];
        }
        (*size)--;
        return order;
    }
}

Order* completeOrderCircular(Order queue[], int size, int* front, int* rear) {
    if((*front) == -1) {
        printf("Queue is empty\n");
        return NULL;
    }
    Order* order = &queue[*front];
    if((*rear) == (*front)){
        *rear = -1;
        (*front) = -1;
    } 
    else
        *front = (((*front))+1)%size;
    return order;
}

void showOrders(Order queue[], int size) {
    if(size == 0) {
        printf("Queue is empty");
    }
    else {
        for (int i = 0; i < size; i++) {
            printf("%d\t", queue[i].id);
        }
        printf("\n");
    }
}

void showOrdersCircular(Order queue[], int size, int front, int rear) {
    if(front == -1) {
        printf("Queue is empty\n");
    }
    else {
        int i;
        for (i = front; i != rear; i=(i+1)%size) {
            printf("%d\t", queue[i].id);
        }
        printf("%d\t", queue[i].id);
    }
}

int main() {
    Order queue[5];
    int size = 0, n = 0, front = -1, rear = -1, queueType = 1;
    printf("\nMENU:\n1. Use simple queue\n2. Use circular queue\nEnter your choice: ");
    scanf("%d", &queueType);
    if(queueType != 1) size = 5;
    while(1) {
        printf("\nMENU:\n1. Add order\n2. Complete order\n3. Show orders\nEnter your choice: ");
        scanf("%d", &n);
        switch(n) {
            case 1: {
                Order o;
                acceptOrder(&o);
                if(queueType == 1)
                    addOrder(queue, &size, o);
                else
                    addOrderCircular(queue, size, &front, &rear, o);
                break;
            }
            case 2: {
                printf("Enter no. of orders to complete: ");
                scanf("%d", &n);
                int total = 0, i = 0;
                for(i = 0; i < n; i++) {
                    Order* order;
                    if(queueType == 1)
                        order = completeOrder(queue, &size);
                    else 
                        order = completeOrderCircular(queue, size, &front, &rear);
                    if(order == NULL)
                        break;
                    total += order->price;
                }
                printf("Successfully paid Rs. %d", total);
                break;
            }
            case 3: {
                if(queueType == 1)
                    showOrders(queue, size);
                else 
                    showOrdersCircular(queue, size, front, rear);
            }
        }
        //printf("\nFront: %d, Rear: %d", front, rear);
    }
}