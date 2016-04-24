package util.sort.ints;

import java.util.Arrays;
import util.Ints;

/**
 * @author pvto https://github.com/pvto
 */
public class BleedSort4b {

    public static int lastSortFlags = 0;
    public static final int 
            TREESORT = 1,
            BLEEDSORT3 = 2,
            BLEEDSORT4 = 4,
            VERY_REPETITIVE = 256,
            REPETITIVE = 512,
            SMALL_RANGE = 1024,
            LONG_UNCHANGING_RUNS_IN_DATA = 2048,
            SMALL_TARGET = 4096;
    
    public static void bleedSort(int[] a)
    {
        lastSortFlags = 0;
        double[] sampledRepetition = sampleRepetition(a, 20);
        if (sampledRepetition[0] > Math.max(20, a.length / 1000000.0))
        {
            if (sampledRepetition[1] > 6)
            {
                lastSortFlags |= VERY_REPETITIVE + LONG_UNCHANGING_RUNS_IN_DATA + TREESORT;
                InntTreeSort.inntTreeHungrySort(a);
                return;
            }
            else
            {
                lastSortFlags |= VERY_REPETITIVE + TREESORT;
                InntTreeSort.inntTreeSort(a);
                return;
            }
        }

        int sampleSize = 160;
        int[] sample = new int[sampleSize];
        for(int i = 0; i < sampleSize; i++)
        {
            int ind = (int) (Math.random() * (a.length - 1));
            sample[i] = a[ind];
        }
        Arrays.sort(sample);
        
        if (sample[sample.length - 1] - sample[0] < 2048)
        {
            lastSortFlags |= SMALL_RANGE + TREESORT;
            InntTreeSort.inntTreeSort(a);
            return;
        }

        int[] q = new int[9];   // quantiles 0, 12.5, 25, 37.5, 50, 62.5, 75, 87.5, 100
        q[0] = sample[0];
        q[1] = sample[sample.length >>> 3];
        q[2] = sample[sample.length >>> 2];
        q[3] = sample[(sample.length >>> 3) + (sample.length >>> 2)];
        q[4] = sample[sample.length >>> 1];
        q[5] = sample[(sample.length >>> 3) + (sample.length >>> 1)];
        q[6] = sample[(sample.length >>> 2) + (sample.length >>> 1)];
        q[7] = sample[sample.length - (sample.length >>> 3)];
        q[8] = sample[sample.length - 1];

        int tmpSize = (int) (a.length * 4);
        if (sampledRepetition[0] > 4 
                || q[8] - q[0] < a.length >>> 1)
        {
            lastSortFlags |= REPETITIVE + BLEEDSORT4;
            countingBleedSort(a, Ints.fill(tmpSize >> 1, Integer.MIN_VALUE), q);
            return;
        }
        else
        {
            lastSortFlags |= BLEEDSORT3;
            int repetitionBitmap = fillLSDs((int)sampledRepetition[0]);
            bleedSort(a, Ints.fill(tmpSize, Integer.MIN_VALUE), q, repetitionBitmap);
            return;
        }
    }
    
    
    public static double[] sampleRepetition(int[] a, int sampleSize)
    {
        double repetitionAvg = 0;
        
        int runLength = 0;
        int sameCount = 0;
        for(int x =a[0], last = x, i = 1; i < a.length >>> 3; i++)
        {
            int s = a[i];
            if (s == last)
            {
                runLength++;
            }
            else
            {
                runLength--;
            }
            if (s == x)
                sameCount++;
            last = s;
            if (runLength > 1000)
                return new double[]{1000.0, runLength};
        }
        repetitionAvg += sameCount * 100.0 / (double) (a.length >>> 3);
        
        for(int j = 0; j < sampleSize - 4; j++)
        {
            int ind = (int) (Math.random() * (a.length - 101));
            sameCount = 0;
            for (int i = 1; i < 101; i++)
                if (a[ind + i] == a[ind])
                    sameCount++;
            repetitionAvg += sameCount;
        }

        for(int j = 2; j < 4; j++)
        {
            int x = a[j];
            sameCount = 0;
            for(int i = j + 1; i < a.length >>> 3; i++)
            {
                int s = a[i];
                if (s == x)
                    sameCount++;
            }
            repetitionAvg += sameCount * 100.0 / (double) (a.length >>> 3);
        }
        return new double[] {
            ( repetitionAvg / sampleSize / 100.0 + 1e-6 ) * a.length, // add a small expectation
            runLength
        };
    } 
    
