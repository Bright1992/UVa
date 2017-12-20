#include <queue>
#include <set>
#include <cstdio>

using namespace std;

int main(){
    priority_queue<long long,vector<long long>,greater<long long> > ugly;
    set<long long> traversed;
    int cnt=1;
    ugly.push(1);
    long long n;
    for(;cnt<=1500;++cnt){
        n=ugly.top();
        ugly.pop();
        if(traversed.count(2*n)==0){
            ugly.push(n*2);
            traversed.insert(n*2);
        }
        if(traversed.count(3*n)==0){
            ugly.push(n*3);
            traversed.insert(n*3);
        }
        if(traversed.count(5*n)==0){
            ugly.push(n*5);
            traversed.insert(n*5);
        }
    }
    printf("The 1500'th ugly number is %lld.\n",n);
    return 0;
}
