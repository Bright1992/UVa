import java.util.Scanner;

public class p679 {
    public static void main(String[] argv){
        Scanner scanner = new Scanner(System.in);
        int N=scanner.nextInt();
        for(int i=0;i<N;++i){
            int d=scanner.nextInt(), n=scanner.nextInt();
            if(n>(int)Math.pow(2,d)-1)  n=n%((int)Math.pow(2,d));
            int res=1, k=n;
            for(int depth = d-1;depth>0;--depth){
                res=res*2+(1-(k%2));
                k=(k+1)/2;
            }
            System.out.println(res);
        }
    }
}