    public static int fillLSDs(int x)
    {
        int y = 0x01;
        while(y < x)
        {
            x |= y;  y <<= 1;
        }
        return x;
    }
    
    
    public static void bleedSort(int[] a, int[] tmp, int[] quantiles, int repetitionBitmap)
    {
        int q0 = quantiles[0];
        int q1 = quantiles[1];
        int q2 = quantiles[2];
        int q3 = quantiles[3];
        int q4 = quantiles[4];
        int q5 = quantiles[5];
        int q6 = quantiles[6];
        int q7 = quantiles[7];
        int q8 = quantiles[8];
        
        int baseline0 = (tmp.length >>> 3); // space for first quantile items; leave some left padding
        int baseline1 = (tmp.length >>> 3) + (tmp.length * 3 >>> 5);  // space for second quantile items
        int baseline2 = (tmp.length >>> 3) + (tmp.length * 6 >>> 5); // space for third quantile items
        int baseline3 = (tmp.length >>> 3) + (tmp.length * 9 >>> 5);  // space for fourth quantile items
        int baseline4 = (tmp.length >>> 3) + (tmp.length * 12 >>> 5);  // space for fifth quantile items
        int baseline5 = (tmp.length >>> 3) + (tmp.length * 15 >>> 5);  // space for sixth quantile items
        int baseline6 = (tmp.length >>> 3) + (tmp.length * 18 >>> 5);  // space for seventh quantile items
        int baseline7 = (tmp.length >>> 3) + (tmp.length * 21 >>> 5);  // space for eighth quantile items
        int baseline8 = (tmp.length * 7 >>> 3);// leave some right padding
        
        double mult0 = (baseline1 - baseline0) / (double) (q1 - q0);
        double mult1 = (baseline2 - baseline1) / (double) (q2 - q1);
        double mult2 = (baseline3 - baseline2) / (double) (q3 - q2);
        double mult3 = (baseline4 - baseline3) / (double) (q4 - q3);
        double mult4 = (baseline5 - baseline4) / (double) (q5 - q4);
        double mult5 = (baseline6 - baseline5) / (double) (q6 - q5);
        double mult6 = (baseline7 - baseline6) / (double) (q7 - q6);
        double mult7 = (baseline8 - baseline7) / (double) (q8 - q7);

        int     // these help in reacting to bad estimates of the distribution
                minHelper = 0, 
                minHelpCount = 0,
                maxHelper = tmp.length - 1, 
                maxHelpCount = 0,
                bleedCount = 0
                ;
        for(int i = 0; i < a.length; i++)
        {
            int x = a[i];
            int place;
            if (x < q4)
            {
                if (x < q2)
                {
                    if (x < q1)
                    {
                        place = baseline0 + (int) ((x - q0) * mult0);
                    }
                    else 
                    {
                        place = baseline1 + (int) ((x - q1) * mult1);
                    }
                    if (place < 0)
                    {
                        place = minHelper;
                        while (tmp[place] != Integer.MIN_VALUE)
                            place++;
                        minHelper = place;
                        minHelper++;
                        if (minHelpCount++ > 100 && i < a.length >>> 1)
                        {   // problem in sample distribution (minimum was estimated too big)
                            fillPartially(a, tmp);
                            System.out.println("out-left");
                            Arrays.sort(a);
                            return;
                        }
                        tmp[place] = x;
                        continue;
                    }
                }
                else if (x < q3)
                {
                    place = baseline2 + (int) ((x - q2) * mult2);
                }
                else {
                    place = baseline3 + (int) ((x - q3) * mult3);
                }
            }
            else if (x < q6)
            {
                if (x < q5)
                {
                    place = baseline4 + (int) ((x - q4) * mult4);
                }
                else
                {
                    place = baseline5 + (int) ((x - q5) * mult5);
                }
            }
            else
            {
                if (x < q7)
                {
                    place = baseline6 + (int) ((x - q6) * mult6);
                }
                else
                {
                    place = baseline7 + (int) ((x - q7) * mult7);
                }
                if (place >= tmp.length)
                {
                    place = maxHelper;
                    while (tmp[place] != Integer.MIN_VALUE)
                        place--;
                    maxHelper = place;
                    if (maxHelpCount++ > 100 && i < a.length >>> 1)
                    {   // problem in sample distribution (maximum was estimated too small)
                        fillPartially(a, tmp);
                        System.out.println("out-right");
                        Arrays.sort(a);
                        return;
                    }
                    tmp[place] = x;
                    continue;
                }
            }

            // two rounds free, and after that bleeding starts to count
            if (tmp[place] == Integer.MIN_VALUE)
            {
                tmp[place] = x;
                continue;
            }
            if (++place == tmp.length)
            {
//                System.out.println("bleed!");
                fillPartially(a, tmp);
                Arrays.sort(a);
                return;
            }
            else if (tmp[place] == Integer.MIN_VALUE)
            {
                tmp[place] = x;
                continue;
            }
            
            // find an unused slot by moving right
            while(tmp[place] != Integer.MIN_VALUE)
            {
                place++;
                if ((place & repetitionBitmap) == 0 && bleedCount++ > a.length << 1
                        || place == tmp.length) 
                {   // excessive bleeding; get out
//                    System.out.println("bleed");
                    fillPartially(a, tmp);
                    Arrays.sort(a);
                    return;
                };
            }
            
            tmp[place] = x;
        }
        for(int i = 0, j = 0; i < a.length; i++)
        {
            while(tmp[j] == Integer.MIN_VALUE)
                j++;
            a[i] = tmp[j];
            j++;
        }
        if (bleedCount == 0)
        {
            boolean sorted = true;
            for(int i = 1; i < a.length; i++)
            {
                if (a[i-1] > a[i])
                {
                    sorted = false;
                    break;
                }
            }
            if (sorted)
            {
                return;
            }
        }
        Arrays.sort(a);
    }

    
    
    
    public static void countingBleedSort(int[] a, int[] tmp, int[] quantiles)
    {
        int q0 = quantiles[0];
        int q1 = quantiles[1];
        int q2 = quantiles[2];
        int q3 = quantiles[3];
        int q4 = quantiles[4];
        int q5 = quantiles[5];
        int q6 = quantiles[6];
        int q7 = quantiles[7];
        int q8 = quantiles[8];
        
        int baseline0 = (tmp.length >>> 3); // space for first quantile items; leave some left padding
        int baseline1 = (tmp.length >>> 3) + (tmp.length * 3 >>> 5);  // space for second quantile items
        int baseline2 = (tmp.length >>> 3) + (tmp.length * 6 >>> 5); // space for third quantile items
        int baseline3 = (tmp.length >>> 3) + (tmp.length * 9 >>> 5);  // space for fourth quantile items
        int baseline4 = (tmp.length >>> 3) + (tmp.length * 12 >>> 5);  // space for fifth quantile items
        int baseline5 = (tmp.length >>> 3) + (tmp.length * 15 >>> 5);  // space for sixth quantile items
        int baseline6 = (tmp.length >>> 3) + (tmp.length * 18 >>> 5);  // space for seventh quantile items
        int baseline7 = (tmp.length >>> 3) + (tmp.length * 21 >>> 5);  // space for eighth quantile items
        int baseline8 = (tmp.length * 7 >>> 3);// leave some right padding
        
        double mult0 = (baseline1 - baseline0) / (double) (q1 - q0);
        double mult1 = (baseline2 - baseline1) / (double) (q2 - q1);
        double mult2 = (baseline3 - baseline2) / (double) (q3 - q2);
        double mult3 = (baseline4 - baseline3) / (double) (q4 - q3);
        double mult4 = (baseline5 - baseline4) / (double) (q5 - q4);
        double mult5 = (baseline6 - baseline5) / (double) (q6 - q5);
        double mult6 = (baseline7 - baseline6) / (double) (q7 - q6);
        double mult7 = (baseline8 - baseline7) / (double) (q8 - q7);

        int[] counts = new int[tmp.length];
        
        int     // these help in reacting to bad estimates of the distribution
                minHelper = 0, 
                minHelpCount = 0,
                maxHelper = tmp.length - 1, 
                maxHelpCount = 0,
                bleedCount = 0
                ;
        for(int i = 0; i < a.length; i++)
        {
            int x = a[i];
            int place;
            if (x < q4)
            {
                if (x < q2)
                {
                    if (x < q1)
                    {
                        place = baseline0 + (int) ((x - q0) * mult0);
                    }
                    else 
                    {
                        place = baseline1 + (int) ((x - q1) * mult1);
                    }
                    if (place < 0)
                    {
                        place = minHelper;
                        while (tmp[place] != x && tmp[place] != Integer.MIN_VALUE)
                            place++;
                        minHelper = place;
                        minHelper++;
                        if (minHelpCount++ > 100 && i < a.length >>> 1)
                        {   // problem in sample distribution (minimum was estimated too big)
                            fillPartiallyWithCounts(a, tmp, counts);
                            System.out.println("out-left");
                            Arrays.sort(a);
                            return;
                        }
                        tmp[place] = x;
                        counts[place]++;
                        continue;
                    }
                }
                else if (x < q3)
                {
                    place = baseline2 + (int) ((x - q2) * mult2);
                }
                else {
                    place = baseline3 + (int) ((x - q3) * mult3);
                }
            }
            else if (x < q6)
            {
                if (x < q5)
                {
                    place = baseline4 + (int) ((x - q4) * mult4);
                }
                else
                {
                    place = baseline5 + (int) ((x - q5) * mult5);
                }
            }
            else
            {
                if (x < q7)
                {
                    place = baseline6 + (int) ((x - q6) * mult6);
                }
                else
                {
                    place = baseline7 + (int) ((x - q7) * mult7);
                }
                if (place >= tmp.length)
                {
                    place = maxHelper;
                    while (tmp[place] != x && tmp[place] != Integer.MIN_VALUE)
                        place--;
                    maxHelper = place;
                    if (maxHelpCount++ > 100 && i < a.length >>> 1)
                    {   // problem in sample distribution (maximum was estimated too small)
                        fillPartiallyWithCounts(a, tmp, counts);
                        System.out.println("out-right");
                        Arrays.sort(a);
                        return;
                    }
                    tmp[place] = x;
                    counts[place]++;
                    continue;
                }
            }

            // two rounds free, and after that bleeding starts to count
            if (tmp[place] == x || tmp[place] == Integer.MIN_VALUE)
            {
                tmp[place] = x;
                counts[place]++;
                continue;
            }
            if (++place == tmp.length)
            {
//                System.out.println("bleed!");
                fillPartiallyWithCounts(a, tmp, counts);
                Arrays.sort(a);
                return;
            }
            else if (tmp[place] == x || tmp[place] == Integer.MIN_VALUE)
            {
                tmp[place] = x;
                counts[place]++;
                continue;
            }
            
            // find an unused slot by moving right
            while(tmp[place] != x && tmp[place] != Integer.MIN_VALUE)
            {
                place++;
                if (bleedCount++ > a.length << 1
                        || place == tmp.length) 
                {   // excessive bleeding; get out
                    System.out.println("bleed");
                    fillPartiallyWithCounts(a, tmp, counts);
                    Arrays.sort(a);
                    return;
                };
            }
            
            tmp[place] = x;
            counts[place]++;
        }
        for(int i = 0, j = 0; i < a.length;)
        {
            while(tmp[j] == Integer.MIN_VALUE)
            {
                j++;
            }
            int x = tmp[j];
            for(int n = counts[j]; n > 0; n--)
            {
                a[i++] = x;
            }
            j++;
        }
        if (bleedCount == 0)
        {
            boolean sorted = true;
            int last = a[0];
            for(int i = 1; i < a.length; i++)
            {
                int x = a[i];
                if (last > x)
                {
                    sorted = false;
                    break;
                }
                last = x;
            }
            if (sorted)
            {
//                System.out.println("sorted!");
                return;
            }
        }
        Arrays.sort(a);
    }

    
    private static void fillPartially(int[] a, int[] tmp)
    {
        out: for(int i = 0, j = 0; j < tmp.length; i++)
        {
            while(tmp[j] == Integer.MIN_VALUE)
            {
                if (++j == tmp.length) break out;
            }
            a[i] = tmp[j];
            j++;
        }
    }

    
    private static void fillPartiallyWithCounts(int[] a, int[] tmp, int[] counts)
    {
        out: for(int i = 0, j = 0; j < tmp.length;)
        {
            while(tmp[j] == Integer.MIN_VALUE)
            {
                if (++j == tmp.length) break out;
            }
            int x = tmp[j];
            for(int n = counts[j]; n > 0; n--)
            {
                a[i++] = x;
            }
            j++;
        }
    }

}
