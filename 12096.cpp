#include <stack>
#include <map>
#include <vector>
#include <set>
#include <algorithm>
#include <cstdio>
#include <cstring>

using namespace std;

typedef set<int> Set;

map<Set,int> IDcache;
vector<Set> Setcache;

int ID(Set s){
    if(IDcache.count(s))    return IDcache[s];
    Setcache.push_back(s);
    IDcache[s]=Setcache.size()-1;
    //printf("new id: %d\n",IDcache[s]);
    return IDcache[s];
}

stack<int> s;

int main(){
    int N=0;
    scanf("%d",&N);
    for(int i=0;i<N;++i){
        int n=0;
        scanf("%d",&n);
        char cmd[10];
        for(int i=0;i<n;++i){
            scanf("%s",cmd);
            switch(cmd[0]){
                case 'P':
                    s.push(ID(Set()));
                    //printf("s.top: %d\n",s.top());
                    break;
                case 'D':
                    s.push(s.top());
                    break;
                case 'U':
                    {Set s1 = Setcache[s.top()];
                    s.pop();
                    Set s2 = Setcache[s.top()];
                    s.pop();
                    Set ns;
                    set_union(s1.begin(),s1.end(),s2.begin(),s2.end(),inserter(ns,ns.begin()));
                    s.push(ID(ns));}
                    break;
                case 'I':
                    {Set s1 = Setcache[s.top()];
                    s.pop();
                    Set s2 = Setcache[s.top()];
                    s.pop();
                    Set ns;
                    set_intersection(s1.begin(),s1.end(),s2.begin(),s2.end(),inserter(ns,ns.begin()));
                    s.push(ID(ns));}
                    break;
                case 'A':
                    //printf("%d\n",s.top());
                    {Set s1 = Setcache[s.top()];
                    //printf("1\n");
                    s.pop();
                    //printf("2\n");
                    Set s2 = Setcache[s.top()];
                    //printf("3\n");
                    s.pop();
                    //printf("4\n");
                    s2.insert(ID(s1));
                    //printf("5\n");
                    s.push(ID(s2));}
                    break;
                default:
                    break;
            }
            printf("%d\n",Setcache[s.top()].size());
        }
        printf("***\n");
    }
    return 0;
}
