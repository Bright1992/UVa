#include <cstdio>
#include <cstring>

char puzzle[5][7];
char cmd[100];
char err_msg[] = "This puzzle has no final configuration.\n";

void swap2(int x,int y,int x2,int y2){
    char tmp = puzzle[y][x];
    puzzle[y][x]=puzzle[y2][x2];
    puzzle[y2][x2]=tmp;
}

void print_puzzle(){
    for(int i=0;i<5;++i){
        int j=0;
        for(j=0;j<4;++j){
            printf("%c ",puzzle[i][j]);
        }
        printf("%c\n",puzzle[i][j]);
    }
    putchar('\n');
}

int main(){
    int cnt=0;
    while(fgets(cmd,100,stdin)!=NULL){
        cnt++;
        if(strlen(cmd)==2 && cmd[0]=='Z')   return 0;
        if(cnt%6!=0){
            strcpy(puzzle[cnt%6-1],cmd);
        }
        else{
            int x=0,y=0;
            for(;y<5;++y){
                for(;x<5;++x){
                    if(puzzle[y][x]==' '){
                        break;
                    }
                }
                if(x<5)
                    break;
                x=0;
            }
            printf("Puzzle #%d:\n",cnt/6);
            int i=0;
            while(cmd[strlen(cmd)-2]!='0'){
                fgets(cmd+strlen(cmd)-1,100-strlen(cmd)+1,stdin);
            }
            for(i=0;i<strlen(cmd);++i){
                if(cmd[i]=='0') break;
                if(cmd[i]=='A'){
                    if(y==0)    {printf("%s\n",err_msg);break;}
                    swap2(x,y,x,y-1);
                    y--;
                }
                else if(cmd[i]=='B'){
                    if(y==4)    {printf("%s\n",err_msg);break;}
                    swap2(x,y,x,y+1);
                    y++;
                }
                else if(cmd[i]=='R'){
                    if(x==4)    {printf("%s\n",err_msg);break;}
                    swap2(x,y,x+1,y);
                    ++x;
                }
                else if(cmd[i]=='L'){
                    if(x==0)    {printf("%s\n",err_msg);break;}
                    swap2(x,y,x-1,y);
                    --x;
                }
                else{
                    printf("%s\n",err_msg);break;
                }
            }
            if(cmd[i]=='0'){
                print_puzzle();
            }
        }
    }
    return 0;
}
