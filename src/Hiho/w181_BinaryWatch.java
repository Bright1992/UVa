package Hiho;

import java.util.Scanner;

public class w181_BinaryWatch {
    static int ones(int n) {
        int ret = 0;
        while (n > 0) {
            if ((n & 1) == 1) ret++;
            n >>= 1;
        }
        return ret;
    }

    public static void main(String[] argv) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            int x = scanner.nextInt();
            for(int h=0;h<24;++h) {
                if (ones(h) > x) continue;
                for(int m=0;m<60;++m){
                    if(ones(h)+ones(m)==x)
                        System.out.format("%02d:%02d\n",h,m);
                }
            }
        }
    }
}
