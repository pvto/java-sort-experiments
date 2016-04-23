package util.sort.ints;

import java.util.Arrays;
import util.Int;

/**
 * @author pvto https://github.com/pvto
 */
public class BleedSort2 {
    
    public static void bleedSort(int[] a)
    {
//        if (a.length < 100_000)
//        {
//            java.util.Arrays.sort(a);
//            return;
//        }
        
        int 
                sampleSize = 60,
                sign = 1,
                signChanges = 0
                ;
        int[] q = new int[5];   // quantiles 0, 25, 50, 75, 100
        int[] sample = new int[sampleSize];
        for(int i = 0; i < sampleSize; i++)
        {
            int 
                    ind = (int) (Math.random() * (a.length - 1)),
                    x1 = a[ind],
                    x2 = a[ind + 1],
                    signChange = Int.sign(x2 - x1)
                    ;
            if (signChange != sign)
                signChanges++;
            sign = signChange;
            sample[i] = a[ind];
        }
        Arrays.sort(sample);
        q[0] = sample[0];
        q[1] = sample[sample.length >>> 2];
        q[2] = sample[sample.length >>> 1];
        q[3] = sample[(sample.length >>> 1) + (sample.length >>> 2)];
        q[4] = sample[sample.length - 1];
            
//        if (Math.abs((q[2] - q[0])  - (q[4] - q[2])) 
//                > (q[4] - q[2]) / 20)
//        {   // if data is skewed, fallback to a more robust sort
//            Arrays.sort(a);
//            return;
//        }
        double similarByAvg = 0;
        for(int x = 0; x < 20; x++)
        {
            int 
                    ind = (int) (Math.random() * (a.length - 101)),
                    sameCount = 0
                    ;
            for (int i = 1; i < 101; i++)
                if (a[ind + i] == a[ind])
                    sameCount++;
            similarByAvg += sameCount;
        }
        similarByAvg = similarByAvg / 20.0 / 100.0
                + 1e-6              // add a small expectation
                ;
        similarByAvg = similarByAvg * a.length;

        if (similarByAvg > 16.0)
        {   // repetitive array, fall back to more robust sort
            System.out.println("out-similar " + similarByAvg);
            Arrays.sort(a);
            return;
        }
        if (q[4] - q[0] < a.length / 3.5)
        {   // when we approach the factor of 1/4 for (data_max - data_min) / (array_size), 
            // bleedsort gets stuck into a loop of expansion and eventually falls back to a more robust sort
            // ; it's better to fall back early 
            System.out.println("out-3.5");
            Arrays.sort(a);
            return;
        }

        int 
                sameness = fillLSDs((int)similarByAvg),
                tmpSize = (int)((a.length * 4))
                ;

        bleedSort(a, Int.fill(tmpSize, Integer.MIN_VALUE), q, sameness, 0);
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
    
    
    public static void bleedSort(int[] a, int[] tmp, int[] quantiles, int sameness, int bleeding)
    {
        if (bleeding > 0)
        {
            Arrays.sort(a);
            return;
        }

        int[] baselines = new int[] {
                (tmp.length >>> 3), // space for first quartile items; leave some left padding
                (tmp.length >>> 3) + (tmp.length * 3 >>> 4),  // space for second quartile items
                (tmp.length >>> 3) + (tmp.length * 6 >>> 4),  // space for third quartile items
                (tmp.length >>> 3) + (tmp.length * 9 >>> 4),  // space for fourth quartile items; leave some right padding
                (tmp.length * 7 >>> 3)
        };
        double[] mult = {
                    (baselines[1] - baselines[0]) / (double) (quantiles[1] - quantiles[0]),
                    (baselines[2] - baselines[1]) / (double) (quantiles[2] - quantiles[1]),
                    (baselines[3] - baselines[2]) / (double) (quantiles[3] - quantiles[2]),
                    (baselines[4] - baselines[3]) / (double) (quantiles[4] - quantiles[3])
        };
//        System.out.println("quantiles: " + Arrays.toString(quantiles));
//        System.out.println("baselines: " + Arrays.toString(baselines));
//        System.out.println("multipliers: " + Arrays.toString(mult));
        int     // these help in reacting to bad estimates of the distribution
                minHelper = 0, 
                minHelpCount = 0,
                maxHelper = tmp.length - 1, 
                maxHelpCount = 0,
                bleedCount = 0,
                bleedPatch = 0
                ;
        for(int i = 0; i < a.length; i++)
        {
            int 
                    x = a[i],
                    place;
            if (x < quantiles[2])
            {
                if (x < quantiles[1])
                {
                    place = baselines[0]
                            + (int) ((x - quantiles[0]) * mult[0])
                            ;
                }
                else 
                {
                    place = baselines[1]
                            + (int) ((x - quantiles[1]) * mult[1])
                            ;
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
                        // forsake our trial and run again!
                        // first adjust minimum
                        for(int j = 0; j < baselines[0]; j++)
                        {
                            if (tmp[j] != Integer.MIN_VALUE && tmp[j] < quantiles[0])
                                quantiles[0] = tmp[j];
                        }
                        // then expand tmp-array and try again
                        tmp = newTmpArr(tmp, a);
                        System.out.println("out-left");
                        bleedSort(a, tmp, quantiles, sameness, ++bleeding);
                        return;
                    }
                    tmp[place] = x;
                    continue;
                }
            }
            else if (x < quantiles[3])
            {
                place = baselines[2]
                        + (int) ((x - quantiles[2]) * mult[2])
                        ;
            }
            else
            {
                place = baselines[3]
                        + (int) ((x - quantiles[3]) * mult[3])
                        ;
                if (place >= tmp.length)
                {
                    place = maxHelper;
                    while (tmp[place] != Integer.MIN_VALUE)
                        place--;
                    maxHelper = place;
                    if (maxHelpCount++ > 100 && i < a.length >>> 1)
                    {   // problem in sample distribution (maximum was estimated too small)
                        // forsake our trial and run again!
                        // first adjust minimum and maximum
                        for(int j = 0; j < baselines[0]; j++)
                        {
                            if (tmp[j] != Integer.MIN_VALUE && tmp[j] < quantiles[0])
                                quantiles[0] = tmp[j];
                        }
                        for(int j = baselines[4]; j < tmp.length; j++)
                        {
                            if (tmp[j] > quantiles[4])
                                quantiles[4] = tmp[j];
                        }
                        // then expand tmp-array and try again
                        tmp = newTmpArr(tmp, a);
                        System.out.println("out-right");
                        bleedSort(a, tmp, quantiles, sameness, ++bleeding);
                        return;
                    }
                    tmp[place] = x;
                    continue;
                }
            }

            // should we "bleed" towards right?
            while(tmp[place] != Integer.MIN_VALUE)
            {
                place++;
                if (place >= tmp.length || 
                        (place & sameness) == 0 && bleedCount++ > a.length << 1)
                {
                    System.out.println("bleed");
                    Arrays.sort(a);
                    return;
//                    // first adjust maximum
//                    for(int j = baselines[4]; j < tmp.length; j++)
//                    {
//                        if (tmp[j] > quantiles[4])
//                            quantiles[4] = tmp[j];
//                    }
//                    // then expand tmp-array and try again
//                    System.out.println("bleed");
//                    tmp = newTmpArr(tmp, a);
//                    bleedSort(a, tmp, quantiles, sameness, ++bleeding);
//                    return;
                };
            }
            
            tmp[place] = x;
        }
//        for(int i = 0; i < tmp.length; i++)
//        {
//            if (tmp[i] == Integer.MIN_VALUE)
//                System.out.print("#");
//            else
//                System.out.print(tmp[i] + " ");
//        }
//        System.out.println("");
        for(int i = 0, j = 0; i < a.length; i++)
        {
            while(tmp[j] == Integer.MIN_VALUE)
                j++;
            a[i] = tmp[j];
            j++;
        }
        Arrays.sort(a);
    }
    
    private static int[] newTmpArr(int[] tmp, int[] a)
    {
        if (tmp.length / (double)a.length < 16)
            tmp = Int.fill((int) ((long)tmp.length * 3L >>> 1), Integer.MIN_VALUE);
        return tmp;
    }
    
}
