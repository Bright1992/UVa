package test;

public class Queens {
    static int[] rows = new int[8];
    static int[][] vis = new int[3][16];

    static int cnt=0;

    static void printQueens(int cur) {
        if (cur == 8) {
            System.out.println(""+(++cnt)+":");
            for (int i = 0; i < 8; ++i) {
                for (int j = 0; j < 8; ++j)
                    if (rows[i] == j)
                        System.out.print(1);
                    else
                        System.out.print(0);
                System.out.println();
            }
            System.out.println();
        }
        for (int j = 0; j < 8; ++j)
            if (vis[0][j] == 0 && vis[1][cur-j+7] == 0 && vis[2][cur+j] == 0) {
                rows[cur]=j;
                vis[0][j]=vis[1][cur-j+7]=vis[2][cur+j]=1;
                printQueens(cur+1);
                vis[0][j]=vis[1][cur-j+7]=vis[2][cur+j]=0;
            }
    }

    public static void main(String[] argv){
        printQueens(0);
    }
}
