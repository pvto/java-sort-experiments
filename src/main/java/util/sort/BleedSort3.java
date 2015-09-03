package util.sort;

import java.util.Arrays;
import util.Int;

/**
 * @author pvto https://github.com/pvto
 */
public class BleedSort3 {
    
    
    
    public static void bleedSort(int[] a)
    {
        double sampledRepetition = sampleRepetition(a, 20);
        if (sampledRepetition > 20)
        {
            //Arrays.sort(a); //mostly slower?
            InntTreeSort.inntTreeSort(a);
            return;
        }
        int repetitionBitmap = fillLSDs((int)sampledRepetition);

        int sampleSize = 160;
        int[] q = new int[9];   // quantiles 0, 12.5, 25, 37.5, 50, 62.5, 75, 87.5, 100
        int[] sample = new int[sampleSize];
        for(int i = 0; i < sampleSize; i++)
        {
            int ind = (int) (Math.random() * (a.length - 1));
            sample[i] = a[ind];
        }
        Arrays.sort(sample);
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
        bleedSort(a, Int.fill(tmpSize, Integer.MIN_VALUE), q, repetitionBitmap);
    }
    
    public static double sampleRepetition(int[] a, int sampleSize)
    {
        double repetitionAvg = 0;
        for(int x = 0; x < sampleSize; x++)
        {
            int ind = (int) (Math.random() * (a.length - 101));
            int sameCount = 0;
            for (int i = 1; i < 101; i++)
                if (a[ind + i] == a[ind])
                    sameCount++;
            repetitionAvg += sameCount;
        }
        return ( repetitionAvg / sampleSize / 100.0 + 1e-6 ) * a.length; // add a small expectation    
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
    
}
