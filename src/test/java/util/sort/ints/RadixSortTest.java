
package util.sort.ints;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static util.sort.ints.Util.copy;
import static util.sort.ints.Util.fillTest;

public class RadixSortTest {


    @Test
    public void testRadix() throws IOException
    {
        int[] orig = new int[ (int)8e6 ];
        int[] t = new int[orig.length];
        double  similarityFactor = 0.0;
        double  compressionFactor = 1.0 * 1000;
        double  mixFactor = 0;
        double  bin_p = 0.25;
        int     n = 0;
        double  exp = 3;
        boolean uniform = true;
        boolean decreasing = false;
        boolean skewed = false;
        boolean binomial = false;
        boolean sinusoidal = false;
        int peaks = 1;
        double frequency = 1;
        double altitude = orig.length;
        double mixFrequency = 1;
        int     trials = 20;
        int     innerTrials = 1;

        
        BufferedWriter bf = new BufferedWriter(new FileWriter("radix8-bs5-times.txt", true));

        Util.TestSorter baseSorter = Util.TestSorters.arraysSortSorter();
        Util.TestSorter mySorter = Util.TestSorters.radix11Sorter();
        Util.TestSorter competingSorter = Util.TestSorters.bleedSort6Sorter();
        
        
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
                    case 0: mySorter.sort(tmp);  break;
                    case 1: competingSorter.sort(tmp);  break;
                    case 2: baseSorter.sort(tmp);  break;
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
                case 0: mySorter.sort(t);  break;
                case 1: competingSorter.sort(t);  tu = t; break;
                case 2: baseSorter.sort(t);  
                    for (int i = 0; i < tu.length; i++) {
                        assertEquals(i + " " + t[i] + " " + tu[i], tu[i], t[i]);
                    }
                break;
            }
        }
        tu = null;

        System.out.println("benchmark...");
        long myTime = 0, compTime = 0, baseTime = 0;
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
                mySorter.sort(t);
                elapsed += System.currentTimeMillis() - start;
//                if (orig.length > 2e6) System.gc();
            }
            System.out.println(mySorter.name() + " " + elapsed + " " + mySorter.lastSortDescription());
            myTime += elapsed;

            elapsed = 0;
            for(int j = 0; j < innerTrials; j++)
            {
                t = Arrays.copyOf(orig, orig.length);
                start = System.currentTimeMillis();
                competingSorter.sort(t);
                elapsed += System.currentTimeMillis() - start;
//                if (orig.length > 2e6) System.gc();
            }
            System.out.println(competingSorter.name() + " " + elapsed + " " + competingSorter.lastSortDescription());
            compTime += elapsed;

            elapsed = 0;
            for(int j = 0; j < innerTrials; j++)
            {
                t = Arrays.copyOf(orig, orig.length);
                start = System.currentTimeMillis();
                baseSorter.sort(t);
                elapsed += System.currentTimeMillis() - start;
//                if (orig.length > 2e6) System.gc();
            }
            System.out.println(baseSorter.name() + " " + elapsed + " " + baseSorter.lastSortDescription());
            baseTime += elapsed;
        }
        double my, compet, base;
        String s =
            mySorter.name() + " " + (my=(myTime / (double)trials / (double)innerTrials))
            + " " + competingSorter.name() + " " + (compet=(compTime / (double)trials / (double)innerTrials))
            + " " + baseSorter.name() + " " + (base=(baseTime / (double)trials / (double)innerTrials))
            + " [ "+(uniform?"rnd":(sinusoidal?"sin":(binomial&peaks==2?"bin2":(binomial?"bin":(skewed?"skewed":(decreasing?"decr":"incr"))))))
                    +" "+orig.length
                    + " simil. "+similarityFactor
                    + " compr. " +compressionFactor
                    + " mix. "+mixFactor
                    + " p "+bin_p + " exp " + exp
                    + " n "+n
                    + " my "+mySorter.lastSortDescription()
                    + " 0 0"
                    + " freq " + frequency
                    + " alt " + altitude
                    + " mixfq " + mixFrequency
                    +" ]: " + (my / base) + " " + (compet / base)
                    + "\n"
            ;
        System.out.print(s);
        bf.append(s);
        bf.close();
    }


}
