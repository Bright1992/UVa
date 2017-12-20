import java.util.LinkedList;
import java.util.Scanner;

public class p12675 {

    public static void swap(int[] left, int[] right, int X, int Y){
        if(left[X]!=Y&&right[X]!=Y) {
            int XL = left[X], XR = right[X], YL = left[Y], YR = right[Y];
            left[X] = left[Y];
            right[X] = right[Y];
            right[XL] = Y;
            left[XR] = Y;
            right[YL] = X;
            left[YR] = X;
            left[Y] = XL;
            right[Y] = XR;
        }
        else{
            if(left[X]==Y){
                int tmp=X;X=Y;Y=tmp;
            }
            left[Y]=left[X];
            right[left[X]]=Y;
            right[X]=right[Y];
            left[right[Y]]=X;
            left[X]=Y;
            right[Y]=X;
        }
    }

    public static void move(int[] left, int[] right, int src, int L){
        int R=right[L];
        if(R==src) {
            swap(left,right,src,L);
            return;
        }
        left[right[src]]=left[src];
        right[left[src]]=right[src];
        right[L]=src;left[src]=L;
        left[R]=src;right[src]=R;
    }

    public static void test(){
        int left[]={4,0,1,2,3};
        int right[]={1,2,3,4,0};
        move(left,right,4,1);
        int pos=0;
        for(int i=1;i<=6;++i)
            System.out.print(""+(pos=right[pos])+" ");
        System.out.println();
    }

    public static void main(String[] argv){
//        test.test();
        Scanner scanner = new Scanner(System.in);
        int cnt=0;
        while(scanner.hasNextInt()){
           int n = scanner.nextInt();
           int m = scanner.nextInt();
           cnt++;
           //boxes[0] is head node
           int[] boxes=new int[n+1],left=new int[n+1],right  = new int[n+1];
           for(int i=0;i<n+1;++i){
               boxes[i]=i;
               left[i]=(i+n)%(n+1);
               right[i]=(i+1)%(n+1);
           }
           boolean inv=false;
           while (m-->0){
//               int pos=0;
//               for(int i=0;i<n;++i)
//                   if(inv)
//                       System.out.print(""+(pos=left[pos])+" ");
//                   else
//                       System.out.print(""+(pos=right[pos])+" ");
//               System.out.println();

               int op=scanner.nextInt();
               if(op==4){
                   inv=!inv;
//                   System.out.println("cmd: 4");
                   continue;
               }
               int X=scanner.nextInt();
               int Y=scanner.nextInt();

//               System.out.println("cmd: "+op+" "+X+" "+Y);

               switch (op){
                   case 1:
                       if(inv){
                           if(X==right[Y])  continue;
                           move(left, right, X,Y);
                           continue;
                       }

                       if(X==left[Y])   continue;
                       move(left, right,X,left[Y]);

                       break;
                   case 2:
                       if(inv){
                           if(X==left[Y])   continue;
                           move(left, right,X,left[Y]);
                           continue;
                       }

                       if(X==right[Y])  continue;
                       move(left, right,X,Y);

                       break;
                   case 3:
                       swap(left, right,X,Y);
                       break;
                   case 4:
                       inv=!inv;
                       continue;
               }


           }
           int pos=0;
           long res=0;
           for(int i=1;i<=n;++i){
               if(inv)
                   pos = left[pos];
               else
                   pos=right[pos];
               if(i%2==1)   res+=pos;
           }
           System.out.println("Case "+cnt+": "+res);
        }
    }
}
