package Hiho;

import java.util.Scanner;

public class w180_NatureNumbers {
    static long A[] = new long[18];

    public static void main(String[] argv){
        A[0]=1;
        long base=1;
        for(int i=1;i<18;++i) {
            base *= 10;
            A[i] = (base - base / 10) * i + A[i - 1];
        }
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLong()){
            long n=scanner.nextLong();
            if(n==0){
                System.out.println(0);
                continue;
            }
            long l=0,h=n;
            while(l<=h) {
                long m = (l + h) / 2;
                long num=0;
                int cnt=0;
                if(m>0) {
                    cnt = getLength(m) - 1;
                    base = getBase(cnt + 1);
                    num = A[cnt] + (m - base) * (cnt + 1);
                }
                if(num<=n&&num+cnt>=n){
                    System.out.println(getDigit(m,(int)(n-num+1)));
                    break;
                }
                if(num>n)   h=m-1;
                else    l=m+1;
            }
        }
    }
    static int getDigit(long n, int d){
        int len=getLength(n);
        long base=1;
        while(len-->d)
            base*=10;
        return (int)((n/base)%10);
    }

    static int getLength(long n){
        int len=0;
        while(n>0){
            n/=10;len++;
        }
        return len;
    }

    static long getBase(int len){
        long base=1;
        while(--len>0)  base*=10;
        return base;
    }
}
