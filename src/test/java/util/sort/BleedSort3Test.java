
package util.sort;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;
import static util.sort.BleedSort2Test.fillDecr;
import static util.sort.BleedSort2Test.fillIncr;
import static util.sort.BleedSort2Test.fillRandom;
import static util.sort.BleedSort2Test.fillSkewed;

public class BleedSort3Test {

    
    @Test
    public void testBS1e6() throws IOException
    {
        BufferedWriter bf = new BufferedWriter(new FileWriter("beedsort-3-times.txt", true));
        double similarityFactor = 0.0;
        double compressionFactor = 1.0 * 1;
        double mixFactor = 1.0;
        double bin_p = 0.4;
        double exp = 0.5;
        boolean 
                random = true,
                decreasing = false,
                skewed = false
                ;
        int 
                trials = 20, 
                innerTrials = 5
                ;
        int[] orig = new int[(int)1e6];
        int[] t;
        

        System.out.println("priming...");
        for(int x = 0; x < 3; x++)
        {
            if (random) fillRandom(orig, similarityFactor, compressionFactor, exp);
            else if (skewed) fillSkewed(orig, bin_p, exp, compressionFactor);
            else if (decreasing) fillDecr(orig, mixFactor, similarityFactor, compressionFactor);
            else if (!decreasing) fillIncr(orig, mixFactor, similarityFactor, compressionFactor);
            
            //System.out.println(Arrays.toString(orig));
            t = Arrays.copyOf(orig, orig.length);
            BleedSort3.bleedSort(t);
            t = Arrays.copyOf(orig, orig.length);
            java.util.Arrays.sort(t);
        }
        
        System.out.println("benchmark...");
        long bs = 0, as = 0;
        long start, elapsed;
        for(int x = 0; x < trials; x++)
        {

            orig = new int[orig.length];
            if (random) fillRandom(orig, similarityFactor, compressionFactor, exp);
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
            + "\nFactor [for "+(random?"rnd":(skewed?"skewed":(decreasing?"decr":"incr")))
                    +" "+orig.length
                    + ", simil. "+similarityFactor
                    + ", compr. " +compressionFactor
                    + ", mix. "+mixFactor
                    + ", p "+bin_p + ", exp " + exp
                    +"]: " + bss / ass
            ;
        System.out.println(s);
        bf.append(s);
        bf.close();
    }
}