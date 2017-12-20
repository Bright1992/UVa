import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class p221 {
    public static void main(String[] argv) {
        class Building {
            int x0, x1;
            int h;
            int y;
            boolean visible;

            public Building(int x, int xl, int y, int h) {
                visible=false;
                x0 = x;
                x1 = x + xl;
                this.y = y;
                this.h = h;
            }
        }
        class Pair implements Comparable<Pair> {
            int x;
            int idx;
            String type;

            public Pair(int x, int idx, String type) {
                this.x = x;
                this.idx = idx;
                this.type = type;
            }

            @Override
            public int compareTo(Pair other) {
                return Integer.compare(x, other.x);
            }
        }
        Scanner scanner = new Scanner(System.in);
        int cnt=0;
        while (scanner.hasNextInt()) {
            int N = scanner.nextInt();
            if (N == 0) break;
            if(cnt>0)
                System.out.println();
            PriorityQueue<Pair> pq = new PriorityQueue<>();
            ArrayList<Building> list = new ArrayList<>();
            for (int i = 0; i < N; ++i) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                int xl = scanner.nextInt();
                int yl = scanner.nextInt();
                int h = scanner.nextInt();
                pq.add(new Pair(x,i,"start"));
                pq.add(new Pair(x+xl,i,"end"));
                list.add(new Building(x,xl,y,h));
            }
            ArrayList<Integer> vline = new ArrayList<>();
            ArrayList<Integer> visibles = new ArrayList<>();
            while(!pq.isEmpty()){
                Pair p = pq.poll();
                if(p.type=="start"){
                    int i=0;
                    for(;i<vline.size();++i){
                        if(list.get(vline.get(i)).y>list.get(p.idx).y)  break;
                    }
                    vline.add(i,p.idx);
                }
                else{
                    vline.remove(vline.indexOf(p.idx));
                }
                int tmp=p.x;
                while(!pq.isEmpty() && (p=pq.peek()).x==tmp){
                    pq.poll();
                    if(p.type=="start"){
                        int i=0;
                        for(;i<vline.size();++i){
                            if(list.get(vline.get(i)).y>list.get(p.idx).y)  break;
                        }
                        vline.add(i,p.idx);
                    }
                    else{
                        vline.remove(vline.indexOf(p.idx));
                    }
                }
                int mh=0;
                for(int idx:vline){
                    if(list.get(idx).h>mh){
                        mh=list.get(idx).h;
                        if(!list.get(idx).visible) {
                            visibles.add(idx);
                            list.get(idx).visible = true;
                        }
                    }
                }
            }
            visibles.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    Building b1=list.get(o1), b2=list.get(o2);
                    if(b1.x0<b2.x0) return -1;
                    if(b1.x0>b2.x0) return 1;
                    if(b1.y<b2.y)   return -1;
                    if(b1.y>b2.y)   return 1;
                    return 0;
                }
            });
            System.out.format("For map #%d, the visible buildings are numbered as follows:",++cnt);
            System.out.println();
            for(int i=0;i<visibles.size()-1;++i){
                System.out.print(""+(visibles.get(i)+1)+" ");
            }
            System.out.println(visibles.get(visibles.size()-1)+1);
        }
    }
}
