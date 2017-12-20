#include <fstream>
#include <iostream>
#include <random>
#include <time.h>

using namespace std;

int main(){
    fstream f("12333_gen.txt",ios::out);
    if(f==nullptr) cout<<"can't open file"<<endl;
    srand(time(0));
    f<<40000<<endl;
    for(int i=0;i<40000;++i)
        f<<rand()*1000/RAND_MAX<<endl;;
    f.close();
}
