import java.util.Scanner;

public class p1572 {
    static int[] vis = new int[52];
    static int[][] graph = new int[52][52];
    static boolean acyclic(){
        for(int i=0;i<52;++i){
            if(vis[i]==0)
                if(!dfs(i)) return false;
        }
        return true;
    }
    static boolean dfs(int n){
        vis[n]=-1;  //visiting
        for(int i=0;i<52;++i){
            if(graph[n][i]==1){
                if(vis[i]==-1)  return false;
                if(vis[i]==0)
                    if(!dfs(i)) return false;
            }
        }
        vis[n]=1;   //finish visiting
        return true;
    }
    static int id(char a1, char a2){
        return 2*(a1-'A')+(a2=='+'?1:0);
    }
    static void connect(char a1, char a2, char b1, char b2){
        if(a1=='0'||b1=='0')    return;
        graph[id(a1,a2)^1][id(b1,b2)]=1;
    }
    public static void main(String[] argv){
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextInt()){
            int N= scanner.nextInt();
            graph=new int[52][52];
            vis = new int[52];
            while(N-->0){
                String s=scanner.next();
                for(int i=0;i<4;++i)
                    for(int j=0;j<4;++j){
                        if(i!=j)
                            connect(s.charAt(i*2),s.charAt(i*2+1),s.charAt(j*2),s.charAt(j*2+1));
                    }
            }
            System.out.println(acyclic()?"bounded":"unbounded");
        }
    }
}
