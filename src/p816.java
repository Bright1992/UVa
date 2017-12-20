
import java.util.*;

public class p816 {
    static class IllegalNodeNumber extends Exception{}
    static final int ENTRANCE = 1;
    static final int NORMAL = 0;
    static final int EXIT = 2;

    static class Node {
        public ArrayList<Edge> arcs = new ArrayList<>();
        public int type = NORMAL;
    }
    static class Pair{
        public int x,y;
        public Pair(int x,int y){
            this.x=x;this.y=y;
        }
    }

    static class Edge {
        public Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }

        public int from, to;
    }
    static final int SIZE=9*9*4+1;

    static Node[] graph = new Node[SIZE];

    static Pair idx2cor(int idx) {
        return new Pair(
                idx / 4 / 9 + 1,
                idx / 4 % 9 + 1
        );
    }

    static int cor2idx(int x, int y, char dir) throws IllegalNodeNumber {
        if(x<1||y<1)  throw new IllegalNodeNumber();
        return (x - 1) * 9 * 4 + (y - 1) * 4 + char2idx.get(dir);
    }

    static Map<Character, Integer> char2idx = new HashMap<>();

    static {
        char2idx.put('S', 0);
        char2idx.put('W', 1);
        char2idx.put('N', 2);
        char2idx.put('E', 3);
    }

    static char[] idx2char = {'S', 'W', 'N', 'E'};

    static void addEdge(int sx, int sy, char dir, char lfr) {
        int idx=0;
        if(lfr=='E') {
            idx=SIZE-1;
            lfr='F';
        }
        else
            try {
                idx = cor2idx(sx, sy, dir);
            }
            catch (IllegalNodeNumber e){
                e.printStackTrace(System.err);
            }
        int idir = char2idx.get(dir);
        switch (lfr) {
            case 'L':
                idir = (idir + 4 - 1) % 4;
                break;
            case 'R':
                idir = (idir + 1) % 4;
                break;
            default:
                break;
        }
        try {
            switch (idx2char[idir]) {
                case 'S':
                    graph[idx].arcs.add(new Edge(idx, cor2idx(sx + 1, sy, 'S')));
                    break;
                case 'W':
                    graph[idx].arcs.add(new Edge(idx, cor2idx(sx, sy - 1, 'W')));
                    break;
                case 'N':
                    graph[idx].arcs.add(new Edge(idx, cor2idx(sx - 1, sy, 'N')));
                    break;
                case 'E':
                    graph[idx].arcs.add(new Edge(idx, cor2idx(sx, sy + 1, 'E')));
                    break;
                default:
                    break;
            }
        }
        catch (IllegalNodeNumber e){
            e.printStackTrace(System.err);
        }
    }
    static int[] mark= new int[SIZE];

    static void BFS(int idx){
        LinkedList<Integer> q = new LinkedList<>();
        q.addLast(idx);
        while(!q.isEmpty()){
            idx=q.pollFirst();
            for(Edge e:graph[idx].arcs){
                if(mark[e.to]==-1){
                    mark[e.to]=idx;
                    q.addLast(e.to);
                }
                if(graph[e.to].type==EXIT)   return;
            }
        }
    }

    public static void main(String[] argv) throws IllegalNodeNumber{
        Scanner scanner = new Scanner(System.in);
        String name=null;
        while (!(name=scanner.nextLine()).equals("END")) {
            for(int i=0;i<SIZE;++i){
                mark[i]=-1;
                graph[i] = new Node();
            }
            int entx = scanner.nextInt(), enty = scanner.nextInt();
            String dir = scanner.next();
            int outx = scanner.nextInt(), outy = scanner.nextInt();
            scanner.nextLine();
            graph[SIZE-1]=new Node();
            graph[SIZE-1].type=ENTRANCE;
            addEdge(entx,enty,dir.charAt(0),'E');
            int idx = cor2idx(outx, outy, 'S');
            for (int i = 0; i < 4; ++i)
                graph[idx + i].type = EXIT;
            while (true) {
                String[] line = scanner.nextLine().split(" ");
                int x = Integer.valueOf(line[0]);
                if(x==0)    break;
                int y = Integer.valueOf(line[1]);
                int j = 2;
                while (!line[j].equals("*")) {
                    char d = line[j].charAt(0);
                    for (int k = 1; k < line[j].length(); ++k) {
                        addEdge(x,y,d,line[j].charAt(k));
                    }
                    j++;
                }
            }
            BFS(cor2idx(10,1,'S'));
            System.out.println(name);
            idx=cor2idx(outx,outy,'S');
            int i=0;
            for(;i<4;++i)
                if(mark[idx+i]!=-1) break;
            if(i==4) {
                System.out.println("  No Solution Possible");
                continue;
            }
            idx+=i;
            Stack<Integer> stack = new Stack<>();
            while(idx!=SIZE-1){
                stack.push(idx);
                idx=mark[idx];
            }
            stack.push(cor2idx(entx,enty,'S'));
            int cnt=0;
            while(!stack.empty()){
                Pair p = idx2cor(stack.pop());
                if(++cnt==1)
                    System.out.print(" ");
                System.out.print(" ");
                System.out.format("(%d,%d)",p.x,p.y);
                if(cnt==10){
                    System.out.println();
                    cnt=0;
                }
            }
            if(cnt!=0)  System.out.println();
        }
    }

}
