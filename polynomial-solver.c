#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <math.h>

struct Node {
    struct Node* next;
    int coeff;
    int power;
};

void addNode(struct Node** start, int power, int coeff) {
    if ((*start) == NULL) {
        *start = (struct Node *) malloc(sizeof(struct Node));
        (*start)->power = power;
        (*start)->coeff = coeff;
        (*start)->next = *start;
    }
    else {
        struct Node* current = (*start);
        while(current->next != (*start)) {
            current = current->next;
        }
        current->next = (struct Node *) malloc(sizeof(struct Node));
        current->next->next = *start;
        current->next->power = power;
        current->next->coeff = coeff;
    } 
}

void display(struct Node** start) {
    struct Node* current = *start;
    if(current == NULL)
        printf("Polynomial has no terms");
    else {
        while(current->next != (*start)) {
            printf("%dx^%d + ", current->coeff, current->power);
            current = current->next;
        }
        printf("%dx^%d\n", current->coeff, current->power);
    }
}

struct Node* add(struct Node** start1, struct Node** start2) {
    struct Node *current1 = *start1, *current2 = *start2, *addition = NULL;
    bool flag1 = false, flag2 = false;
    if(current1 == NULL)
        return current2;
    if(current2 == NULL)
        return current1;
    while(!flag1 && !flag2) {
        if(current1->power > current2->power) {
            addNode(&addition, current1->power, current1->coeff);
            current1 = current1->next;
            if(current1 == (*start1)) flag1 = true;
        }
        else if(current1->power < current2->power) {
            addNode(&addition, current2->power, current2->coeff);
            current2 = current2->next;
            if(current2 == (*start2)) flag2 = true;
        }
        else {
            addNode(&addition, current1->power, current1->coeff + current2->coeff);
            current1 = current1->next;
            current2 = current2->next;
            if(current1 == (*start1)) flag1 = true; 
            if(current2 == (*start2)) flag2 = true;
        }
    }

    while(!flag1) {
        addNode(&addition, current1->power, current1->coeff);
        current1 = current1->next;
        if(current1 == (*start1)) flag1 = true;
    }

    while(!flag2) {
        addNode(&addition, current2->power, current2->coeff);
        current2 = current2->next;
        if(current2 == (*start2)) flag2 = true;
    }

    return addition;
}

void createPolynomial(struct Node** start) {
    int n;
    printf("\nEnter number of terms in the polynomial: ");
    scanf("%d", &n);
    *start = NULL;
    for(int i = 0; i < n; i++) {
        printf("Enter term %d:\n", i+1);
        int power, coeff;
        printf("Enter coefficient of term: ");
        scanf("%d", &coeff);
        printf("Enter power of term: ");
        scanf("%d", &power);
        addNode(start, power, coeff);
        printf("\n");
    }
}

int evaluatePolynomial(struct Node** start, int x) {
    struct Node *current = *start;
    bool flag = false;
    int sum = 0;
    while (!flag) {
        sum += current->coeff * pow(x, current->power);
        current = current->next;
        if(current == (*start)) flag  = true;
    }
    return sum;
}

int main() {
    struct Node *start1 = NULL, *start2 = NULL;
    int n;
    while (1) {
        printf("\nMENU\n1. Create polynomial\n2. Display Polynomial\n3. Add Polynomial\n4. Evaluate Polynomial\nEnter your choice: ");
        scanf("%d", &n);
        switch(n) {
            case 1: {
                createPolynomial(&start1);
                break;
            }
            case 2: {
                printf("\n");
                display(&start1);
                break;
            }
            case 3 : {
                printf("\nEnter second polynomial: \n");
                createPolynomial(&start2);
                struct Node* addition = add(&start1, &start2);
                display(&addition);
                break;
            }
            case 4: {
                printf("Enter value to evaluate on: ");
                scanf("%d", &n);
                int ans = evaluatePolynomial(&start1, n);
                printf("\nValue of polynomial at x=%d: %d\n", n, ans);
            }
        }
    }
     
}
