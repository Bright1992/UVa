#include <cstdio>
#include <cstring>

char cubes[14];

void rotateLeft(){
    char tmp[14];
    strcpy(tmp,cubes);
    tmp[1]=cubes[3];
    tmp[3]=cubes[4];
    tmp[4]=cubes[2];
    tmp[2]=cubes[1];
    strcpy(cubes,tmp);
}

void rotateUp(){
    char tmp[14];
    strcpy(tmp,cubes);
    tmp[0]=cubes[1];
    tmp[1]=cubes[5];
    tmp[5]=cubes[4];
    tmp[4]=cubes[0];
    strcpy(cubes,tmp);
}

int isEqn(){
    for(int i=0;i<6;++i){
        if(cubes[i]!=cubes[i+6])    return 0;
    }
    return 1;
}

int main(){
    int cnt=0;
    while(fgets(cubes,14,stdin)!=NULL){
        int len=strlen(cubes);
        if(len<12)  return 0;
        int i;
        cnt++;
        for(i=0;i<4;++i){
            int j;
            for(j=0;j<4;++j){
                if(isEqn()) {printf("TRUE\n");break;}
                rotateLeft();
            }
            if(j<4) break;
            rotateUp();
        }
        int j;
        if(i==4){
            rotateLeft();
            rotateUp();
            for(j=0;j<4;++j){
                if(isEqn()) {printf("TRUE\n");break;}
                rotateLeft();
            }
            if(j==4){
                rotateUp();rotateUp();
                for(j=0;j<4;++j){
                    if(isEqn()) {printf("TRUE\n");break;}
                    rotateLeft();
                }
            }
            if(j<4)    continue;
        }
        if(i==4)
            printf("FALSE\n");
    }
    return 0;
}
