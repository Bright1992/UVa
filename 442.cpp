#include <iostream>
#include <map>
#include <stack>

using namespace std;

struct myPair{
    int r,c;
    myPair(int r,int c):r(r),c(c){}
    myPair(){}
};

//#define _DEBUG

int main(){
    int N=0;
    cin>>N;
    map<char,myPair> m;
    while(N--){
        char var=0;
        int r,c;
        cin>>var>>r>>c;
        myPair s(r,c);
        m[var]=s;
    }
    string line;
    getline(cin,line);
    while(getline(cin,line)){
        stack<char> syms;
        syms.push('#');     //lowest priority
        stack<myPair> mats;
        int sum=0;
        int err=0;
        #ifdef _DEBUG
        cout<<line<<endl;
        #endif // _DEBUG
        for(int i=0;i<line.size();++i){
            if(line[i]=='('){
                if(i>0&&line[i-1]!='(') syms.push('*');
                syms.push('(');
            }
            else if(line[i]==')'){
                syms.pop();
                if(syms.top()=='*'){
                    syms.pop();
                    myPair s2=mats.top();mats.pop();
                    myPair s1=mats.top();mats.pop();
                    if(s1.c!=s2.r){
                        cout<<"error"<<endl;
                        err=1;
                        break;
                    }
                    sum=sum+(s1.c*s1.r*s2.c);
                    mats.push(myPair(s1.r,s2.c));
                }
            }
            else{
                if(i==0)    mats.push(m[line[i]]);
                else if(line[i-1]=='('){
                    mats.push(m[line[i]]);
                }
                else{       //multiply
                    myPair s1=mats.top();mats.pop();
                    myPair s2=m[line[i]];
                    if(s1.c!=s2.r){
                        cout<<"error"<<endl;
                        err=1;
                        break;
                    }
                    sum=sum+(s1.c*s1.r*s2.c);
                    mats.push(myPair(s1.r,s2.c));
                }
            }
        }
        if(!err)
            cout<<sum<<endl;
        else
            err=0;
    }
    return 0;
}
