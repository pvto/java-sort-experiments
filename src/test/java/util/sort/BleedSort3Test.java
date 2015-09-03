
package util.sort;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static util.sort.BleedSort2Test.fillBinomial;
import static util.sort.BleedSort2Test.fillDecr;
import static util.sort.BleedSort2Test.fillIncr;
import static util.sort.BleedSort2Test.fillRandom;
import static util.sort.BleedSort2Test.fillSkewed;

public class BleedSort3Test {

    
    @Test
    public void testBS1e6() throws IOException
    {
        BufferedWriter bf = new BufferedWriter(new FileWriter("bleedsort-3-times-phase-2.txt", true));
        double  similarityFactor = 8.0;
        double  compressionFactor = 1.0 * 1;
        double  mixFactor = 0.0;
        double  bin_p = 0.5;
        int     n = 1000;
        double  exp = 8;
        boolean uniform = true;
        boolean decreasing = false;
        boolean skewed = false;
        boolean binomial = false;
        int     trials = 20;
        int     innerTrials = 50;
        int[] orig = new int[ (int)1e5 ];
        int[] t;
        

        System.out.println("priming...");
        for(int x = 0; x < 3; x++)
        {
            if (uniform) fillRandom(orig, similarityFactor, compressionFactor, exp);
            else if (binomial) fillBinomial(orig, bin_p, n);
            else if (skewed) fillSkewed(orig, bin_p, exp, compressionFactor);
            else if (decreasing) fillDecr(orig, mixFactor, similarityFactor, compressionFactor);
            else if (!decreasing) fillIncr(orig, mixFactor, similarityFactor, compressionFactor);

            t = Arrays.copyOf(orig, orig.length);
            BleedSort3.bleedSort(t);
            int[] tu = t;
            
            t = Arrays.copyOf(orig, orig.length);
            java.util.Arrays.sort(t);
            
            for (int i = 0; i < t.length; i++) {
                
                assertEquals(i + "", t[i], tu[i]);
                
            }
        }
        
        System.out.println("benchmark...");
        long bs = 0, as = 0;
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
                start = System.currentTimeMillis();
                t = Arrays.copyOf(orig, orig.length);
                BleedSort3.bleedSort(t);
                elapsed += System.currentTimeMillis() - start;
            }
            bs += elapsed;
            System.out.println("bleedsort3 " + (elapsed / (double)innerTrials));
            
            elapsed = 0;
            for(int j = 0; j < innerTrials; j++)
            {
                start = System.currentTimeMillis();
                t = Arrays.copyOf(orig, orig.length);
                java.util.Arrays.sort(t);
                elapsed += System.currentTimeMillis() - start;
            }
            as += elapsed;
            System.out.println("Arrays.sort " + (elapsed / (double)innerTrials));
        }
        double bss, ass;
        String s = 
            "\nbleedsort3[avg]: " + (bss=(bs / (double)trials / (double)innerTrials))
            + "\nArrays.sort[avg]: " + (ass=(as / (double)trials / (double)innerTrials))
            + "\nFactor [for "+(uniform?"rnd":(binomial?"bin":(skewed?"skewed":(decreasing?"decr":"incr"))))
                    +" "+orig.length
                    + ", simil. "+similarityFactor
                    + ", compr. " +compressionFactor
                    + ", mix. "+mixFactor
                    + ", p "+bin_p + ", exp " + exp
                    + ", n "+n
                    +"]: " + bss / ass
            ;
        System.out.println(s);
        bf.append(s);
        bf.close();
    }
}