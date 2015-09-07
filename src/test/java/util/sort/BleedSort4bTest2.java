package util.sort;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import org.junit.Test;
import static util.sort.Util.fillBinomial;
import static util.sort.Util.fillDecr;
import static util.sort.Util.fillIncr;
import static util.sort.Util.fillRandom;
import static util.sort.Util.fillSkewed;
import static util.sort.Util.fillTwinBinomial;

public class BleedSort4bTest2 {

    private void fillTest(int[] orig, double similarityFactor, double compressionFactor, double mixFactor, 
            double bin_p, int n, double exp, 
            boolean uniform, boolean decreasing, boolean skewed, boolean binomial, 
            int peaks)
    {
        if (uniform) fillRandom(orig, similarityFactor, compressionFactor, exp);
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
        double  similarityFactor = 0.0;
        double  compressionFactor = 1.0 * 0.10;
        double  mixFactor = 2.0;
        double  bin_p = 0.0;
        int     n = 1013;
        double  exp = 1;
        boolean uniform = false;
        boolean decreasing = true;
        boolean skewed = false;
        boolean binomial = false;
        int peaks = 1;
        int     trials = 20;
        int     innerTrials = 2;
        int[] orig = new int[ (int)1e6 ];
        int[] t = new int[orig.length];

        BufferedWriter bf = new BufferedWriter(new FileWriter("bleedsort-4b-times.txt", true));

        System.out.println("priming...");
        int[] tu = new int[100000];
        for(int y = 1; y < 7; y++)
        {
            fillTest(tu, similarityFactor, compressionFactor, mixFactor, bin_p,
                    n, exp, uniform, decreasing, skewed, binomial, peaks
            );
            for(int x = 0; x < 3; x++)
            {
                copy(orig, t);
                switch(y)
                {
                    case 0: BleedSort4.bleedSort(t);  break;
                    case 1: BleedSort4b.bleedSort(t);  break;
                    case 2: Arrays.sort(t);  break;
                }
            }
        }

        System.out.println("benchmark...");
        long bs4 = 0, bs4b = 0, as = 0;
        long start, elapsed;
        for(int x = 0; x < trials; x++)
        {
            fillTest(orig, similarityFactor, compressionFactor, mixFactor, bin_p,
                    n, exp, uniform, decreasing, skewed, binomial, peaks
            );

            elapsed = 0;
            for(int j = 0; j < innerTrials; j++)
            {
                t = Arrays.copyOf(orig, orig.length);
                start = System.currentTimeMillis();
                BleedSort4.bleedSort(t);
                elapsed += System.currentTimeMillis() - start;
                if (orig.length > 2e6)
                    System.gc();
            }
            System.out.println("Bleedsort4 " + elapsed + " " + BleedSort4.lastSortStatistics);
            bs4 += elapsed;

            elapsed = 0;
            for(int j = 0; j < innerTrials; j++)
            {
                t = Arrays.copyOf(orig, orig.length);
                start = System.currentTimeMillis();
                BleedSort4b.bleedSort(t);
                elapsed += System.currentTimeMillis() - start;
                if (orig.length > 2e6)
                    System.gc();
            }
            System.out.println("Bleedsort4b " + elapsed + " " + BleedSort4b.lastSortStatistics);
            bs4b += elapsed;

            elapsed = 0;
            for(int j = 0; j < innerTrials; j++)
            {
                t = Arrays.copyOf(orig, orig.length);
                start = System.currentTimeMillis();
                java.util.Arrays.sort(t);
                elapsed += System.currentTimeMillis() - start;
                if (orig.length > 2e6)
                    System.gc();
            }
            System.out.println("Arrays.sort " + elapsed);
            as += elapsed;
        }
        double bss, bss4, ass;
        String s =
            "bleedsort4 " + (bss=(bs4 / (double)trials / (double)innerTrials))
            + " bleedsort4b " + (bss4=(bs4b / (double)trials / (double)innerTrials))
            + " Arrays.sort " + (ass=(as / (double)trials / (double)innerTrials))
            + " [ "+(uniform?"rnd":(binomial&peaks==2?"bin2":(binomial?"bin":(skewed?"skewed":(decreasing?"decr":"incr")))))
                    +" "+orig.length
                    + " simil. "+similarityFactor
                    + " compr. " +compressionFactor
                    + " mix. "+mixFactor
                    + " p "+bin_p + " exp " + exp
                    + " n "+n
                    + " bs4 "+BleedSort4.lastSortStatistics
                    + " bs4b "+BleedSort4b.lastSortStatistics
                    +" ]: " + (bss / ass) + " " + (bss4 / ass)
                    + "\n"
            ;
        System.out.print(s);
        bf.append(s);
        bf.close();
    }


}
