#include <stdio.h>
#include <string.h>
#include <ctype.h>

#define MAX_N 10000

int main(){
    int n;
    scanf("%d",&n);
    char str[MAX_N];
    int j=0;
    for(j=0;j<n;++j){
        scanf("%s",str);
        int len=strlen(str);
        //if(str[len-1]=='\n')    str[--len]='\0';
        int sum=0,cur=0;
        int i=0;
        for(i=0;i<len;++i){
            if(str[i]=='O') {cur++;sum+=cur;}
            else{
                cur=0;
            }
        }
        printf("%d\n",sum);
    }
    return 0;
}
