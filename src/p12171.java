import java.util.Arrays;
import java.util.Scanner;

public class p12171 {
    static int map[][][];

    static class ScatteredCors implements Comparable<ScatteredCors> {
        public int originCor, objNum;

        public ScatteredCors(int oc, int on) {
            originCor = oc;
            objNum = on;
        }

        @Override
        public int compareTo(ScatteredCors o) {
            return Integer.compare(originCor, o.originCor);
        }
    }

    static ScatteredCors SC[][];

    static class Obj {
        public static int cnt = 1;
        public int x0, x1, y0, y1, z0, z1;
        public int[] sc = new int[6];

        public Obj(int x, int y, int z, int dx, int dy, int dz) {
            x0 = x;
            x1 = x + dx;
            y0 = y;
            y1 = y + dy;
            z0 = z;
            z1 = z + dz;
            SC[0][cnt] = new ScatteredCors(x, cnt);
            SC[1][cnt] = new ScatteredCors(y, cnt);
            SC[2][cnt] = new ScatteredCors(z, cnt);
            cnt++;
            SC[0][cnt] = new ScatteredCors(x1, cnt);
            SC[1][cnt] = new ScatteredCors(y1, cnt);
            SC[2][cnt] = new ScatteredCors(z1, cnt);
            cnt++;
        }
    }

    static Obj objs[];
    static int vis[][][];
    static int volume, area;

    static void init(int size) {
        map = new int[size + 2][size + 2][size + 2];
        Obj.cnt = 1;
        objs = new Obj[size / 2];
        SC = new ScatteredCors[3][size + 2];
        vis = new int[size + 2][size + 2][size + 2];
        volume = area = 0;
    }

    static void getSC() {
        for (int i = 0; i < 3; ++i) {
            Arrays.sort(SC[i]);
            for (int j = 1; j < SC[i].length - 1; ++j) {
                objs[(SC[i][j].objNum - 1) / 2].sc[(SC[i][j].objNum - 1) % 2 + i * 2] = j;
            }
        }
    }

    static void fillMap() {
        int len = map[0][0].length;
        for (Obj o : objs) {
            int sc[] = Arrays.copyOf(o.sc, o.sc.length);
            for (int i = 0; i < 3; ++i) {
                while (sc[i * 2 + 1] < len - 2 && SC[i][sc[i * 2 + 1]].originCor == SC[i][sc[i * 2 + 1] - 1].originCor)
                    sc[i * 2 + 1]++;
                while (sc[i * 2] > 1 && SC[i][sc[i * 2]].originCor == SC[i][sc[i * 2] - 1].originCor)
                    sc[i * 2]--;
            }
            for (int x = sc[0]; x < sc[1]; ++x)
                for (int y = sc[2]; y < sc[3]; ++y)
                    for (int z = sc[4]; z < sc[5]; ++z)
                        map[x][y][z] = 1;
        }
    }

    static boolean valid(int x) {
        return x >= 0 && x < map[0][0].length - 1;
    }

    static void floodfill() {
        floodfill(0, 0, 0);
        volume = 1001 * 1001 * 1001 - volume;
    }

    static void floodfill(int sx, int sy, int sz) {
        if (vis[sx][sy][sz] == 1 || map[sx][sy][sz] == 1) return;
        vis[sx][sy][sz] = 1;
        int len = map[0][0].length;

//        if (sx != len-1 && sy != len-1 && sz != len-1)
//        System.out.println(""+sx+" "+sy+" "+sz+" "+SC[0][sx+1].originCor+" "+SC[1][sy+1].originCor+" "+SC[2][sz+1].originCor);
        volume += ((SC[0][sx + 1].originCor - SC[0][sx].originCor) *
                (SC[1][sy + 1].originCor - SC[1][sy].originCor) *
                (SC[2][sz + 1].originCor - SC[2][sz].originCor));
        if (valid(sx - 1)) {
            if (map[sx - 1][sy][sz] == 0)
                floodfill(sx - 1, sy, sz);
            else
                area+=((SC[1][sy+1].originCor-SC[1][sy].originCor)*(SC[2][sz+1].originCor-SC[2][sz].originCor));
        }
        if (valid(sx + 1)) {
            if(map[sx+1][sy][sz]==0)
                floodfill(sx + 1, sy, sz);
            else
                area+=((SC[1][sy+1].originCor-SC[1][sy].originCor)*(SC[2][sz+1].originCor-SC[2][sz].originCor));
        }
        if (valid(sy - 1)) {
            if(map[sx][sy-1][sz]==0)
                floodfill(sx, sy - 1, sz);
            else
                area+=((SC[0][sx+1].originCor-SC[0][sx].originCor)*(SC[2][sz+1].originCor-SC[2][sz].originCor));
        }
        if (valid(sy + 1)){
            if(map[sx][sy+1][sz]==0)
                floodfill(sx, sy + 1, sz);
            else
                area+=((SC[0][sx+1].originCor-SC[0][sx].originCor)*(SC[2][sz+1].originCor-SC[2][sz].originCor));
        }
        if (valid(sz - 1)){
            if(map[sx][sy][sz-1]==0)
                floodfill(sx, sy, sz - 1);
            else
                area+=((SC[0][sx+1].originCor-SC[0][sx].originCor)*(SC[1][sy+1].originCor-SC[1][sy].originCor));
        }
        if (valid(sz + 1)){
            if(map[sx][sy][sz+1]==0)
                floodfill(sx, sy, sz + 1);
            else
                area+=((SC[0][sx+1].originCor-SC[0][sx].originCor)*(SC[1][sy+1].originCor-SC[1][sy].originCor));
        }
    }

    public static void main(String[] argv) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        while (N-- > 0) {
            int n = scanner.nextInt();
            int size = n * 2;
            init(size);
            for (int i = 0; i < n; ++i) {
                objs[i] = new Obj(
                        scanner.nextInt(),
                        scanner.nextInt(),
                        scanner.nextInt(),
                        scanner.nextInt(),
                        scanner.nextInt(),
                        scanner.nextInt()
                );
            }
            for (int i = 0; i < 3; ++i) {
                SC[i][SC[i].length - 1] = new ScatteredCors(1001, 0);
                SC[i][0] = new ScatteredCors(0, 0);
            }
            getSC();
            fillMap();
            floodfill();
            System.out.println("" + area + " " + volume);
        }
    }

}
