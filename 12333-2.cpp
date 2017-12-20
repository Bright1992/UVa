#include <cstdio>
#include <cstring>
#include <string>
#include <algorithm>
#include <cassert>

using namespace std;

typedef long long ll;
const int mod=1000000007;
const int maxn=1e5+5;
const int inf=1e9;

const int ra=10;
int ten[4]= {1,ra,ra*ra,ra*ra*ra};
int radix=ra*ra*ra*ra;
const int NV=10000;
struct big_integer
{
    int d[NV];
    big_integer()
    {
        *this=big_integer(0);
    }
    big_integer(int x)
    {
        for (int i=0; i<NV; i++) d[i]=0;
        if (!x) d[0]=1;
        while(x)
        {
            d[++d[0]]=x%radix;
            x/=radix;
        }
    }
    big_integer(long long x)
    {
        for (int i=0; i<NV; i++) d[i]=0;
        if (!x) d[0]=1;
        while(x)
        {
            d[++d[0]]=x%radix;
            x/=radix;
        }
    }
    big_integer(const char s[])
    {
        int len=strlen(s),i,j,k;
        d[0]=(len-1)/4+1;
        for (i=1; i<NV; i++) d[i]=0;
        for (i=len-1; i>=0; i--)
        {
            j=(len-i-1)/4+1;
            k=(len-i-1)%4;
            d[j]+=ten[k]*(s[i]-'0');
        }
        while(d[0]>1&&d[d[0]]==0) d[0]--;
    }
    string get_digits()
    {
        string s;
        int i,j,temp;
        for (i=3; i>=1; i--) if (d[d[0]]>=ten[i]) break;
        temp=d[d[0]];
        int cnt=0;
        for (j=i; j>=0; j--)
        {
            s+=(char) (temp/ten[j]+'0');
            if(cnt++>41)return s;
            temp%=ten[j];
        }
        for (i=d[0]-1; i>0; i--)
        {
            temp=d[i];
            for (j=3; j>=0; j--)
            {
                s+=(char) (temp/ten[j]+'0');
                if(cnt++>41)return s;
                temp%=ten[j];
            }
        }
        return s;
    }
    void output()
    {
        int k=d[0];
        printf("%d",d[k--]);
        while(k) printf("%04d",d[k--]);
        putchar('\n');
    }
} d,mid1[15];
bool operator <(const big_integer &a,const big_integer &b)
{
    if (a.d[0]!=b.d[0]) return a.d[0]<b.d[0];
    for (int i=a.d[0]; i>0; i--)
        if (a.d[i]!=b.d[i])
            return a.d[i]<b.d[i];
    return 0;
}
big_integer operator +(const big_integer &a,const big_integer &b)
{
    big_integer c;
    c.d[0]=max(a.d[0],b.d[0]);
    int i,x=0;
    for (i=1; i<=c.d[0]; i++)
    {
        x+=a.d[i]+b.d[i];
        c.d[i]=x%radix;
        x/=radix;
    }
    while(x)
    {
        c.d[++c.d[0]]=x%radix;
        x/=radix;
    }
    return c;
}
big_integer operator -(const big_integer &a,const big_integer &b)
{
    big_integer c;
    c.d[0]=a.d[0];
    int i,x=0;
    for (i=1; i<=c.d[0]; i++)
    {
        x+=radix+a.d[i]-b.d[i];
        c.d[i]=x%radix;
        x=x/radix-1;
    }
    while(c.d[0]>1&&c.d[c.d[0]]==0) c.d[0]--;
    return c;
}
big_integer operator *(const big_integer &a,const big_integer &b)
{
    big_integer c;
    c.d[0]=a.d[0]+b.d[0];
    int i,j,x=0;
    for (i=1; i<=a.d[0]; i++)
    {
        x=0;
        for (j=1; j<=b.d[0]; j++)
        {
            x=a.d[i]*b.d[j]+x+c.d[i+j-1];
            c.d[i+j-1]=x%radix;
            x/=radix;
        }
        c.d[i+b.d[0]]=x;
    }
    while(c.d[0]>1&&c.d[c.d[0]]==0) c.d[0]--;
    return c;
}
big_integer operator *(const big_integer &a,const long long &k)
{
    big_integer c;
    c.d[0]=a.d[0];
    int i;
    long long x=0;
    for (i=1; i<=a.d[0]; i++)
    {
        x+=a.d[i]*k;
        c.d[i]=x%radix;
        x/=radix;
    }
    while(x>0)
    {
        c.d[++c.d[0]]=x%radix;
        x/=radix;
    }
    while(c.d[0]>1&&c.d[c.d[0]]==0) c.d[0]--;
    return c;
}
long long rem;
big_integer operator /(const big_integer &a,const long long &k)
{
    big_integer c;
    c.d[0]=a.d[0];
    long long x=0;
    for (int i=a.d[0]; i>=1; i--)
    {
        x+=a.d[i];
        c.d[i]=x/k;
        x%=k;
        rem=x;
        x*=radix;
    }
    while(c.d[0]>1&&c.d[c.d[0]]==0) c.d[0]--;
    return c;
}
bool smaller(const big_integer &a,const big_integer &b,int delta)
{
    if (a.d[0]+delta!=b.d[0]) return a.d[0]+delta<b.d[0];
    for (int i=a.d[0]; i>0; i--)
        if (a.d[i]!=b.d[i+delta])
            return a.d[i]<b.d[i+delta];
    return 1;
}
void Minus(big_integer &a,const big_integer &b,int delta)
{
    int i,x=0;
    for (i=1; i<=a.d[0]-delta; i++)
    {
        x+=radix+a.d[i+delta]-b.d[i];
        a.d[i+delta]=x%radix;
        x=x/radix-1;
    }
    while(a.d[0]>1&&a.d[a.d[0]]==0) a.d[0]--;
}
big_integer operator /(const big_integer &a,const big_integer &b)
{
    big_integer c;
    d=a;
    int i,j,temp;
    mid1[0]=b;
    for (i=1; i<=13; i++) mid1[i]=mid1[i-1]*2;
    for (i=a.d[0]-b.d[0]; i>=0; i--)
    {
        temp=8192;
        for (j=13; j>=0; j--)
        {
            if (smaller(mid1[j],d,i))
            {
                Minus(d,mid1[j],i);
                c.d[i+1]+=temp;
            }
            temp/=2;
        }
    }
    c.d[0]=max(1,a.d[0]-b.d[0]+1);
    while(c.d[0]>1&&c.d[c.d[0]]==0) c.d[0]--;
    return c;
}
bool operator ==(const big_integer &a,const big_integer &b)
{
    if (a.d[0]!=b.d[0]) return 0;
    for (int i=1; i<=a.d[0]; i++)
        if (a.d[i]!=b.d[i])
            return 0;
    return 1;
}
void init(int b) ///将大数切换至任意<=10进制
{
    for (int i=1; i<=3; i++)
        ten[i]=ten[i-1]*b;
    radix=b*b*b*b;
}


