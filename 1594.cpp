#include <cstdio>
#include <cstring>
#include <algorithm>

using namespace std;

int main(){
    int N,n;
    scanf("%d",&N);
    for(int i=0;i<N;++i){
        scanf("%d",&n);
        int *A = new int[n];
        for(int j=0;j<n;++j){
            scanf("%d",A+j);
        }
        int cnt=0,loop=1;
        while(loop){
            loop=0;
            int tmp=A[0];
            for(int j=1;j<n;++j){
                A[j-1]=abs(A[j]-A[j-1]);
                if(A[j-1]!=0)   loop=1;
            }
            A[n-1]=abs(tmp-A[n-1]);
            if(A[n-1]!=0)   loop=1;
            if(++cnt==1000) break;
        }
        if(loop)
            puts("LOOP");
        else
            puts("ZERO");
        if(i<N-1)   putc('\n',stdin);
    }
    return 0;
}
