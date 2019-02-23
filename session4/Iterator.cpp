#include<iostream>
#include<vector>
#include<list>
using namespace std;

int main() {
    //with a vector this is fine, but with a list it is not
    vector<int> a;
    const int n = 10;
    a.reserve(n);
    for(int i = 0; i < n; i++) {
        a.push_back(i);
    }

    //only for vector, otherwise costly for list
    for(int i = 0; i < n; i++) {
        cout << a[i] << ' ';
    }
    cout << '\n';
    
    //in every class there is an iterator, can make some really nice stuff
    for(vector<int>::iterator i = a.begin(); i != a.end(); ++i) {
        cout << *i << ' ';
    }
    cout << '\n';

    for(auto x : a) { //figure out the type for me!
        cout << x << ' ';
    }
    cout <<'\n';
}