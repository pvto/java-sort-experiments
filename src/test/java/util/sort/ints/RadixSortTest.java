
package util.sort.ints;

import util.sort.ints.RadixSort;
import util.sort.ints.BleedSort5;
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
        int[] orig = new int[ (int)1e8 ];
        int[] t = new int[orig.length];
        double  similarityFactor = 0.0;
        double  compressionFactor = 1.0 * 1;
        double  mixFactor = 10;
        double  bin_p = 0.25;
        int     n = 5000;
        double  exp = 1;
        boolean uniform = false;
        boolean decreasing = false;
        boolean skewed = false;
        boolean binomial = true;
        boolean sinusoidal = false;
        int peaks = 1;
        double frequency = 1;
        double altitude = orig.length;
        double mixFrequency = 1;
        int     trials = 20;
        int     innerTrials = 1;

        
        BufferedWriter bf = new BufferedWriter(new FileWriter("radix8-bs5-times.txt", true));

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
                    case 0: BleedSort5.bleedSort(tmp);  break;
                    case 1: RadixSort.radix8(tmp);  break;
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
                case 0: BleedSort5.bleedSort(t);  break;
                case 1: RadixSort.radix8(t);  tu = t; break;
                case 2: Arrays.sort(t);  
                    for (int i = 0; i < tu.length; i++) {
                        assertEquals(i + " " + t[i] + " " + tu[i], tu[i], t[i]);
                    }
                break;
            }
        }
        tu = null;

        System.out.println("benchmark...");
        long bs5 = 0, rad8 = 0, as = 0;
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
                BleedSort5.bleedSort(t);
                elapsed += System.currentTimeMillis() - start;
//                if (orig.length > 2e6) System.gc();
            }
            System.out.println("Bleedsort5 " + elapsed + " " + BleedSort5.lastSortFlags);
            bs5 += elapsed;

            elapsed = 0;
            for(int j = 0; j < innerTrials; j++)
            {
                t = Arrays.copyOf(orig, orig.length);
                start = System.currentTimeMillis();
                RadixSort.radix16(t);
                elapsed += System.currentTimeMillis() - start;
//                if (orig.length > 2e6) System.gc();
            }
            System.out.println("Radix8 " + elapsed);
            rad8 += elapsed;

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
        double bss5, radix, ass;
        String s =
            "bleedsort5 " + (bss5=(bs5 / (double)trials / (double)innerTrials))
            + " radix8 " + (radix=(rad8 / (double)trials / (double)innerTrials))
            + " Arrays.sort " + (ass=(as / (double)trials / (double)innerTrials))
            + " [ "+(uniform?"rnd":(sinusoidal?"sin":(binomial&peaks==2?"bin2":(binomial?"bin":(skewed?"skewed":(decreasing?"decr":"incr"))))))
                    +" "+orig.length
                    + " simil. "+similarityFactor
                    + " compr. " +compressionFactor
                    + " mix. "+mixFactor
                    + " p "+bin_p + " exp " + exp
                    + " n "+n
                    + " bs5 "+BleedSort5.lastSortFlags
                    + " 0 0"
                    + " freq " + frequency
                    + " alt " + altitude
                    + " mixfq " + mixFrequency
                    +" ]: " + (bss5 / ass) + " " + (radix / ass)
                    + "\n"
            ;
        System.out.print(s);
        bf.append(s);
        bf.close();
    }


}
