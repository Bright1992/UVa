import java.util.Scanner;

public class p699 {
    static int leftmost=80, rightmost=80;
    static int piles[] = new int[161];
    static Scanner scanner = new Scanner(System.in);
    static void build(int mid){
        int l=scanner.nextInt();
        if(l!=-1){
            leftmost=Math.min(leftmost,mid-1);
            piles[mid-1]+=l;
            build(mid-1);
        }
        int r=scanner.nextInt();
        if(r!=-1){
            rightmost=Math.max(rightmost,mid+1);
            piles[mid+1]+=r;
            build(mid+1);
        }
    }
    public static void main(String[] argv){
        int m, cnt=0;
        while((m=scanner.nextInt())!=-1){
            for(int i=0;i<161;++i)
                piles[i]=0;
            piles[rightmost=leftmost=80]=m;
            build(rightmost);
            System.out.println("Case "+(++cnt)+":");
            for(int i=leftmost;i<rightmost;++i)
                System.out.print(""+piles[i]+" ");
            System.out.println(piles[rightmost]);
            System.out.println();
        }
    }
}