class trie{
public:
    class node{
    public:
        node* next[10];
        int min_idx;
        node(int n) : min_idx(n){
            for(int i=0;i<10;++i)   next[i]=nullptr;
        }
        node(){
            for(int i=0;i<10;++i)   next[i]=nullptr;
        }
    };
    trie():root(new node()){}
    void insert(const char *b_int, int min_idx){
        int len=strlen(b_int);
        node *tmp = root;
        for(int i=0;i<len;++i){
            if(tmp->next[b_int[i]-'0']==nullptr)
                tmp->next[b_int[i]-'0']=new node(min_idx);
            tmp=tmp->next[b_int[i]-'0'];
            tmp->min_idx = min_idx<tmp->min_idx?min_idx:tmp->min_idx;
        }
    }

    int find(const char *precode){
        int len=strlen(precode);
        node *tmp=root;
        for(int i=0;i<len;++i){
            if(tmp->next[precode[i]-'0']==nullptr)   return -1;
            tmp=tmp->next[precode[i]-'0'];
        }
        return tmp->min_idx;
    }

private:
    node *root;
};

#include <ctime>

void test(){
    clock_t t0 = clock();
    big_integer a("1246342567"),b("4568435346");
    big_integer c("123456789");
    char s[10000];
    printf("get:%s\n",a.get_digits().c_str());
    for(int i=0;i<50000;++i){
        c=a+b;
        a=b;b=c;
    }
    clock_t t1 = clock();
    printf("%lld\n",t1-t0);
    trie t;
    t.insert("12",10);
    t.insert("12345",15);
    printf("%d, %d\n",t.find("1"),t.find("12345"));
}

#define MAX_N 100000

//#define _DEBUG

char fibs[41];
big_integer a("1"),b("1"),c;
trie dict;

int main(){
//    test();
    dict.insert("1",0);
    #ifdef _DEBUG
    clock_t t0 = clock();
    #endif // _DEBUG
    for(int i=2;i<MAX_N;++i){
        c = a+b;
        dict.insert(c.get_digits().c_str(),i);
        a=b;b=c;
    }
    #ifdef _DEBUG
    clock_t t1 = clock();
    printf("cost %ld ms\n",t1-t0);
    #endif // _DEBUG
//    printf("%s\n",fibs[MAX_N-1]);
    char s[41];
    int n=0;
    scanf("%d\n",&n);
    for(int i=0;i<n;++i){
        scanf("%s",s);
        printf("Case #%d: %d\n",i+1,dict.find(s));
        }
}
