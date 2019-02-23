class DoubleLinkedList {
private:
    class Node {
    public:
        Node* next;
        Node* prev;
        int val; //could also make a templated one
    };

    Node* head;
    Node* tail;

public:
}