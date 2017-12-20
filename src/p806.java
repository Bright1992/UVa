import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class p806 {
    static char[][] img;
    static Scanner scanner;

    static void rfpath2img(int imgSize){
        img=new char[imgSize][imgSize];
        for(int i=0;i<imgSize;++i)
            for(int j=0;j<imgSize;++j)
                img[i][j]='.';
        int rfpath;
        while(scanner.hasNextInt()&&(rfpath=scanner.nextInt())!=-1)
            do_rfpath2img(rfpath,0,0,imgSize);
    }

    static void do_rfpath2img(int rfpath, int r, int c, int w){
        if(rfpath==-1)  return;
        if(rfpath==0){
            for(int i=r;i<r+w;++i)
                for(int j=c;j<c+w;++j)
                    img[i][j]='*';
            return;
        }
        w/=2;
        switch(rfpath%5){
            case 1:break;
            case 2:c+=w;break;
            case 3:r+=w;break;
            case 4:c+=w;r+=w;break;
            default:System.out.println("Unexpected 0");
        }
        if(rfpath>=5){
            do_rfpath2img(rfpath/5, r, c, w);
        }
        else{
            for(int i=r;i<r+w;++i)
                for(int j=c;j<c+w;++j)
                    img[i][j]='*';
        }
    }

    static ArrayList<Integer> img2rfseq(int imgSize){
        img=new char[imgSize][];
        scanner.nextLine();
        for(int i=0;i<imgSize;++i){
            String line=scanner.nextLine();
            img[i]=line.toCharArray();
        }
        ArrayList<Integer> ret = new ArrayList<>();
        do_img2rfseq(0,0,imgSize,0,ret);
        return ret;
    }
    static void do_img2rfseq(int r, int c, int w, int rfpath, ArrayList<Integer> array) {
        int isBlack[] = new int[4];
        int s[][] = {{0, 0}, {0, w / 2}, {w / 2, 0}, {w / 2, w / 2}};
        int cnt = 0;
        int base=1,tmp=rfpath;
        while(tmp>0){
            base*=5;tmp/=5;
        }
        for (int i = 0; i < 4; ++i){
            switch (judgeColor(r + s[i][0], c+s[i][1], w / 2)) {
                case 0: //white
                    break;
                case 1: //black
                    isBlack[i] = 1;
                    cnt++;
                    break;
                case 2: //white&black
                    do_img2rfseq(r+s[i][0], c+s[i][1], w / 2, rfpath + base*(i+1), array);
                    break;
            }
        }
        if(cnt==4)
            array.add(rfpath);
        else
            for(int i=0;i<4;++i)
                if(isBlack[i]==1)
                    array.add(rfpath+base*(i+1));

    }

    /*
    pure white:0
    pure black:1
    white & black:2
     */
    static int judgeColor(int r,int c,int w){
        if(w==0) {
            if (img[r][c] == '1') return 1;
            else return 0;
        }
        boolean hasBlack=false,hasWhite=false;
        for(int i=r;i<r+w;++i){
            for(int j=c;j<c+w;++j) {
                if (img[i][j]=='1')
                    hasBlack=true;
                else
                    hasWhite=true;
                if(hasBlack&&hasWhite)  return 2;
            }
        }
        if(!hasBlack)   return 0;
        else    return 1;
    }

    public static void main(String[] argv){
        scanner = new Scanner(System.in);
        int cnt=0;
        while(scanner.hasNextInt()){
//            System.out.println("WTF???");
            int n=scanner.nextInt();
            if(n==0)    return;
            if(cnt>0)   System.out.println();
            if(n>0){
                ArrayList<Integer> arr=img2rfseq(n);
                Collections.sort(arr);
                System.out.print("Image "+(++cnt));
                for(int i=0;i<arr.size();++i) {
                    if(i%12>0)  System.out.print(" ");
                    if(i%12==0)
                        System.out.println();
                    System.out.print(arr.get(i));
                }
                System.out.println();
                System.out.println("Total number of black nodes = "+arr.size());
            }
            else{
                rfpath2img(-n);
                System.out.println("Image "+(++cnt));
                for(int i=0;i<-n;++i) {
                    for (int j = 0; j < -n; ++j)
                        System.out.print(img[i][j]);
                    System.out.println();
                }
            }
        }
    }
}
