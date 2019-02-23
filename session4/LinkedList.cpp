template<typename T>
class LinkedList {
private:
    class Node {
    public:
        Node* next; //in C++, just saying "Node" would be a recursive object reference
        T val; //using a generic or template
        Node(Node* next, const T& val) : next(next) , val(v) {}
    };
    Node* head;

public:
    LinkedList() : head(nullptr) {}
    void addStart(const T& val) { //we are unsure of the size of this, don't copy it
        Node* temp = new Node(head, val);
        //replaced with the constructor
        // temp->val = val; //arrow is a dereference followed by an access
        // temp->next = head; 
        head = temp;
        //or just in one line:
        //head = new Node(head, val);
    }

    void addEnd(const T& val) {
        Node* temp = new Node(nullptr, val);
        Node* p = head;
        if(p == null) {
            head = temp;
            return;
        }
        for(; p->next != nullptr; p = p->next);
        p->next = temp;
    }

    void removeStart() {
        Node* temp = head;
        head = head->next;
        delete temp; //MUST free the memory or you will leak
    }

    void removeEnd() {
        Node* p = head;
        Node* q = p.next;
        if(q == nullptr) {
            delete head;
            head = nullptr;
            return;
        }
        for(; q->next != nullptr; p = q, q = q->next);
        delete p->next;
        p->next = nullptr;
    }

    //make a copy of a linked list
    LinkedList(const LinkedList& orig) {
        if(orig.head == nullptr) {
            head = nullptr;
            return;
        }
        head = new Node(nullptr, orig.head->val);
        Node* p = head;

        Node* src = orig.head->next;
        Node* dest = head;
        while(src != null) {
            dest->next = new Node(nullptr, src->val);
            dest = dest->next;
            src = src->next;
        }
    }

    //the destructor
    ~LinkedList() {
        //remember you can't advance on something after you've deleted it
        for(Node* p = head; p != nullptr;){
            Node* temp = p;
            p = p->next;
            delete temp;
        }
    }

    //calling a function which returns a list
    LinkedList<int> f() {
        LinkedList<int> a;
        a.addEnd(5);
        a.addEnd(4);
        return a;
    }

    //move constructor, instead of giving memory to a variable that is going to die anyways, just move its memory
    //can only use when you call a function and return by value
    LinkedList(LinkedList&& orig) {
        head = orig.head;
        return head;
    }

};

int main() {
    LinkedList<int> a;
    const int n = 10;
    for(int i = 0; i < n; i++) {
        a.addEnd(i);
        a.addStart(i);
    }
    a.removeStart();

    LinkedList<int> b = a;
    LinkedList<int> c = f();
}