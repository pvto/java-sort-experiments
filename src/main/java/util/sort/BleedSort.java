package util.sort;

import util.Int;

/**
 *
 * @author github.com/pvto
 */
public class BleedSort {
    
    public static void bleedSort(int[] a)
    {
        int 
                min = Integer.MAX_VALUE,
                max = Integer.MIN_VALUE,
                mean,
                sign = 1,
                signChanges = 0,
                sample = Math.min(a.length >> 2, 60)
                ;
        long sum = 0L;
        for(int i = sample; i > 0; i--)
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
            min = Math.min(min, x1);
            max = Math.max(max, x1);
            sum = sum + x1;
        }
        mean = (int) (sum / sample);
        
        double factor = signChanges / (double)sample;
        if (factor < 0.2 || a.length < 200000)
        {
            QuickSort.quickSort(a);
            return;
        }
        
        int tmpSize = (int)((a.length * 4));
        System.out.println(String.format(
                "sample %d min %d max %d mean %d signChanges %.2f tmpSize %d", 
                sample, min, max, mean, factor, tmpSize
        ));
        bleedSort(a, Int.fill(tmpSize, Integer.MIN_VALUE), min, max, mean);
    }
    
    
    
    public static void bleedSort(int[] a, int[] tmp, int min, int max, int mean)
    {
        double mult = (tmp.length * 0.5) / (double)a.length;
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
            int place = (a.length >>> 2)
                    + (int) ((x - min) * mult)
                    ;
            if (place < 0)
            {
                place = minHelper;
                while (tmp[place] != Integer.MIN_VALUE)
                    place++;
                minHelper = place;
                if (minHelpCount++ > 100 && i < a.length >>> 1)
                {   // problem in sample distribution (minimum was estimated too big)
                    // forsake our trial and run again!
                    tmp = newTmpArr(tmp, a);
                    System.out.println(String.format(
                            "new trial min %d->%d max %d tmp-size %d",
                            min, x, max, tmp.length
                    ));
                    bleedSort(a, tmp, x, max, mean);
                    return;
                }
            }
            if (place >= tmp.length)
            {
                place = maxHelper;
                while (tmp[place] != Integer.MIN_VALUE)
                    place--;
                maxHelper = place;
                if (maxHelpCount++ > 100 && i < a.length >>> 1)
                {   // problem in sample distribution (maximum was estimated too small)
                    // forsake our trial and run again!
                    tmp = newTmpArr(tmp, a);
                    System.out.println(String.format(
                            "new trial min %d max %d->%d, tmp-size %d",
                            min, max, x, tmp.length
                    ));
                    bleedSort(a, tmp, min, x, mean);
                    return;
                }
            }
            else
            {
                while(tmp[place] != Integer.MIN_VALUE)
                {
                    place++;
                    if (bleedCount++ > a.length << 1)
                    {
                        System.out.println("bleed");
                        bleedCount = 0;
                        int newMin = min, newMax = max;
                        for(int j = 0; j < tmp.length; j++)
                            if (tmp[j] != Integer.MIN_VALUE)
                            {
                                newMin = tmp[j];
                                break;
                            }
                        for(int j = tmp.length - 1; j > 0; j--)
                            if (tmp[j] != Integer.MIN_VALUE)
                            {
                                newMax = tmp[j];
                                break;
                            }
                        tmp = newTmpArr(tmp, a);
                        System.out.println(String.format(
                                "new trial min %d->%d max %d->%d, tmp-size %d",
                                min, newMin, max, newMax, x, tmp.length
                        ));
                        bleedSort(a, tmp, newMin, newMax, mean);
                        return;
                    };
                }
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
        QuickSort.quickSort(a);
    }
    
    private static int[] newTmpArr(int[] tmp, int[] a)
    {
        if (tmp.length / (double)a.length < 16)
            tmp = Int.fill((int) ((long)tmp.length * 3L >>> 1), Integer.MIN_VALUE);
        return tmp;
    }
    
}
