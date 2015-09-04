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

public class BleedSort4Test {

    @Test
    public void testBS1e6() throws IOException
    {
        double  similarityFactor = 0;
        double  compressionFactor = 1.0 * 1;
        double  mixFactor = 0.1;
        double  bin_p = 0.7;
        int     n = 1;
        double  exp = 4.75;
        boolean uniform = false;
        boolean decreasing = false;
        boolean skewed = true;
        boolean binomial = false;
        int     trials = 20;
        int     innerTrials = 10;
        int[] orig = new int[ (int)5e5 ];
        int[] t;
        
        BufferedWriter bf = new BufferedWriter(new FileWriter("bleedsort-4-times.txt", true));
        
        System.out.println("priming...");
        for(int x = 0; x < 3; x++)
        {
            if (uniform) fillRandom(orig, similarityFactor, compressionFactor, exp);
            else if (binomial) fillBinomial(orig, bin_p, n);
            else if (skewed) fillSkewed(orig, bin_p, exp, compressionFactor);
            else if (decreasing) fillDecr(orig, mixFactor, similarityFactor, compressionFactor);
            else if (!decreasing) fillIncr(orig, mixFactor, similarityFactor, compressionFactor);

//            for(int i = 0; i < Math.min(1000, orig.length); i++) 
//                System.out.print(orig[i] + " ");
//            System.exit(0);
            
            t = Arrays.copyOf(orig, orig.length);
            BleedSort3.bleedSort(t);
            t = Arrays.copyOf(orig, orig.length);
            BleedSort4.bleedSort(t);
            int[] tu = t;
            
            t = Arrays.copyOf(orig, orig.length);
            java.util.Arrays.sort(t);
            
            for (int i = 0; i < t.length; i++) {
                
                assertEquals(i + "", t[i], tu[i]);
                
            }
        }
        
        System.out.println("benchmark...");
        long bs3 = 0, bs4 = 0, as = 0;
        long start, elapsed;
        for(int x = 0; x < trials; x++)
        {

            orig = new int[orig.length];
            if (uniform) fillRandom(orig, similarityFactor, compressionFactor, exp);
            else if (binomial) fillBinomial(orig, bin_p, n);
            else if (skewed) fillSkewed(orig, bin_p, exp, compressionFactor);
            else if (decreasing) fillDecr(orig, mixFactor, similarityFactor, compressionFactor);
            else if (!decreasing) fillIncr(orig, mixFactor, similarityFactor, compressionFactor);


            elapsed = 0;
            for(int j = 0; j < innerTrials; j++)
            {
                t = Arrays.copyOf(orig, orig.length);
                start = System.currentTimeMillis();
                BleedSort3.bleedSort(t);
                elapsed += System.currentTimeMillis() - start;
                if (orig.length > 2e6)
                    System.gc();
            }
            System.out.println("Bleedsort3 " + elapsed);
            bs3 += elapsed;

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
            System.out.println("Bleedsort4 " + elapsed);
            bs4 += elapsed;

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
            "bleedsort3 " + (bss=(bs3 / (double)trials / (double)innerTrials))
            + " bleedsort4 " + (bss4=(bs4 / (double)trials / (double)innerTrials))
            + " Arrays.sort " + (ass=(as / (double)trials / (double)innerTrials))
            + " [ "+(uniform?"rnd":(binomial?"bin":(skewed?"skewed":(decreasing?"decr":"incr"))))
                    +" "+orig.length
                    + " simil. "+similarityFactor
                    + " compr. " +compressionFactor
                    + " mix. "+mixFactor
                    + " p "+bin_p + " exp " + exp
                    + " n "+n
                    +" ]: " + (bss / ass) + " " + (bss4 / ass)
                    + "\n"
            ;
        System.out.print(s);
        bf.append(s);
        bf.close();
    }

}
