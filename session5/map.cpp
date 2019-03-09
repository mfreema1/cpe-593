#include<map>
#include<iostream>
#include<string>
using namespace std;

int main() {
    map<string, double> stock;
    stock["IBM"] = 119.5;
    stock["BIDU"] = 138;
    stock["AAPL"] = 152;

    for(auto p : stock) {
        cout << p.first << " ==> " << p.second << "\n";
    }
    
}