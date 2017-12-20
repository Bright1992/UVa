#include <cstdio>
#include <cstring>
#include <cctype>

char mirror[] = "A***3**HIL*JM*O***2TUVWXY51SE*Z**8*";

#define MAX_LEN 10000

int main(){
    char msg[MAX_LEN];
    while(fgets(msg,MAX_LEN,stdin)!=NULL){
        int len = strlen(msg);
        if(msg[len-1]=='\n')    msg[--len]='\0';
        int isPan=1,isMir=1;
        for(int i=0;i<=len/2;++i){
            int p = (isalpha(msg[i])?(msg[i]-'A'):(msg[i]-'1'+26));
            if(mirror[p]!=msg[len-1-i]) isMir=0;
            if(msg[len-1-i]!=msg[i])    isPan=0;
            if(isMir==0&&isPan==0)  break;
        }
        if(isMir && isPan)
            printf("%s -- is a mirrored palindrome.\n\n",msg);
        if(isMir && !isPan)
            printf("%s -- is a mirrored string.\n\n",msg);
        if(!isMir && isPan)
            printf("%s -- is a regular palindrome.\n\n",msg);
        if(!isMir && !isPan)
            printf("%s -- is not a palindrome.\n\n",msg);
    }
    return 0;
}
