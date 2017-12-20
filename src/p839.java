import java.util.Scanner;

public class p839 {
    static Scanner scanner = new Scanner(System.in);
    static boolean bal=true;
    static int balance(){
        int wl=scanner.nextInt(), dl=scanner.nextInt(), wr=scanner.nextInt(), dr=scanner.nextInt();
        if(wl==0)
            wl=balance();
        if(wr==0)
            wr=balance();
        if(wl*dl!=wr*dr)
            bal=false;
        return wl+wr;
    }

    public static void main(String[] argv){
        int N= scanner.nextInt();
        while(N-->0){
            bal=true;
            balance();
            if(bal) System.out.println("YES");
            else    System.out.println("NO");
            if(N>0) System.out.println();
        }
    }
}
