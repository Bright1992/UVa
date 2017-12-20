import java.util.Scanner;

public class p1103 {
    static char[][] map;
    static int cur;
    final static int dir[][] = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    final static char[][] codes = {
            {'0', '0', '0', '0'}, {'0', '0', '0', '1'}, {'0', '0', '1', '0'}, {'0', '0', '1', '1'},
            {'0', '1', '0', '0'}, {'0', '1', '0', '1'}, {'0', '1', '1', '0'}, {'0', '1', '1', '1'},
            {'1', '0', '0', '0'}, {'1', '0', '0', '1'}, {'1', '0', '1', '0'}, {'1', '0', '1', '1'},
            {'1', '1', '0', '0'}, {'1', '1', '0', '1'}, {'1', '1', '1', '0'}, {'1', '1', '1', '1'}
    };
    final static char[] syms={'W','A','K','J','S','D'};
    static int[] cnts;
    final static int[] order={1,5,3,2,4,0};

    static void str2map(String line, int row) {
        for (int i = 0; i < line.length(); ++i) {
            int idx = Integer.valueOf("" + line.charAt(i), 16);
            for (int j = i * 4; j < (i + 1) * 4; ++j)
                map[row][j] = codes[idx][j - i * 4];
        }
    }

    static boolean isValid(int r,int c){
        if(r<0||r>=map.length||c<0||c>=map[0].length)
            return false;
        return true;
    }

    static void dfs1(int r, int c) {    //find white blocks
        map[r][c]='-';
//        System.out.print(" "+dep++);
        for(int i=0;i<4;++i){
            int r1=r+dir[i][0], c1=c+dir[i][1];
            if(isValid(r1,c1)&&map[r1][c1]=='0')
                dfs1(r+dir[i][0],c+dir[i][1]);
        }
    }

    static void dfs2(int r,int c){  //count while blocks surrounded by black.
        if(!(isValid(r,c)&&map[r][c]=='1'))   return;
        map[r][c]='-';
        for(int i=0;i<4;++i){
            int r1=r+dir[i][0], c1=c+dir[i][1];
            if(!isValid(r1,c1)) continue;
            if(map[r1][c1]=='0') {
                dfs1(r1,c1);
                cur++;
            }
            else
                dfs2(r1,c1);
        }
    }

    static int dep=0;

    public static void main(String[] argv){
        Scanner scanner = new Scanner(System.in);
        int casen=0;
        while(true) {
            int H = scanner.nextInt(), W = scanner.nextInt();
            if (H == 0 && W == 0) return;
            dep=0;
            cnts = new int[6];
            map = new char[H][W * 4];
            scanner.nextLine();
            for (int i = 0; i < H; ++i) {
                String line = scanner.nextLine();
                str2map(line, i);
            }
            for(int i=0;i<H;++i) {
                if(map[i][0]=='0')
                    dfs1(i, 0);
                if(map[i][W*4-1]=='0')
                    dfs1(i,W*4-1);
            }
            for(int i=0;i<W*4;++i){
                if(map[0][i]=='0')
                    dfs1(0,i);
                if(map[H-1][i]=='0')
                    dfs1(H-1,i);
            }
            for (int i = 0; i < H; ++i) {
                for (int j = 0; j < W * 4; ++j) {
                    if (map[i][j] == '1') {
                        cur = 0;
                        dfs2(i, j);
                        cnts[cur]++;
                    }
                }
            }
            System.out.print("Case "+(++casen)+": ");
            for(int i=0;i<6;++i){
                for(int j=0;j<cnts[order[i]];++j)
                    System.out.print(syms[order[i]]);
            }
            System.out.println();
        }
    }
}
