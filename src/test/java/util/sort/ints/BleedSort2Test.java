
package util.sort.ints;

import util.sort.ints.BleedSort2;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import static util.sort.ints.Util.fillDecr;
import static util.sort.ints.Util.fillIncr;
import static util.sort.ints.Util.fillRandom;
import static util.sort.ints.Util.fillSkewed;

public class BleedSort2Test {

    @Ignore
    @Test
    public void testBS1e7()
    {
        int[] orig = new int[(int)1e7];
        int[] t;
        
        System.out.println("priming...");
        for(int x = 0; x < 3; x++)
        {
            for(int i = 0; i < orig.length; i++)
                orig[i] = (int) (Math.random() * orig.length);
            t = Arrays.copyOf(orig, orig.length);
            BleedSort2.bleedSort(t);
            t = Arrays.copyOf(orig, orig.length);
            java.util.Arrays.sort(t);
        }
        
        System.out.println("benchmark...");
        long bs = 0, as = 0;
        int trials = 20;
        for(int x = 0; x < trials; x++)
        {

            orig = new int[(int)1e7];
            for(int i = 0; i < orig.length; i++)
                orig[i] = (int) (Math.random() * orig.length);

            t = Arrays.copyOf(orig, orig.length);
            long start = System.currentTimeMillis();
            BleedSort2.bleedSort(t);
            long elapsed = System.currentTimeMillis() - start;
            bs += elapsed;
            System.out.println("bleedsort " + elapsed);
            for(int i = 1; i < t.length; i++)
                assertTrue(i + ": " + t[i-1] + ">" + t[i], t[i -1] <= t[i]);


            t = Arrays.copyOf(orig, orig.length);
            start = System.currentTimeMillis();
            java.util.Arrays.sort(t);
            elapsed = System.currentTimeMillis() - start;
            System.out.println("Arrays.sort " + elapsed);
            as += elapsed;
        }
        System.out.println("bleedsort[avg]: " + (bs / (double)trials));
        System.out.println("Arrays.sort[avg]: " + (as / (double)trials));
//bleedsort[avg]: 626.65
//Arrays.sort[avg]: 848.45
    }
    
    
    @Ignore
    @Test
    public void testBS1e6() throws IOException
    {
        BufferedWriter bf = new BufferedWriter(new FileWriter("sort-times-2.txt", true));
        double similarityFactor = 0.0;
        double compressionFactor = 1.0 * 1;
        double mixFactor = 1.0;
        double bin_p = 0.4;
        double exp = 1.25;
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
            BleedSort2.bleedSort(t);
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
                BleedSort2.bleedSort(t);
                elapsed += System.currentTimeMillis() - start;
            }
            bs += elapsed;
            System.out.println("bleedsort " + (elapsed / (double)innerTrials));
            
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
            "\nbleedsort[avg]: " + (bss=(bs / (double)trials / (double)innerTrials))
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
//bleedsort[avg]: 0.3922
//Arrays.sort[avg]: 0.4777
//Factor [for 10000, simil. 0]: 0.8210173749214988

//bleedsort[avg]: 4.255
//Arrays.sort[avg]: 6.0729999999999995
//Factor [for 100000, simil. 0]: 0.7006421867281409

//bleedsort[avg]: 6.551
//Arrays.sort[avg]: 6.292000000000001
//Factor [for rnd 100000, simil. 0.0, compr. 0.0625, mix. 1.0]: 1.0411633820724728
//bleedsort[avg]: 7.125
//Arrays.sort[avg]: 6.82
//Factor [for rnd 100000, simil. 0.0, compr. 0.125, mix. 1.0]: 1.0447214076246334
//bleedsort[avg]: 6.88
//Arrays.sort[avg]: 6.8
//Factor [for rnd 100000, simil. 0.0, compr. 0.25, mix. 1.0]: 1.011764705882353
//bleedsort[avg]: 5.2925
//Arrays.sort[avg]: 6.395
//Factor [for rnd 100000, simil. 0.0, compr. 0.3333333333333333, mix. 1.0]: 0.8275996872556686
//bleedsort[avg]: 5.0475
//Arrays.sort[avg]: 6.660833333333333
//Factor [for rnd 100000, simil. 0.0, compr. 0.5, mix. 1.0]: 0.7577880645564871
//bleedsort[avg]: 4.965
//Arrays.sort[avg]: 6.529166666666667
//Factor [for rnd 100000, simil. 0.0, compr. 1.0, mix. 1.0]: 0.760433950223356
//bleedsort[avg]: 4.42
//Arrays.sort[avg]: 7.015000000000001
//Factor [for rnd 100000, simil. 0.0, compr. 32.0, mix. 1.0]: 0.63007840342124017
//bleedsort[avg]: 4.715
//Arrays.sort[avg]: 7.640000000000001
//Factor [for rnd 100000, simil. 0.0, compr. 256.0, mix. 1.0]: 0.6171465968586387
//bleedsort[avg]: 4.6049999999999995
//Arrays.sort[avg]: 7.125
//Factor [for rnd 100000, simil. 0.0, compr. 2048.0, mix. 1.0]: 0.6463157894736842

        
//bleedsort[avg]: 7.189
//Arrays.sort[avg]: 7.14
//Factor [for incr 100000, simil. 0.0, compr. 0.25, mix. 1.0]: 1.0068627450980392
//bleedsort[avg]: 6.896
//Arrays.sort[avg]: 6.93
//Factor [for incr 100000, simil. 0.0, compr. 0.2857142857142857, mix. 1.0]: 0.9950937950937951
//bleedsort[avg]: 5.455
//Arrays.sort[avg]: 6.99
//Factor [for incr 100000, simil. 0.0, compr. 0.3333333333333333, mix. 1.0]: 0.7804005722460658
//bleedsort[avg]: 3.78
//Arrays.sort[avg]: 7.01
//Factor [for incr 100000, simil. 0.0, compr. 0.5, mix. 1.0]: 0.5392296718972895
//bleedsort[avg]: 2.44
//Arrays.sort[avg]: 7.025
//Factor [for incr 100000, simil. 0.0, compr. 1.0, mix. 1.0]: 0.3473309608540925
//bleedsort[avg]: 2.145
//Arrays.sort[avg]: 6.876
//Factor [for incr 100000, simil. 0.0, compr. 2.0, mix. 1.0]: 0.3119546247818499
        


//######## 1000_000
//bleedsort[avg]: 59.379999999999995
//Arrays.sort[avg]: 76.09
//Factor [for 1000000, simil. 0]: 0.78
//bleedsort[avg]: 62.06
//Arrays.sort[avg]: 76.5
//Factor [for 1000000, simil. 0.1]: 0.8112418300653595
//bleedsort[avg]: 65.66
//Arrays.sort[avg]: 73.41
//Factor [for 1000000, simil. 0.2]: 0.8944285519683967
//bleedsort[avg]: 67.22
//Arrays.sort[avg]: 71.6
//Factor [for 1000000, simil. 0.3]: 0.9388268156424582
//bleedsort[avg]: 69.14
//Arrays.sort[avg]: 71.22999999999999
//Factor [for 1000000, simil. 0.4]: 0.9706584304366139
//bleedsort[avg]: 71.15
//Arrays.sort[avg]: 76.1
//Factor [for 1000000, simil. 0.5]: 0.9349540078843628
//bleedsort[avg]: 74.08
//Arrays.sort[avg]: 71.25
//Factor [for 1000000, simil. 0.6]: 1.039719298245614
//bleedsort[avg]: 74.7
//Arrays.sort[avg]: 77.05
//Factor [for 1000000, simil. 0.7]: 0.9695003244646334
//bleedsort[avg]: 74.66
//Arrays.sort[avg]: 77.49
//!!! Factor [for 1000000, simil. 0.7]: 0.9634791586011099
//bleedsort[avg]: 75.59
//Arrays.sort[avg]: 71.97999999999999
//Factor [for 1000000, simil. 0.8]: 1.0501528202278412
//bleedsort[avg]: 77.69
//Arrays.sort[avg]: 70.64
//Factor [for 1000000, simil. 0.9]: 1.0998018120045299
//bleedsort[avg]: 85.33
//Arrays.sort[avg]: 73.83
//Factor [for 1000000, simil. 1.0]: 1.1557632398753894

//bleedsort[avg]: 12.440000000000001
//Arrays.sort[avg]: 2.2800000000000002
//Factor [for incr 1000000, simil. 0.0, mix. 0.0]: 5.456140350877193
//bleedsort[avg]: 11.709999999999999
//Arrays.sort[avg]: 10.879999999999999
//Factor [for incr 1000000, simil. 0.0, mix. 3.051758E-6]: 1.0762867647058825
//bleedsort[avg]: 18.330000000000002
//Arrays.sort[avg]: 41.239999999999995
//Factor [for incr 1000000, simil. 0.0, mix. 0.1]: 0.4444713870029099
//bleedsort[avg]: 24.95
//Arrays.sort[avg]: 70.38
//Factor [for incr 1000000, simil. 0.0, mix. 1.0]: 0.3545041204887752

//bleedsort[avg]: 43.95
//Arrays.sort[avg]: 73.25
//Factor [for incr 1000000, simil. 0.1, mix. 1.0]: 0.6000000000000001
//bleedsort[avg]: 58.95
//Arrays.sort[avg]: 77.1
//Factor [for incr 1000000, simil. 0.5, mix. 1.0]: 0.764591439688716
//bleedsort[avg]: 71.6
//Arrays.sort[avg]: 78.6
//Factor [for incr 1000000, simil. 1.0, mix. 1.0]: 0.910941475826972

//bleedsort[avg]: 14.690000000000001
//Arrays.sort[avg]: 3.1399999999999997
//Factor [for decr 1000000, simil. 0.0, mix. 0.0]: 4.678343949044587
//bleedsort[avg]: 11.86
//Arrays.sort[avg]: 10.690000000000001
//Factor [for decr 1000000, simil. 0.0, mix. 3.051758E-6]: 1.109448082319925
//bleedsort[avg]: 12.88
//Arrays.sort[avg]: 11.5
//Factor [for decr 1000000, simil. 0.0, mix. 6.103516E-6]: 1.12
//bleedsort[avg]: 12.03
//Arrays.sort[avg]: 14.63
//Factor [for decr 1000000, simil. 0.0, mix. 1.220703E-5]: 0.822282980177717
//bleedsort[avg]: 12.14
//Arrays.sort[avg]: 16.32
//Factor [for decr 1000000, simil. 0.0, mix. 2.441406E-5]: 0.7438725490196079
//bleedsort[avg]: 12.27
//Arrays.sort[avg]: 15.319999999999999
//Factor [for decr 1000000, simil. 0.0, mix. 4.882813E-5]: 0.8009138381201045
//bleedsort[avg]: 12.51
//Arrays.sort[avg]: 16.580000000000002
//Factor [for decr 1000000, simil. 0.0, mix. 9.765625E-5]: 0.7545235223160434
//bleedsort[avg]: 12.309999999999999
//Arrays.sort[avg]: 16.97
//Factor [for decr 1000000, simil. 0.0, mix. 1.953125E-4]: 0.7253977607542722
//bleedsort[avg]: 12.55
//Arrays.sort[avg]: 18.919999999999998
//Factor [for decr 1000000, simil. 0.0, mix. 3.90625E-4]: 0.6633192389006344
//bleedsort[avg]: 13.85
//Arrays.sort[avg]: 18.2
//Factor [for decr 1000000, simil. 0.0, mix. 7.8125E-4]: 0.760989010989011
//bleedsort[avg]: 12.64
//Arrays.sort[avg]: 19.47
//Factor [for decr 1000000, simil. 0.0, mix. 0.0015625]: 0.6492039034411916
//bleedsort[avg]: 12.709999999999999
//Arrays.sort[avg]: 21.11
//Factor [for decr 1000000, simil. 0.0, mix. 0.003125]: 0.6020843202273803
//bleedsort[avg]: 13.25
//Arrays.sort[avg]: 21.7
//Factor [for decr 1000000, simil. 0.0, mix. 0.00625]: 0.6105990783410138
//bleedsort[avg]: 13.85
//Arrays.sort[avg]: 23.27
//Factor [for decr 1000000, simil. 0.0, mix. 0.0125]: 0.5951869359690589
//bleedsort[avg]: 14.77
//Arrays.sort[avg]: 29.380000000000003
//Factor [for decr 1000000, simil. 0.0, mix. 0.025]: 0.5027229407760381
//bleedsort[avg]: 17.009999999999998
//Arrays.sort[avg]: 32.57
//Factor [for decr 1000000, simil. 0.0, mix. 0.05]: 0.5222597482345717
//bleedsort[avg]: 18.259999999999998
//Arrays.sort[avg]: 40.660000000000004
//Factor [for decr 1000000, simil. 0.0, mix. 0.1]: 0.4490900147565174
//bleedsort[avg]: 22.59
//Arrays.sort[avg]: 62.33
//Factor [for decr 1000000, simil. 0.0, mix. 0.3]: 0.3624257981710252
//bleedsort[avg]: 23.16
//Arrays.sort[avg]: 66.09
//Factor [for decr 1000000, simil. 0.0, mix. 0.5]: 0.3504312301407172
//bleedsort[avg]: 24.79
//Arrays.sort[avg]: 71.72
//Factor [for decr 1000000, simil. 0.0, mix. 1.0]: 0.34564974902398216

//######## 2e8
//bleedsort[avg]: 1301.1
//Arrays.sort[avg]: 1879.5
//Factor [for 20000000, simil. 0]: 0.6922585794094174
//bleedsort[avg]: 1631.95
//Arrays.sort[avg]: 1772.35
//Factor [for 20000000, simil. 0.5]: 0.9207831410274495
        
//bleedsort[avg]: 981.35
//Arrays.sort[avg]: 1869.35
//Factor [for incr 20000000, simil. 0.1, mix. 1.0]: 0.5249685719635168
    }
 

}