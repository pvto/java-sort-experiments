
package util.sort;

import java.util.Arrays;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Ignore;
import static util.sort.MergeSort.insertionSort;

public class MergeSortTest {

    @Ignore
    @Test
    public void testSpeeds() {
        long alltogether = 0L;
        for(int size = 4; size <= 32; size++)
        {
            int[] orig = new int[1000017];
            Util.fillRandom(orig, 0, 1, 1);
            long start = System.currentTimeMillis();
            for (int j = 0; j < 100; j++)
            {
                int[] a = Arrays.copyOf(orig, orig.length);
                int i = 0;
                for(; i < a.length - size; i += size)
                {
                    insertionSort(a, i, i + size);
                }
                if (i < a.length - size)
                {
                    insertionSort(a, i, a.length);
                }
            }
            long end = System.currentTimeMillis();
            System.out.println((size + 1) + " " + (end - start) + "ms");
        }
        
    }
    
    @Test
    public void testNaiveContinuousMerge()
    {
        int[] x = new int[]{15, 10, 12, 7, 12, 7, 10, 9, 8, 13, 0, 4, 7, 8, 2, 0};
        MergeSort.naiveMonotonousMergeSort(x);
        for (int i = 1; i < x.length; i++) { assertTrue(x[i] >= x[i - 1]); }
        
        x = new int[]{1, 0, 0};
        MergeSort.naiveMonotonousMergeSort(x);
        for (int i = 1; i < x.length; i++) { assertTrue(x[i] >= x[i - 1]); }
        if (1==0) return;
        
        int reasonable = 10000;
        for (int j = 0; j < reasonable; j++) {
            int[] a = new int[3+(int)(Math.random()*1024)];
            Util.fillRandom(a, 0, 1, 1);
            int[] orig = Arrays.copyOf(a, a.length);

            //MergeSort.mergeSort(orig);
            MergeSort.naiveMonotonousMergeSort(orig);
            for (int i = 1; i < orig.length; i++) {
                if (orig[i] < orig[i - 1])
                {
                    System.out.println(orig.length + " " + i + " " + orig[i] + " " + orig[i-1]);
                    System.out.println(Arrays.toString(a));
                    System.out.println(Arrays.toString(orig));
                }
                assertTrue(orig[i] >= orig[i - 1]);
            }
        }
    }
    
    @Test
    public void testMerge()
    {
        int reasonable = 10;   // 10000
        for (int j = 0; j < reasonable; j++) {
            int[] orig = new int[16+(int)(Math.random()*1024)];
            Util.fillRandom(orig, 0, 1, 1);
            
            MergeSort.mergeSort(orig);
            for (int i = 1; i < orig.length; i++) {
                assertTrue(orig[i] >= orig[i - 1]);
            }
        }
    }
    
    private int size = 5000;
    private int n = 2;
    private void fillSinusoidal(int[] orig)
    {
        Util.fillSinusoidal(orig, 5, 1000.0, 1.0, 0.0, 1.0);
        //            1e8, freq ~3.5(7) as pro ncms
        //            5e7       ~5(10) as pro ncms        
        //            1e7, freq ~15(30) as pro ncms
        //            1e6, freq ~20(40) threshold for as pro ncms
        //            7e5,      ~40(80) as pro ncms
        //            5e5,      ~130(260) as pro ncms/ms (ncms faster below)
        //            2e5, freq ~800(1600) as pro ms 
        //            1e5, freq ~3000(6000) threshold for as pro ms 
        //            7e4, freq ~2000(4000) threshold for as pro ms 
        //            1e4  freq ~17(35) th for ms pro ncms
        //                      ~80(160) as pro ms
        //            1e3  as reasonable if not sorted already
    }
    @Ignore
    @Test
    public void benchmarkMerge()
    {
        int[] orig = new int[size];
        fillSinusoidal(orig);
        int[] a = null;
        for (int j = 0; j < Math.min(10,n+1); j++)
        {
            a = Arrays.copyOf(orig, orig.length);
            MergeSort.mergeSort(a);
        }
        long start = System.currentTimeMillis();
        for (int j = 0; j < n; j++)
        {
            a = Arrays.copyOf(orig, orig.length);
            MergeSort.mergeSort(a);
        }
        long end = System.currentTimeMillis();
        System.out.println((a.length) + " " + ( (end - start) / (double)n) + "ms/sort");
        for (int i = 1; i < a.length; i++) {
            assertTrue(a[i] >= a[i - 1]);
        }
    }
    
    @Test
    public void benchmarkArraysSort()
    {
        int[] orig = new int[size];
        fillSinusoidal(orig);
        int[] a = null;
        for (int j = 0; j < Math.min(10,n+1); j++)
        {
            a = Arrays.copyOf(orig, orig.length);
            MergeSort.naiveMonotonousMergeSort(a);
        }
        long start = System.currentTimeMillis();
        for (int j = 0; j < n; j++)
        {
            a = Arrays.copyOf(orig, orig.length);
            Arrays.sort(a);
        }
        long end = System.currentTimeMillis();
        System.out.println((a.length) + " " + ( (end - start) / (double)n) + "ms/Arrays.sort");
    }
    
   @Test
    public void benchmarkNaiveContinuousMerge()
    {
        int[] orig = new int[size];
        fillSinusoidal(orig);
        int[] a = null;
        for (int j = 0; j < Math.min(10,n+1); j++)
        {
            a = Arrays.copyOf(orig, orig.length);
            MergeSort.naiveMonotonousMergeSort(a);
        }
        long start = System.currentTimeMillis();
        for (int j = 0; j < n; j++)
        {
            a = Arrays.copyOf(orig, orig.length);
            MergeSort.naiveMonotonousMergeSort(a);
        }
        long end = System.currentTimeMillis();
        System.out.println((a.length) + " " + ( (end - start) / (double)n) + "ms/cont.sort");
        for (int i = 1; i < a.length; i++) {
            assertTrue(a[i] >= a[i - 1]);
        }
    }
}