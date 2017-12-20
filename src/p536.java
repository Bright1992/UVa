import java.util.Scanner;
import java.util.Stack;

public class p536 {
    static void postOrder(
            String preOrder, int ps, int pe,
            String midOrder, int ms, int me,
            Stack<Character> post
    ) {
        if (ps >= pe) return;
        char c = preOrder.charAt(ps);
        post.push(c);
        int p = ms;
        for (; p < me; ++p)
            if (midOrder.charAt(p) == c)
                break;
        assert p < me;
        postOrder(
                preOrder, ps + (p - ms) + 1, pe,
                midOrder, p + 1, me, post
        );
        postOrder(
                preOrder, ps + 1, ps + (p - ms) + 1,
                midOrder, ms, p, post
        );
    }

    public static void main(String[] argv) {
        Scanner scanner =new Scanner(System.in);
        while(scanner.hasNextLine()) {
            String l=scanner.nextLine();
            if(l.length()==0)   break;
            String[] line = l.split(" ");
            Stack<Character> post = new Stack<>();
            postOrder(line[0],0,line[0].length(),line[1],0,line[1].length(),post);
            while(!post.empty())
                System.out.print(post.pop());
            System.out.println();
        }
    }
}
