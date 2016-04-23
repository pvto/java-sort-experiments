package util.sort.ints;

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
    
    public static void fillTwinBinomial(int[] a, double p, int n)
    {
        double q = 1.0-p;
        for(int i = 0; i < a.length; i++)
            a[i] = Prob.binmRnd(n, p, Math.random()) + Prob.binmRnd(n, q, Math.random());
    }
    
    public static void fillSinusoidal(int[] a, double frequency, double altitude, double exponent, double mixFactor, double mixXFrequency)
    {
        double place = 0.0;
        double add = Math.PI * 2.0 * frequency / a.length; 
        for(int i = 0; i < a.length; i++)
        {
            double val = Math.sin(place);
            if (exponent != 1.0) 
                val = Math.pow(val, exponent);
            a[i] = (int) (altitude * val);
            
            place += add;
        }
        if (mixFactor > 0)
            for(int i = 0; i < mixFactor * a.length; i++)
            {
                int x = Math.min(a.length - 1,
                        (int) (mixXFrequency * a.length + Math.random() * (a.length * (1.0 - mixXFrequency * 2)))
                );
                int y = (int) (Math.random() * mixXFrequency);
                if (y < 0) y = 0;
                else if (y >= a.length) y = a.length;
                int tmp = a[x];
                a[x] = a[y];
                a[y] = tmp;
            }
    }
    
    
    
    
    
    public static void fillTest(int[] orig, double similarityFactor, double compressionFactor, double mixFactor, 
            double bin_p, int n, double exp, 
            boolean uniform, boolean decreasing, boolean skewed, 
            boolean binomial, int peaks,
            boolean sinusoidal, double frequency, double altitude, double mixFrequency)
    {
        if (uniform) fillRandom(orig, similarityFactor, compressionFactor, exp);
        else if (sinusoidal) fillSinusoidal(orig, frequency, altitude, exp, mixFactor, mixFrequency);
        else if (binomial && peaks == 2) fillTwinBinomial(orig, bin_p, n);
        else if (binomial) fillBinomial(orig, bin_p, n);
        else if (skewed) fillSkewed(orig, bin_p, exp, compressionFactor);
        else if (decreasing) fillDecr(orig, mixFactor, similarityFactor, compressionFactor);
        else if (!decreasing) fillIncr(orig, mixFactor, similarityFactor, compressionFactor);
    }

    public static void copy(int[] src, int[] dest)
    {
        System.arraycopy(src, 0, dest, 0, src.length);
    }


}
