import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class p1600 {
    static int[][] map;
    static int[][] vis;
    static int k;
    static class Step{
        int ttl, len;
        public Step(int ttl, int len){
            this.ttl=ttl;this.len=len;
        }
    }
    static class Point{
        int x,y,len,ttl;
        public Point(int x,int y,int len,int ttl){
            this.x=x;this.y=y;this.len=len;this.ttl=ttl;
        }
    }
    static ArrayList<Step>[][] step;
    static int bfs(int m, int n){
        int x=0,y=0;
        LinkedList<Point> q = new LinkedList<>();
        vis[x][y]=1;
        q.addLast(new Point(x,y,0,k));
        while(!q.isEmpty()){
            Point p = q.pollFirst();
            if(p.x==m-1&&p.y==n-1)
                return p.len;
            Point p1 = new Point(p.x,p.y,p.len,p.ttl);
            if(valid(p.x+1,p.y,p)){
                q.addLast(new Point(p.x+1,p.y,p.len,p.ttl));
            }
            p=new Point(p1.x,p1.y,p1.len,p1.ttl);
            if(valid(p.x-1,p.y,p)){
                q.addLast(new Point(p.x-1,p.y,p.len,p.ttl));
            }
            p=new Point(p1.x,p1.y,p1.len,p1.ttl);
            if(valid(p.x,p.y-1,p)){
                q.addLast(new Point(p.x,p.y-1,p.len,p.ttl));
            }
            p=new Point(p1.x,p1.y,p1.len,p1.ttl);
            if(valid(p.x,p.y+1,p)){
                q.addLast(new Point(p.x,p.y+1,p.len,p.ttl));
            }
        }
        return -1;
    }
    static boolean valid(int x,int y, Point p){
        if(x>=0&&x<map.length&&y>=0&&y<map[0].length){
            if(vis[x][y]==1)    return false;
            p.len++;
            if(map[x][y]==0){
                vis[x][y]=1;
                p.ttl=k;
                return true;
            }
            p.ttl--;
            if(p.ttl<0)    return false;
            if(step[x][y]==null){
                step[x][y]=new ArrayList<>();
                step[x][y].add(new Step(p.ttl,p.len));
                return true;
            }
            for(Step s:step[x][y]){
                if(s.len<=p.len&&s.ttl>=p.ttl)  return false;
            }
            step[x][y].add(new Step(p.ttl,p.len));
            return true;
        }
        return false;
    }

    public static void main(String[] argv){
        Scanner scanner= new Scanner(System.in);
        int N=scanner.nextInt();
        while(N-->0){
            int m=scanner.nextInt(), n=scanner.nextInt();
            k=scanner.nextInt();
            map=new int[m][n];
            step=new ArrayList[m][n];
            vis = new int[m][n];
            for(int i=0;i<m;++i){
                for(int j=0;j<n;++j)
                    map[i][j]=scanner.nextInt();
            }
            System.out.println(bfs(m,n));
        }
    }
}
