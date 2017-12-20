import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class p12166 {
    static HashMap<Long,Integer> map;
    static int size;
    static void dfs(String line, int s, int t, int level){
        if(line.charAt(s)=='['){
            int p=0;
            for(int i=s+1;i<t;++i){
                if(line.charAt(i)=='[') p++;
                if(line.charAt(i)==']') p--;
                if(p==0&&line.charAt(i)==','){
                    dfs(line,s+1,i,level+1);
                    dfs(line,i+1,t-1,level+1);
                    return;
                }
            }
        }
        else{
            int num=0;
            size++;
            for(int i=s;i<t;++i)    num=num*10+(line.charAt(i)-'0');
            long sumw=(long)num<<level;
            map.put(sumw,map.getOrDefault(sumw,0)+1);
        }
    }
    public static void main(String[] argv){
        Scanner scanner = new Scanner(System.in);
        int N= scanner.nextInt();
        if(N>0) scanner.nextLine();
        while(N-->0){
            map = new HashMap<>();
            size=0;
            String line=scanner.nextLine();
            int s=0,t=line.length();
            dfs(line,s,t,0);
            int maxn=0;
            for(Map.Entry<Long,Integer>e:map.entrySet()){
                maxn=Math.max(e.getValue(),maxn);
            }
            System.out.println(size-maxn);
        }
    }
}
