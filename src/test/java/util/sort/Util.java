package util.sort;

import util.Prob;

/**
 *
 * @author Paavo Toivanen https://github.com/pvto
 */
public class Util {

    

    public static void similarize(int[] a , double similarityFactor)
    {
        for(int i = 0; i < a.length * similarityFactor; i++)
        {
            int 
                    x = (int) (Math.random() * a.length),
                    y = (int) (Math.random() * a.length)
                    ;
            a[x] = a[y];
        }
    }
    public static void mix(int[] a, double mixFactor)
    {
        for(int i = 0; i < a.length * mixFactor; i++)
        {
            int 
                    x = (int) (Math.random() * a.length),
                    y = (int) (Math.random() * a.length)
                    ;
            int tmp = a[x];
            a[x] = a[y];
            a[y] = tmp;
        }
    }
    
    public static void fillRandom(int[] a, double similarityFactor, double compressionFactor, double curveExponent)
    {
        for(int i = 0; i < a.length; i++)
        {
            double d = Math.pow(Math.random(), curveExponent);
            a[i] = (int) (a.length * d * compressionFactor);
        }
        similarize(a, similarityFactor);
    }
    public static void fillDecr(int[] a, double mixFactor, double similarityFactor, double compressionFactor)
    {
        for(int i = 0; i < a.length; i++)
            a[i] = (int) ((a.length - i) * compressionFactor);
        mix(a, mixFactor);
        similarize(a, similarityFactor);
    }
    public static void fillIncr(int[] a, double mixFactor, double similarityFactor, double compressionFactor)
    {
        for(int i = 0; i < a.length; i++)
            a[i] = (int) ((a.length - i) * compressionFactor);
        mix(a, mixFactor);
        similarize(a, similarityFactor);
    }
    public static void fillSkewed(int[] a, double p, double exponent, double compressionFactor)
    {
        double[] skewThresholds = new double[(int)exponent];
        double pn = p;
        for (int i = 0; i < skewThresholds.length; i++) {
            skewThresholds[i] = pn;
            pn *= p;
        }
        for(int i = 0; i < a.length; i++)
        {
            double d = Math.random();
            for(int n = skewThresholds.length - 1; n >= 0; n--)
            {
                if (d > skewThresholds[n])
                    d = Math.random();
                else break;
            }
            a[i] = (int) (a.length * d * compressionFactor);
        }
    }
    public static void fillBinomial(int[] a, double p, int n)
    {
        for(int i = 0; i < a.length; i++)
            a[i] = Prob.binmRnd(n, p, Math.random());
    }
    

}
