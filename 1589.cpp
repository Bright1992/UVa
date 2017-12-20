#include <cstdio>
#include <cstring>

char map[10][10];
int occupied[10][10];

void onCannon(int x, int y){
    int blocked=0;
    for(int i=x+1;i<10;++i){
        if(map[i][y]=='0'||map[i][y]=='B'){
            if(blocked) occupied[i][y]=1;
        }
        else{
            if(blocked==0)  blocked=1;
            else {
                occupied[i][y]=1;
                break;
            }
        }
    }
    blocked=0;
    for(int i=x-1;i>=0;--i){
        if(map[i][y]=='0'||map[i][y]=='B'){
            if(blocked) occupied[i][y]=1;
        }
        else{
            if(blocked==0)  blocked=1;
            else {
                occupied[i][y]=1;
                break;
            }
        }
    }
    blocked=0;
    for(int j=y+1;j<9;++j){
        if(map[x][j]=='0'||map[x][j]=='B'){
            if(blocked) occupied[x][j]=1;
        }
        else{
            if(blocked==0)  blocked=1;
            else {
                occupied[x][j]=1;
                break;
            }
        }
    }
    blocked = 0;
    for(int j=y-1;j>=0;--j){
        if(map[x][j]=='0'||map[x][j]=='B'){
            if(blocked) occupied[x][j]=1;
        }
        else{
            if(blocked==0)  blocked=1;
            else {
                occupied[x][j]=1;
                break;
            }
        }
    }
}

void onHorse(int x, int y){
    if(x>=2){
        if(map[x-1][y]=='0'){
            if(y>=1)    occupied[x-2][y-1]=1;
            if(y<=8)    occupied[x-2][y+1]=1;
        }
    }
    if(x<=7){
        if(map[x+1][y]=='0'){
            if(y>=1)    occupied[x+2][y-1]=1;
            if(y<=8)    occupied[x+2][y+1]=1;
        }
    }
    if(y>=2){
        if(map[x][y-1]=='0'){
            if(x>=1)    occupied[x-1][y-2]=1;
            if(x<=7)    occupied[x+1][y-2]=1;
        }
    }
    if(y<=6){
        if(map[x][y+1]=='0'){
            if(x>=1)    occupied[x-1][y+2]=1;
            if(x<=7)    occupied[x+1][y+2]=1;
        }
    }
}

void onChariot(int x, int y, int mark=1){
    for(int i=x+1;i<10;++i){
        occupied[i][y]=mark;
        if(!(map[i][y]=='0'||map[i][y]=='B'))
            break;
    }
    for(int i=x-1;i>=0;--i){
        occupied[i][y]=mark;
        if(!(map[i][y]=='0'||map[i][y]=='B'))
            break;
    }
    for(int j=y+1;j<9;++j){
        occupied[x][j]=mark;
        if(!(map[x][j]=='0'||map[x][j]=='B'))
            break;
    }
    for(int j=y-1;j>=0;--j){
        occupied[x][j]=mark;
        if(!(map[x][j]=='0'||map[x][j]=='B'))
            break;
    }
}

void onGeneral(int x, int y){
    onChariot(x, y, 2);
}

void printmap(){
    for(int i=0;i<10;++i){
        for(int j=0;j<9;++j){
            printf("%c ",map[i][j]);
        }
        printf("\n");
    }
    printf("\n");
    for(int i=0;i<10;++i){
        for(int j=0;j<9;++j){
            printf("%d ",occupied[i][j]);
        }
        printf("\n");
    }
}

int main(){
    while(1){
        int N,x,y;
        scanf("%d %d %d",&N,&x,&y);
        if(N==0&&x==0&&y==0)    break;
        memset(map,'0',sizeof(map));
        memset(occupied,0,sizeof(occupied));
        map[--x][--y]='B';
        char c;
        int x1,y1;
        getchar();
        for(int i=0;i<N;++i){
            scanf("%c %d %d",&c,&x1,&y1);
            getchar();
            //printf("%d:%c %d %d\n",i,c,x1,y1);
            map[x1-1][y1-1]=c;
        }
        for(int i=0;i<10;++i){
            for(int j=0;j<9;++j){
                if(map[i][j]!='0'&&map[i][j]!='B'){
                    switch(map[i][j]){
                    case 'C':
                        onCannon(i,j);
                        break;
                    case 'R':
                        onChariot(i,j);
                        break;
                    case 'H':
                        onHorse(i,j);
                        break;
                    case 'G':
                        onGeneral(i,j);
                        break;
                    default:
                        break;
                    }
                }
            }
        }
        //printmap();
        if(occupied[x][y]==2){
            printf("NO\n");
            continue;
        }
        if(x>0){
            if(occupied[x-1][y]==0){
                printf("NO\n");
                continue;
            }
        }
        if(x<2){
            if(occupied[x+1][y]==0){
                printf("NO\n");
                continue;
            }
        }
        if(y>3){
            if(occupied[x][y-1]==0){
                printf("NO\n");
                continue;
            }
        }
        if(y<5){
            if(occupied[x][y+1]==0){
                printf("NO\n",y);
                continue;
            }
        }
        printf("YES\n");
    }
    return 0;
}
