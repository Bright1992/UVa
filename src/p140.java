import java.util.Arrays;
import java.util.Scanner;

public class p140 {
    static int conn[][] = new int[26][26];
    static boolean mark[] = new boolean[26];
    static char syms[] = new char[8];
    static final int MAX_POSITIVE=0x7FFFFFFF;
    public static void main(String[] argv){
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            if(line.equals("#"))    return;
            conn=new int[26][26];
            mark=new boolean[26];
            syms=new char[8];
            for(int i=0;i<8;++i)    syms[i]='Z'+1;
            int node_cnt=0;
            for(String s:line.split(";")){
                String []pair=s.split(":");
                char node=pair[0].charAt(0);
                if(!mark[node-'A']){
                    mark[node-'A']=true;syms[node_cnt++]=node;
                }
                for(int i=0;i<pair[1].length();++i){
                    char adj=pair[1].charAt(i);
                    if(!mark[adj-'A']){
                        mark[adj-'A']=true;syms[node_cnt++]=adj;
                    }
                    conn[node-'A'][adj-'A']=conn[adj-'A'][node-'A']=1;
                }
            }
            Arrays.sort(syms);
            min_width=MAX_POSITIVE;
            seq = new char[node_cnt];
            vis =  new boolean[node_cnt];
            char perm[] = new char[node_cnt];
            dfs(node_cnt,0,perm,0);
            for(int i=0;i<node_cnt;++i){
                System.out.print("" + seq[i]+" ");
            }
            System.out.println("-> "+min_width);
        }
    }
    static int min_width;
    static char seq[];
    static boolean vis[];
    static void dfs(int node_cnt, int cur, char[] perm, int max_width){
        if(cur==node_cnt) {
            if(min_width>max_width) {
                min_width = max_width;
                for (int i = 0; i < perm.length; ++i) seq[i] = perm[i];
            }
            return;
        }
        for(int i=0;i<node_cnt;++i){
            perm[cur]=syms[i];
            if(cur==0){
                vis[i]=true;
                dfs(node_cnt,cur+1,perm,0);
                vis[i]=false;
                continue;
            }
            if(vis[i])  continue;
            for(int j=cur-1;j>=0;--j){
                if(conn[perm[j]-'A'][perm[cur]-'A']==1){
                    if(cur-j>max_width){
                        max_width=cur-j;
                        if(max_width>min_width) return;
                    }
                }
            }
            vis[i]=true;
            dfs(node_cnt,cur+1,perm,max_width);
            vis[i]=false;
        }
    }
}
