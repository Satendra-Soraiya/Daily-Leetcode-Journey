import java.util.*;

class Solution {
    int n, T;
    int[] bt, ti, to2, dp, m;
    int[][] up;

    void put(int i, int v) {
        for (; i <= n + 1; i += i & -i) bt[i] ^= v;
    }

    int get(int i) {
        int r = 0;
        for (; i > 0; i -= i & -i) r ^= bt[i];
        return r;
    }

    void flip(int x, int d) {
        put(ti[x], d);
        put(to2[x] + 1, d);
    }

    int lc(int a, int b) {
        if (dp[a] < dp[b]) {
            int t = a;
            a = b;
            b = t;
        }
        int df = dp[a] - dp[b];
        for (int j = 0; j < 17; j++) {
            if (((df >> j) & 1) == 1) a = up[j][a];
        }
        if (a == b) return a;
        for (int j = 16; j >= 0; j--) {
            if (up[j][a] != up[j][b]) {
                a = up[j][a];
                b = up[j][b];
            }
        }
        return up[0][a];
    }

    public List<Boolean> palindromePath(int N, int[][] edges, String s, String[] queries) {
        n = N;
        int[] hd = new int[n];
        Arrays.fill(hd, -1);
        int[] nx = new int[2 * n];
        int[] ds = new int[2 * n];
        int c = 0;
        for (int[] e : edges) {
            ds[c] = e[1];
            nx[c] = hd[e[0]];
            hd[e[0]] = c++;
            ds[c] = e[0];
            nx[c] = hd[e[1]];
            hd[e[1]] = c++;
        }
        bt = new int[n + 3];
        ti = new int[n];
        to2 = new int[n];
        dp = new int[n];
        m = new int[n];
        up = new int[17][n];
        int[] cur = hd.clone();
        int[] st = new int[n];
        int sp = 0;
        T = 1;
        st[sp++] = 0;
        ti[0] = T++;
        while (sp > 0) {
            int v = st[sp - 1];
            int e = cur[v];
            if (e == -1) {
                to2[v] = T - 1;
                sp--;
            } else {
                cur[v] = nx[e];
                int w = ds[e];
                if (w == up[0][v]) continue;
                up[0][w] = v;
                dp[w] = dp[v] + 1;
                ti[w] = T++;
                st[sp++] = w;
            }
        }
        for (int j = 1; j < 17; j++) {
            for (int v = 0; v < n; v++) {
                up[j][v] = up[j - 1][up[j - 1][v]];
            }
        }
        for (int i = 0; i < n; i++) {
            m[i] = 1 << (s.charAt(i) - 'a');
            flip(i, m[i]);
        }
        List<Boolean> res = new ArrayList<>();
        for (String q : queries) {
            String[] p = q.split(" ");
            if (p[0].equals("update")) {
                int u = Integer.parseInt(p[1]);
                int nm = 1 << (p[2].charAt(0) - 'a');
                if (nm != m[u]) {
                    flip(u, m[u] ^ nm);
                    m[u] = nm;
                }
            } else {
                int a = Integer.parseInt(p[1]);
                int b = Integer.parseInt(p[2]);
                int l = lc(a, b);
                int x = get(ti[a]) ^ get(ti[b]) ^ m[l];
                res.add(Integer.bitCount(x) <= 1);
            }
        }
        return res;
    }
}