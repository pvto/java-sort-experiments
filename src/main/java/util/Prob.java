package util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pvto https://github.com/pvto
 */
public final class Prob {

    public static long prod(int n)
    {
        return prodCache[n];
    }
    
    public static long npr(int n, int r)
    {
        long res = 1L;
        for(int i = n; i >= r; i--)
            res *= (long)i;
        return res;
    }

    public static long ncr(int n, int r)
    {
        int m = Math.max(r, n-r);
        r = n - m;
        long res = 1L;
        int j = 2;
        for(int i = n; i > m; i--)
        {
            res *= (long)i;
            if (j <= r && res % j == 0)
                res /= j++;
        }
        while(j <= r)
            res /= j++;
        return res;
    }

    public static double binm(int n, double p, int x)
    {
        checkp(p);
        if (x < 0 || x > n)
            throw new IllegalArgumentException("binm("+n+","+p+") [0..+"+n+"] not defined for " + x);
        if (n < 60)
        {
            return ncr(n,x) * Math.pow(p, x) * Math.pow(1.0 - p, n - x);
        }
        double ret = 1.0;
        int j = n - x, k = x;
        for(int i = x + 1; i <= n; i++)
        {
            ret *= i;
            while(k > 0 && ret > 1.0)
            {
                ret *= p;
                k--;
            }    
            while (j > 0 && ret > 1.0)
            {
                ret /= j--;
                ret *= (1.0 - p);
            }
        }
        while(j > 0)
        {
            ret /= j--;
            ret *= (1.0 - p);
        }
        while(k > 0)
        {
            ret *= p;
            k--;
        }

        return ret;
    }

    public static double binmCuml(int n, double p, int x)
    {
        checkp(p);
        double res = 0;
        int y = (n - x < x) ? n - x - 1 : x;
        for(int i = 0; i <= y; i++)
            res += binm(n, p, i);
        return (y < x) ? 1.0 - res : res;
    }

    public static double binmE(int n, double p)
    {
        checkp(p);
        return n * p;
    }
    
    public static double binmVar(int n, double p)
    {
        checkp(p);
        return n * p * (1.0 - p);
    }
    
    public static int binmRnd(int n, double p, double rndFromU)
    {
        checkp(p); checkp(rndFromU);
        double[] cached = getBinmCumulCache(n, p);
        int 
                i = cached.length / 2,
                left = 1, right = cached.length - 1
                ;
        for(;;)
        {
            if (cached[i] < rndFromU) { left = i; }
            else if (cached[i] > rndFromU) { right = i; }
            i = (left + right) >> 1;
            if (i == left || i == right) break;
        }
        while(i < cached.length && cached[i] < rndFromU) i++;
        return (int)cached[0] + i - 1;
    }
    
    
    
    
    static private void checkp(double p)
    {
        if (p < 0 || p > 1.0)
            throw new IllegalArgumentException("outside of [0..1]");
    }
    
    static private final long[] prodCache = new long[40];
    static
    {
        long x = prodCache[0] = 1L;
        for(int i = 1; i < prodCache.length; i++)
            prodCache[i] = prodCache[i - 1] * (long)i;
    }

    private static Map binmcache = new HashMap();
    private static double[] getBinmCache(int n, double p)
    {
        double key = n+p/2.0;
        double[] cached = (double[])binmcache.get(key);
        if (cached != null)
            return cached;
        return binmCache(n, p);
    }
    private static double[] getBinmCumulCache(int n, double p)
    {
        double key = -(n+p/2.0);
        double[] cached = (double[])binmcache.get(key);
        if (cached != null)
            return cached;
        binmCache(n, p);
        return (double[])binmcache.get(key);
    }
    private static double[] binmCache(int n, double p)
    {
        double key = n+p/2.0;
        double[] cached = new double[n+1];
        for(int i = 0; i <= (n >> 1); i++)
            cached[i] = cached[cached.length - i - 1] = binm(n, p, i);
        int i = 0;
        while(cached[i] == 0) i++;
        double[] tmp = new double[cached.length - i + 1];
        tmp[0] = i;
        for (int j = 0; j < tmp.length - 1; j++) {
            tmp[j + 1] = cached[i + j];
        }
        cached = tmp;
        binmcache.put(key, cached);
//        System.out.println(Arrays.toString(cached));
        double[] cumul = new double[cached.length];
        cumul[0] = cached[0];
        double d = 0;
        for(i = 1; i < cached.length; i++)
        {
            d += cached[i];
            cumul[i] = d;
        }
//        System.out.println(Arrays.toString(cumul));
        binmcache.put(-key, cumul);
        return cached;
    }
    
    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        System.out.println(binm(50, 0.5, 25));
        System.out.println(binm(60, 0.5, 30));
        System.out.println(binm(1000, 0.5, 500));
        System.out.println(binm(10000, 0.5, 5000));
        System.out.println(Arrays.toString(getBinmCumulCache(3, 0.5)));
        System.out.println(Arrays.toString(getBinmCumulCache(4, 0.5)));
        System.out.println(Arrays.toString(getBinmCumulCache(60, 0.5)));
        System.out.println(Arrays.toString(getBinmCumulCache(10000, 0.5)));
//        System.out.println(System.currentTimeMillis() - l);
    }

}
