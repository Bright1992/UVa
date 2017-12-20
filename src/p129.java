import java.util.Scanner;

public class p129 {
    static int cur;
    static boolean dfs(int n, int L, int pos, char[] ret){
        if(cur==n)  return true;
        for(int i=0;i<L;++i){
            if(isHard(ret,pos,(char)('A'+i))) {
                ret[pos]=(char)('A'+i);
                cur++;
                if(dfs(n, L, pos+1, ret))
                    return true;
                ret[pos]=0;
            }
        }
        return false;
    }
    static boolean isHard(char[] ret, int last, char add){
        for(int i=last-1;i>=last/2;i-=1){
            if(add!=ret[i]) continue;
            int j;
            for(j=1;j<(last-i);++j)
                if(ret[last-j]!=ret[i-j])   break;
            if(j==last-i)   return false;
        }
        return true;
    }
    public static void main(String[] argv){
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextInt()){
            int n=scanner.nextInt(), L=scanner.nextInt();
            if(n==0)    return;
            char [] ret = new char[n];
            cur=0;
            dfs(n,L,0,ret);
            int i;
            for(i=0;i<ret.length;++i){
                if(ret[i]==0)   break;
                if(i%(16*4)==0&&i>0)    System.out.println();
                else if(i%4==0&&i>0)    System.out.print(" ");
                System.out.print(ret[i]);
            }
            System.out.println();
            System.out.println(i);
        }
    }
}
