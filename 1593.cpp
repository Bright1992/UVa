#include <vector>
#include <string>
#include <iostream>
#include <cstring>

using namespace std;

int main(){
    vector<vector<string> >words;
    string line;
    while(getline(cin,line)){
        vector<string> sline;
        int state=0;
        string word;
        for(int i=0;i<line.size();++i){
            if(line[i]==' '){
                if(word!=""){
                    sline.push_back(word);
                    word="";
                }
                continue;
            }
            word+=line[i];
        }
        if(word!="")   sline.push_back(word);
        words.push_back(sline);
    }
    int max_s = 0;
    for(int i=0;i<words.size();++i)
        if(words[i].size()>max_s)   max_s=words[i].size();
    int *format = new int[max_s];
    memset(format,0,max_s*sizeof(int));
    for(int j=0;j<max_s;++j){
        for(int i=0;i<words.size();++i){
            if(words[i].size()>j)
                format[j]=format[j]<words[i][j].size()?words[i][j].size():format[j];
        }
    }
    for(int i=0;i<words.size();++i){
        for(int j=0;j<words[i].size();++j){
            cout<<words[i][j];
            if(j<words[i].size()-1)
                for(int k=0;k<format[j]-(int)words[i][j].size()+1;++k){
                    cout<<" ";
                }
        }
        cout<<endl;
    }
    return 0;
}
