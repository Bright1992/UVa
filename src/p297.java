import java.util.Scanner;

public class p297 {
    static int idx1,idx2;
    static int build(String t1,int d1,String t2,int d2){
        char c1=t1.charAt(idx1);
        char c2=t2.charAt(idx2);
        if(c1=='p'&&c2=='p'){
            idx1++;idx2++;
            return build(t1,d1+1,t2,d2+1)
                    +build(t1,d1+1,t2,d2+1)
                    +build(t1,d1+1,t2,d2+1)
                    +build(t1,d1+1,t2,d2+1);
        }
        if(c1=='p'){
            idx1++;
            int ret=build(t1,d1+1,t2,d2)
                    +build(t1,d1+1,t2,d2)
                    +build(t1,d1+1,t2,d2)
                    +build(t1,d1+1,t2,d2);
            idx2++;
            if(c2=='f'&&d2==d1) ret=1<<(2*(5-d2));
            return ret;
        }
        if(c2=='p'){
            idx2++;
            int ret=build(t1,d1,t2,d2+1)
                    +build(t1,d1,t2,d2+1)
                    +build(t1,d1,t2,d2+1)
                    +build(t1,d1,t2,d2+1);
            idx1++;
            if(c1=='f'&&d1==d2) ret=1<<(2*(5-d1));
            return ret;
        }
        if(d1>=d2)   idx1++;
        if(d2>=d1)   idx2++;
        if(c1=='f'&&d1>=d2) return 1<<(2*(5-d1));
        if(c2=='f'&&d2>=d1) return 1<<(2*(5-d2));
        return 0;
    }

    public static void main(String[] argv){
        Scanner scanner = new Scanner(System.in);
        int N=scanner.nextInt();
        scanner.nextLine();
        while(N-->0){
            String t1=scanner.nextLine(),t2=scanner.nextLine();
            idx1=idx2=0;
            System.out.println("There are "+build(t1,0,t2,0)+" black pixels.");
//            System.out.println("idx1="+idx1+" idx2="+idx2);
        }
    }
}
