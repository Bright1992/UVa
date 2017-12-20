import com.sun.xml.internal.txw2.output.DataWriter;

import java.io.*;
import java.util.Scanner;

public class p524 {
    static int[] isPrime = new int[16*2+1];
    static void genPrime(){
        for(int i=2;i<16*2+1;++i)   isPrime[i]=1;
        isPrime[0]=isPrime[1]=0;
        for(int i=2;i<=10;++i)
            for(int j=i;j*i<16*2+1;++j)
                isPrime[i*j]=0;
    }
    static int[] circle,mark;
    static void dfs(int n, int cur){
        if(cur==n+1 && isPrime[circle[n]+1]==1){
            for(int i=1;i<=n;++i) {
                if (i > 1) System.out.print(" ");
                System.out.print(circle[i]);
            }
            System.out.println();
        }
        for(int i=2;i<=n;++i){
            if(mark[i]==0 && isPrime[i+circle[cur-1]]==1) {
                circle[cur]=i;
                mark[i] = 1;
                dfs(n, cur + 1);
                mark[i] = 0;
            }
        }
    }
    public static void main(String[] argv){
        int cnt=0;
        Scanner scanner = new Scanner(System.in);
        genPrime();
        while(scanner.hasNextInt()){
            if(cnt>0)   System.out.println();
            int n=scanner.nextInt();
            circle=new int[n+1];mark=new int[n+1];
            circle[1]=1;
            System.out.println("Case "+(++cnt)+":");
            dfs(n,2);
        }
    }
}
