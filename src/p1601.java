import java.util.*;

public class p1601 {
    static LinkedList<Integer> queue;
    static char[][] map;
    static int[][] blanks;
    static int[] deg;
    static int[][] id;
    static int[] finalPos, initPos;
    static byte[] dist;
    static final int[][] dir = new int[][]{
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}, {0, 0}
    };

    static int bfs() {
        for (int i = 0; i < (1 << 24); ++i)
            dist[i] = -1;
        queue = new LinkedList<Integer>();
        dist[pos2state(initPos)] = 0;
        int finalState = pos2state(finalPos);
        if (pos2state(initPos) == finalState) return 0;
        queue.addLast(pos2state(initPos));
        int n = initPos.length;
        int[] pos = new int[n];
        int[] pos2 = new int[n];
        while (!queue.isEmpty()) {
            int s = queue.pollFirst();
            for(int i=0;i<n;++i)    pos2[i]=0;
            state2pos(s, pos);
            do {
                int ns = updateState(pos, pos2);
                if (ns != 0 && dist[ns] == -1) {
                    if (ns == finalState) return dist[s] + 1;
                    dist[ns] = (byte) (dist[s] + 1);
                    queue.addLast(ns);
                }
            }
            while (nextPos(pos2, pos) != 0);
        }
        return -1;
    }

    static int invoke = 0;
    static int[] nextPos;

    static int updateState(int[] pos, int[] pos2) {
        invoke++;
        int n = pos2.length;
        for (int i = 0; i < n; ++i) {
            nextPos[i] = blanks[pos[i]][pos2[i]];
        }
        if (!valid(pos, nextPos))
            return 0;
        return pos2state(nextPos);
    }

    static boolean valid(int[] pos, int[] nextPos) {
        boolean changed = false;
        for (int i = 0; i < pos.length; ++i) {
            for (int j = 0; j < nextPos.length; ++j) {
                if (i == j) {
                    if (pos[i] != nextPos[i])
                        changed = true;
                } else {
                    if (pos[i] == nextPos[j] && pos[j] == nextPos[i])
                        return false;
                    if (pos[i] == pos[j])
                        return false;
                }
            }
        }
        return changed;
    }

    static int nextPos(int[] pos2, int[] pos) {
        int len = pos2.length;
        for (int i = len - 1; i >= 0; --i) {
            if (pos2[i] < deg[pos[i]] - 1) {
                pos2[i]++;
                return 1;
            } else
                pos2[i] = 0;
        }
        return 0;
    }

    static int cor2int(int r, int c) {
        return (r << 4) + c;
    }

    static int pos2state(int[] pos) {
        int ret = 0;
        for (int i = 0; i < pos.length; ++i) {
            ret += (pos[i] << (i * 8));
        }
        return ret;
    }

    static int[] state2pos(int state, int[] pos) {
        int n = pos.length;
        for (int i = 0; i < n; ++i) {
            pos[i] = (state & 0xFF);
            state >>= 8;
        }
        return pos;
    }

    public static void main(String[] argv) {
        //test();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            int c = scanner.nextInt(), r = scanner.nextInt(), n = scanner.nextInt();
            if (c == 0) return;
            map = new char[r][c];
            finalPos = new int[n];
            initPos = new int[n];
            blanks = new int[1 << 8][5];
            deg = new int[1 << 8];
            id = new int[r][c];
            dist = new byte[1 << 24];
            invoke = 0;
            System.gc();
            scanner.nextLine();
            for (int i = 0; i < r; ++i) {
                String line = scanner.nextLine();
                for (int j = 0; j < c; ++j) {
                    char ch = line.charAt(j);
                    if (ch == '#' || ch == ' ')
                        map[i][j] = ch;
                    else if (ch >= 'a' && ch < (char) ('a' + n)) {
                        initPos[ch - 'a'] = cor2int(i, j);
                        map[i][j] = ' ';
                    } else if (ch >= 'A' && ch < (char) ('A' + n)) {
                        finalPos[ch - 'A'] = cor2int(i, j);
                        map[i][j] = ' ';
                    }
                }
            }
            for (int i = 0; i < r; ++i)
                for (int j = 0; j < c; ++j) {
                    id[i][j] = cor2int(i, j);
                    if (map[i][j] == ' ') {
                        for (int k = 0; k < 5; ++k) {
                            int r1 = i + dir[k][0], c1 = j + dir[k][1];
                            if (r1 < 0 || r1 > map.length || c1 < 0 || c1 > map[0].length || map[r1][c1] == '#')
                                continue;
                            else
                                blanks[id[i][j]][deg[id[i][j]]++] = cor2int(r1, c1);
                        }
                    }
                }
            nextPos = new int[n];
            System.out.println(bfs()&0xFF);
//            System.out.println("Invocation:"+invoke);
        }
    }
}
