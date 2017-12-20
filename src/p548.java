import java.util.Scanner;

public class p548 {
    static int pathval=0x7FFFFFFF, nodeidx=0;
    static void rebuild(int[] post, int start1, int end1, int[] mid, int start2, int end2, int pv){
        int root=post[end1-1];
        pv+=root;
        int n=start2;
        for(;n<end2;++n)
            if(mid[n]==root)    break;

        int cnt=0;
        if(n-start2>=1) {
            rebuild(post, start1, start1 + (n - start2), mid, start2, n, pv);
            cnt++;
        }
        if(end2-n>1) {
            rebuild(post, start1 + (n - start2), end1 - 1, mid, n + 1, end2, pv);
            cnt++;
        }
        if(cnt==0) { //leaf
            if(pathval>pv){
                pathval=pv;
                nodeidx=post[start1];
            }
            if(pathval==pv)
                nodeidx=Math.min(nodeidx,post[start1]);
//            System.out.println(""+pathval+" "+nodeidx);
        }
    }

    public static void main(String[] argv){
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            pathval=0x7FFFFFFF; nodeidx=0;
            String[] line1 = scanner.nextLine().split(" ");
            String[] line2 = scanner.nextLine().split(" ");
            int mid[] = new int[line1.length], post[] = new int[line1.length];
            for(int i=0;i<line1.length;++i){
                mid[i]=Integer.valueOf(line1[i]);
                post[i] = Integer.valueOf(line2[i]);
            }
            rebuild(post,0,mid.length,mid,0,mid.length,0);
            System.out.println(nodeidx);
        }
    }
}
