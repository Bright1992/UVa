#include <cstdio>
#include <cstring>
#include <string>
#include <algorithm>
#include <cassert>

using namespace std;

class big_integer{
public:
    int digits[3000];
    int length;
    const int BASE=100000000;
    const int WIDTH = 8;

    big_integer(const char *num){
        int len=strlen(num);
        int d=0,b=1;
        length=(len-1)/WIDTH+1;
        int idx=length-1;
        for(int i=len-1;i>=0;--i){
            d=d+(num[i]-'0')*b;
            b*=10;
            if(b==BASE){
                digits[idx--]=d;
                b=1;
                d=0;
            }
        }
        if(idx>=0)
            digits[idx]=d;
    }

    big_integer(const int *num, const int len):length(len){
        for(int i=0;i<len;++i){
            digits[i]=num[i];
        }
    }

    char *get_digits(char *out, int l=0){
        if(l==0)    l=length*8;
        int l2=l;
        memset(out,0,l);
        int fir=0;
        for(int i=0;i<length;++i){
            int d=digits[i];
            int cnt=0;
            if(i==0){
                while(d>0){
                    out[fir++]='0'+d%10;
                    if(--l==0)  break;
                    d/=10;
                }
                reverse(out,0,fir);
                if(l==0)    break;
                continue;
            }
            int b=BASE/10;
            while(cnt!=8){
                out[fir+(i-1)*8+cnt]=(char)('0'+(d/b));
                d%=b;b/=10;cnt++;
                if(--l==0)  break;
            }
            if(l==0)    break;
        }
        out[l2]=0;
        return out;
    }

    big_integer():length(0){}

    big_integer &operator=(const big_integer &other){
        for(int i=0;i<other.length+1;++i) digits[i]=other.digits[i];
        length=other.length;
        return *this;
    }

    big_integer operator+(const big_integer &other){
        int max_len=(length>other.length?length:other.length)+1;
        //printf("%d\n",max_len);
        int *res = new int[max_len];
        int i=length-1,j=other.length-1;
        int c=0;
        for(;i>=0&&j>=0;--i,--j){
            int a=digits[i], b=other.digits[j];
            res[max_len-(length-i)]=(a+b+c)%BASE;
            c=(a+b+c)/BASE;
        }
        while(i>=0){
            int a = digits[i];
            res[max_len-(length-i)]=(a+c)%BASE;
            c=(a+c)/BASE;
            i--;
        }
        while(j>=0){
            int a= other.digits[j];
            res[max_len-(other.length-j)]=(a+c)%BASE;
            c=(a+c)/BASE;
            j--;
        }
        big_integer ret;
        if(c>0){
            res[0]=c;
            ret =big_integer(res,max_len);
        }
        else    {ret=big_integer(res+1,max_len-1);}
        delete[] res;
        return ret;
    }
private:
    void reverse(char *out, int start, int end){
        for(int i=start,j=end-1;i<j;++i,--j){
            char tmp=out[i];
            out[i]=out[j];
            out[j]=tmp;
        }
    }
};

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
    printf("get:%s\n",a.get_digits(s,8));
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
        #ifdef _DEBUG
        printf("%s\n",c.get_digits(fibs,40));
        #else
        dict.insert(c.get_digits(fibs,40),i);
        #endif // _DEBUG
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
