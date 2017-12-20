import java.util.Scanner;

public class p10562 {
    static char[][] map=new char[201][200];

    static String build(int r, int c){
        String ret=""+map[r][c];
        if(map[r+1][c]!='|'||map[r+2][c]!='-')    return ret+"()";
        int start=c,end=c;
        while(start>=0&&map[r+2][start--]=='-');
        while(end<map[r+2].length&&map[r+2][end++]=='-');
        ret+='(';
        for(int i=start+1;i<end;++i){
            if(map[r+3][i]==0)  break;
            if(map[r+3][i]!=' '&&map[r+3][i]!='-'&&map[r+3][i]!='|'&&map[r+3][i]!='#')
                ret += build(r + 3, i);
        }
        ret+=')';
        return ret;
    }

    public static void main(String[] argv){
        Scanner scanner = new Scanner(System.in);
        int N=scanner.nextInt();
        scanner.nextLine();
        while(N-->0){
            map = new char[201][200];
            String line;
            int cnt=0;
            while(!(line=scanner.nextLine()).equals("#")){
                for(int i=0;i<line.length();++i)
                    map[cnt][i]=line.charAt(i);
                cnt++;
            }
            int i=0,j=0;
            for(;i<cnt;++i) {
                j=0;
                for (; j < map[i].length; ++j)
                    if(map[i][j]!=' '&&map[i][j]!='-'&&map[i][j]!='#'&&map[i][j]!='|') {
                        break;
                    }
                if(j<map[i].length) break;
            }
            if(i==cnt){
                System.out.println("()");
                continue;
            }
            String tree="("+build(i,j)+")";
            System.out.println(tree);
        }
    }
}
