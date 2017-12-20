#include <iostream>
#include <set>
#include <map>

using namespace std;

int main(){
    map<set<int>,int> m;
    set<int> s1,s2;
    s1.insert(1);
    s2.insert(1);
    s2.insert(2);
    if(s1==s2)
        cout<<"equal"<<endl;
    else
        cout<<"not equal"<<endl;
}
