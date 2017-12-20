#include <deque>
#include <iostream>
#include <queue>
#include <stack>
#include <map>
#include <set>
#include <string>
#include <algorithm>

using namespace std;

map<string, int> vars;
int times[7];

#define ASSIGNMENT 1
#define OUTPUT 2
#define LOCK 3
#define UNLOCK 4
#define END 5
#define INVALID 0

//#define _DEBUG

class cmd{
public:
    int op;
    string var;
    int val;
    string line;

public:
    cmd(string line):line(line){
        this->val=0;
        this->var="";
        this->op=INVALID;
        int s = line.size();
        string var="";
        int i=0;
        for(;i<s;++i){
            if(line[i]==' '){
                if(var=="")
                    continue;
                else
                    break;
            }
            if(line[i]=='='){
                break;
            }
            var+=line[i];
        }
        if(var=="")
            op=INVALID;
        else if(var=="lock")
            op=LOCK;
        else if(var=="unlock")
            op=UNLOCK;
        else if(var=="print"){
            op=OUTPUT;
            var="";
            for(;i<s;++i){
                if(line[i]==' '&&var=="")   continue;
                if(line[i]==' '&&var!="")   break;
                var+=line[i];
            }
            if(var==""){
                cerr<<"invalid cmd: print"<<endl;
                op=INVALID;
                return;
            }
            this->var=var;
        }
        else if(var=="end")
            op=END;
        else{
            for(;i<s;++i){
                if(line[i]=='=')    break;
            }
            i++;
            if(i==s){
                cerr<<"invalid cmd: var"<<endl;
                op=INVALID;
                return;
            }
            this->var=var;
            this->op=ASSIGNMENT;
            for(;i<s;++i){
                if(line[i]==' '){
                    continue;
                }
                if(line[i]<'0' || line[i]>'9')  break;
                val=val*10+(line[i]-'0');
//                cout<<this->var<<":"<<val<<endl;
            }
//            cout<<this->var<<":"<<val<<endl;
        }
    }
};

int get_cmd(string line){
    int len=line.size();
    for(int i=0;i<len;++i){
        if(line[i]==' ')    continue;

    }
}

int main(){
    int N=0;
    cin>>N;
    while(N--){
        vars.clear();
        for(int i=0;i<7;++i){
            times[i]=0;
            cin>>times[i];
        }
        string line;
        int cnt=0;
        #ifdef _DEBUG
        cout<<"case: "<<N<<" "<<times[0]<<" "<<times[1]<<" "<<times[2]<<endl;
        #endif // _DEBUG
        queue<cmd> **cq;
        cq = new queue<cmd>*[times[0]];
        for(int i=0;i<times[0];++i)    cq[i] = new queue<cmd>;
        deque<int> dq;
    //    set<string> locked;
        queue<int> waiting;
        int locked=0;
    //    int *state = new int[times[0]];
    //    vector<string> **occupied = new vector<string>*[times[0]];

        while(getline(cin,line)){
            cmd c(line);
            if(c.op==INVALID) continue;
            if(c.op==END) {
                if(++cnt==times[0])  break;
                continue;
            }            //END is not pushed into the waiting queue
            if(cnt==times[0])   break;
            cq[cnt]->push(c);
        }
        int *quantum_left = new int[times[0]];
        for(int i=0;i<times[0];++i){
            quantum_left[i] = times[6];
            dq.push_back(i);
    //        state[i]=0;
    //        occupied[i] = new vector<string>();
        }
        int turn=0;
        while(!dq.empty()){
            turn = dq.front();
            dq.pop_front();
            int w=0;
            while(quantum_left[turn]>0 && !cq[turn]->empty()){
                cmd c=cq[turn]->front();
    //            if(find(occupied[turn]->begin(),occupied[turn]->end(),c.var)==occupied[turn]->end() && locked.count(c.var)>0){
    //                if(waiting.count(c.var)==0)
    //                    waiting[c.var] = stack<int>();
    //                waiting[c.var].push(turn);
    //                break;
    //            }
                #ifdef _DEBUG
                cout<<"executing: ("<<turn+1<<")"<<c.line<<endl;
                #endif // _DEBUG
                if(c.op==LOCK){
                    if(!locked){
                        locked=1;
                    }
                    else{
                        waiting.push(turn);
                        #ifdef _DEBUG
                        cout<<"("<<turn+1<<") blocked"<<endl;
                        #endif
                        w=1;
                        break;
                    }
                }
                #ifdef _DEBUG
    //            cout<<c.op<<" "<<times[c.op]<<endl;
                #endif // _DEBUG
                cq[turn]->pop();
                switch(c.op){
                case ASSIGNMENT:
                    vars[c.var]=c.val;
                    break;
                case OUTPUT:
                    cout<<turn+1<<": "<<(vars.count(c.var)>0?vars[c.var]:0)<<endl;
                    break;
                case UNLOCK:
                    locked=0;
                    if(!waiting.empty()){
                        dq.push_front(waiting.front());
                        waiting.pop();
                    }
                    break;
                case END:
                    break;
                default:
                    break;
                }
                quantum_left[turn]-=times[c.op];
                #ifdef _DEBUG
    //            cout<<"quantum left:"<<quantum_left[turn]<<endl;
                #endif // _DEBUG
            }
            if(w==0&&!cq[turn]->empty())    dq.push_back(turn);
            quantum_left[turn]=times[6];
        }

        for(int i=0;i<times[0];++i) {
            delete cq[i];
    //        delete occupied[i];
        }
        delete [] cq;
    //    delete [] occupied;
        delete [] quantum_left;
    //    delete [] state;
        if(N>0) cout<<endl;
    }
    return 0;
}
