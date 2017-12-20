import java.util.ArrayList;
import java.util.Scanner;

public class p804 {
    enum TYPE {PLACE, TRANS}

    static class Node {
        public Node(TYPE type, Object... args) {
            if (type == TYPE.PLACE) {
                assert args.length == 1 && args[0].getClass() == Integer.class;
                tokens = (Integer) args[0];
            } else {
                assert args.length == 0;
                input = new ArrayList<>();
                output = new ArrayList<>();
            }
        }

        int tokens = -1;
        ArrayList<Integer> input = null, output = null;

        public void add(int arc) {
            assert tokens == -1;
            if (arc < 0)
                input.add(0 - arc);
            else
                output.add(arc);
        }
    }

    static Node[] Places = null;
    static Node[] Trans = null;

    public static void main(String[] argv) {
        Scanner scanner = new Scanner(System.in);
        int cnt = 0;
        while (scanner.hasNextInt()) {
            int p = scanner.nextInt();
            if (p == 0) return;
            cnt++;
            Places = new Node[p + 1];
            for (int i = 1; i <= p; ++i)
                Places[i] = new Node(TYPE.PLACE, scanner.nextInt());
            int t = scanner.nextInt();
            Trans = new Node[t];
            for (int i = 0; i < t; ++i) {
                Trans[i] = new Node(TYPE.TRANS);
                int arc = 0;
                while ((arc = scanner.nextInt()) != 0)
                    Trans[i].add(arc);
            }
            int rounds = scanner.nextInt();
            int r;
            for (r = 0; r < rounds; ++r) {
                int i;
                for (i = 0; i < t; ++i) {
                    int j;
                    for (j = 0; j < Trans[i].input.size(); ++j) {
                        if (--Places[Trans[i].input.get(j)].tokens < 0) {
                            for (int k = 0; k <= j; ++k)
                                ++Places[Trans[i].input.get(j)].tokens;
                            break;
                        }
                    }
                    if (j == Trans[i].input.size()) {
                        //fire
                        for (int k = 0; k < Trans[i].output.size(); ++k)
                            Places[Trans[i].output.get(k)].tokens++;
                        break;
                    }
                }
                if (i == t) {
                    System.out.println("Case " + cnt + ": dead after " + r + " transitions");
                    break;
                }
            }
            if (r == rounds) {
                System.out.println("Case " + cnt + ": still live after " + rounds + " transitions");
            }
            System.out.print("Places with tokens: ");
            int cnt2 = 0;
            for (int i = 1; i <= p; ++i) {
                if (Places[i].tokens > 0) {
                    if (cnt2++ > 0)
                        System.out.print(" ");
                    System.out.format("%d (%d)", i, Places[i].tokens);
                }
            }
            System.out.println();
            System.out.println();
        }
    }
}
