#include <cstdio>
#include <cstring>

class big_integer{
public:
    char digits[40000];
    int length;

    big_integer(const char *num){
        length=strlen(num);
        for(int i=0;i<length;++i)
           digits[i]=num[i];
        digits[length]='\0';
    }

    big_integer():length(0){}

    big_integer &operator=(const big_integer &other){
        for(int i=0;i<other.length+1;++i) digits[i]=other.digits[i];
        length=other.length;
//        printf("op=:%s\n",digits);
        return *this;
    }

    big_integer operator+(const big_integer &other){
        int max_len=(length>other.length?length:other.length)+1;
        char *res = new char[max_len+1];
        res[max_len]=0;
        int i=length-1,j=other.length-1;
//        printf("length=%d, other.length=%d\n",length,other.length);
        int c=0;
        for(;i>=0&&j>=0;--i,--j){
            int a=digits[i]-'0', b=other.digits[j]-'0';
            res[max_len-(length-i)]='0'+(a+b+c)%10;
//            printf("digit1=%d\n",(a+b+c)%10);
            c=(a+b+c)/10;
        }
        while(i>=0){
            int a = digits[i]-'0';
            res[max_len-(length-i)]='0'+(a+c)%10;
//            printf("digit2=%d\n",(a+c)%10);
            c=(a+c)/10;
            i--;
        }
        while(j>=0){
            int a= other.digits[j]-'0';
            res[max_len-(other.length-j)]='0'+(a+c)%10;
//            printf("digit3=%d\n",(a+c)%10);
            c=(a+c)/10;
            j--;
        }
        if(c==1)    res[0]='1';
        else    res++;
        big_integer ret(res);
        delete[] --res;
//        printf("op+:%s\n",ret.digits);
        return ret;
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
    big_integer a("1246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852"), b("1246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852124634256723478965872346785234678568234659872436895627983456726105783426785212463425672347896587234678523467856823465987243689562798345672610578342678521246342567234789658723467852346785682346598724368956279834567261057834267852");
    big_integer c;
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

char fibs[100000][41];

int main(){
//    test();
    trie dict;
    strcpy(fibs[0],"1");
    strcpy(fibs[1],"1");
    dict.insert("1",0);
    big_integer a("1"),b("1"),c;
    clock_t t0 = clock();
    for(int i=2;i<100000;++i){
        c = a+b;
        strncpy(fibs[i],c.digits,40);
        dict.insert(fibs[i],i);
        a=b;b=c;
//        printf("a%d:%s, b%d:%s\n",i,a.digits,i,b.digits);

    }
    clock_t t1 = clock();
    printf("cost %ld ms\n",t1-t0);
    char s[41];
    int n=0;
    scanf("%d\n",&n);
    for(int i=0;i<n;++i){
        scanf("%s",s);
//        printf(s);
        printf("Case #%d: %d\n",i+1,dict.find(s));
        }
}
