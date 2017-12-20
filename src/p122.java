import java.util.Scanner;
import java.util.ArrayList;

public class p122 {
    public static void main(String[] argv) {
        class Tree {
            Tree lchild, rchild;
            Integer val;

            public Tree(int val) {
                this.val = val;
            }

            public Tree() {
                this.val = null;
            }

            public void traverse() {
                Tree queue[] = new Tree[256];
                int front = 0, back = 0;
                queue[back++] = this;
                ArrayList<String> output = new ArrayList<>();
                while (front < back) {
                    Tree tmp = queue[front++];
                    if (tmp.val == null) {
                        System.out.println("not complete");
                        return;
                    }
                    output.add(tmp.val.toString());
                    if (tmp.lchild != null)
                        queue[back++] = tmp.lchild;
                    if (tmp.rchild != null)
                        queue[back++] = tmp.rchild;
                }
                for (int i = 0; i < output.size() - 1; ++i) {
                    System.out.print(output.get(i) + " ");
                }
                System.out.println(output.get(output.size() - 1));
            }
        }
        Tree tree = new Tree();
        Scanner scanner = new Scanner(System.in);
        boolean fault = false;
        while (scanner.hasNext()) {
            String node = scanner.next();
            if (fault) {
                tree = new Tree();
                if (!node.equals("()")) continue;
            }
            if (node.equals("()")) {
                if (fault) {
                    fault = false;
                    continue;
                }
                tree.traverse();
                tree = new Tree();
                continue;
            }
            node = node.substring(1, node.length() - 1);
            String[] as = node.split(",");
            if (as.length == 1) {
                if (tree.val != null) {
                    System.out.println("not complete");
                    fault = true;
                    continue;
                }
                tree.val = Integer.valueOf(as[0]);
                continue;
            }
            Tree tmp = tree;
            for (int i = 0; i < as[1].length(); ++i) {
                char c = as[1].charAt(i);
                if (c == 'L') {
                    if (tmp.lchild == null)
                        tmp.lchild = new Tree();
                    tmp = tmp.lchild;
                } else if (c == 'R') {
                    if (tmp.rchild == null)
                        tmp.rchild = new Tree();
                    tmp = tmp.rchild;
                } else {
                    System.err.println("Invalid char: " + c);
                }
            }
            if(tmp.val==null)
                tmp.val = Integer.valueOf(as[0]);
            else{
                System.out.println("not complete");
                fault = true;
                continue;
            }
        }
    }
}
