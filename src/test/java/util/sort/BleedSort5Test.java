package util.sort;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static util.sort.Util.fillBinomial;
import static util.sort.Util.fillDecr;
import static util.sort.Util.fillIncr;
import static util.sort.Util.fillRandom;
import static util.sort.Util.fillSinusoidal;
import static util.sort.Util.fillSkewed;
import static util.sort.Util.fillTwinBinomial;

public class BleedSort5Test {

    private void fillTest(int[] orig, double similarityFactor, double compressionFactor, double mixFactor, 
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
    
    private void copy(int[] src, int[] dest)
    {
        System.arraycopy(src, 0, dest, 0, src.length);
    }

    @Test
    public void testBS() throws IOException
    {
        int[] orig = new int[ (int)1e5 ];
        int[] t = new int[orig.length];
        double  similarityFactor = 0.0;
        double  compressionFactor = 1.0 * 0.10;
        double  mixFactor = 40.0;
        double  bin_p = 0.0;
        int     n = 1021;
        double  exp = 1;
        boolean uniform = false;
        boolean decreasing = false;
        boolean skewed = false;
        boolean binomial = false;
        boolean sinusoidal = true;
        int peaks = 1;
        double frequency = 1000.0;
        double altitude = 32;
        double mixFrequency = 0.0001;
        int     trials = 20;
        int     innerTrials = 100;

        
        BufferedWriter bf = new BufferedWriter(new FileWriter("bleedsort-5-times.txt", true));

        System.out.println("priming...");
        for(int y = 1; y < 7; y++)
        {
            int[] tu = new int[100000];
            fillTest(tu, similarityFactor, compressionFactor, mixFactor, bin_p,
                    n, exp, uniform, decreasing, skewed, 
                    binomial, peaks, 
                    sinusoidal, frequency, altitude, mixFrequency
            );
            for(int x = 0; x < 3; x++)
            {
                int[] tmp = new int[tu.length];
                copy(tu, tmp);
                switch(y)
                {
                    case 0: BleedSort4b.bleedSort(tmp);  break;
                    case 1: BleedSort5.bleedSort(tmp);  break;
                    case 2: Arrays.sort(tmp);  break;
                }
            }
        }

        fillTest(orig, similarityFactor, compressionFactor, mixFactor, bin_p,
                n, exp, uniform, decreasing, skewed, 
                binomial, peaks, 
                sinusoidal, frequency, altitude, mixFrequency
        );
        int[] tu = null;
        for(int x = 0; x < 3; x++)
        {
            t = Arrays.copyOf(orig, orig.length);
            switch(x)
            {
                case 0: BleedSort4b.bleedSort(t);  break;
                case 1: BleedSort5.bleedSort(t);  tu = t; break;
                case 2: Arrays.sort(t);  
                    for (int i = 0; i < tu.length; i++) {
                        assertEquals(i + " " + t[i] + " " + tu[i], tu[i], t[i]);
                    }
                break;
            }
        }
        tu = null;

        System.out.println("benchmark...");
        long bs4 = 0, bs5 = 0, as = 0;
        long start, elapsed;
        for(int x = 0; x < trials; x++)
        {
            fillTest(orig, similarityFactor, compressionFactor, mixFactor, bin_p,
                    n, exp, uniform, decreasing, skewed, 
                    binomial, peaks, 
                    sinusoidal, frequency, altitude, mixFrequency
            );

            elapsed = 0;
            for(int j = 0; j < innerTrials; j++)
            {
                t = Arrays.copyOf(orig, orig.length);
                start = System.currentTimeMillis();
                BleedSort4b.bleedSort(t);
                elapsed += System.currentTimeMillis() - start;
//                if (orig.length > 2e6) System.gc();
            }
            System.out.println("Bleedsort4b " + elapsed + " " + BleedSort4b.lastSortStatistics);
            bs4 += elapsed;

            elapsed = 0;
            for(int j = 0; j < innerTrials; j++)
            {
                t = Arrays.copyOf(orig, orig.length);
                start = System.currentTimeMillis();
                BleedSort5.bleedSort(t);
                elapsed += System.currentTimeMillis() - start;
//                if (orig.length > 2e6) System.gc();
            }
            System.out.println("Bleedsort5 " + elapsed + " " + BleedSort5.lastSortStatistics);
            bs5 += elapsed;

            elapsed = 0;
            for(int j = 0; j < innerTrials; j++)
            {
                t = Arrays.copyOf(orig, orig.length);
                start = System.currentTimeMillis();
                java.util.Arrays.sort(t);
                elapsed += System.currentTimeMillis() - start;
//                if (orig.length > 2e6) System.gc();
            }
            System.out.println("Arrays.sort " + elapsed);
            as += elapsed;
        }
        double bss4, bss5, ass;
        String s =
            "bleedsort4b " + (bss4=(bs4 / (double)trials / (double)innerTrials))
            + " bleedsort5 " + (bss5=(bs5 / (double)trials / (double)innerTrials))
            + " Arrays.sort " + (ass=(as / (double)trials / (double)innerTrials))
            + " [ "+(uniform?"rnd":(sinusoidal?"sin":(binomial&peaks==2?"bin2":(binomial?"bin":(skewed?"skewed":(decreasing?"decr":"incr"))))))
                    +" "+orig.length
                    + " simil. "+similarityFactor
                    + " compr. " +compressionFactor
                    + " mix. "+mixFactor
                    + " p "+bin_p + " exp " + exp
                    + " n "+n
                    + " bs4b "+BleedSort4b.lastSortStatistics
                    + " bs5 "+BleedSort5.lastSortStatistics
                    + " freq " + frequency
                    + " alt " + altitude
                    + " mixfq " + mixFrequency
                    +" ]: " + (bss4 / ass) + " " + (bss5 / ass)
                    + "\n"
            ;
        System.out.print(s);
        bf.append(s);
        bf.close();
    }


}
