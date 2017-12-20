package test;

import java.util.Scanner;

public class IOTimeTest {
    static void in(){
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextInt())
            System.out.println(scanner.nextInt());
    }
    public static void main(String[] argv){
        in();
    }
}
