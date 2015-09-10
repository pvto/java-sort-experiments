
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

    @Test
    public void benchmark()
    {
        int[] orig = new int[1000017];
        Util.fillSinusoidal(orig, 3.0, 1000.0, 1.0, 0.0, 1.0);
        long start = System.currentTimeMillis();
        int[] a = null;
        for (int j = 0; j < 20; j++)
        {
            if (j == 10) start = System.currentTimeMillis();
            a = Arrays.copyOf(orig, orig.length);
            MergeSort.mergeSort(a);
        }
        long end = System.currentTimeMillis();
        System.out.println((a.length) + " " + ( (end - start) / 100.0) + "ms/sort");
        for (int i = 1; i < a.length; i++) {
            assertTrue(a[i] >= a[i - 1]);
        }
    }
    
    @Test
    public void benchmarkArraysSort()
    {
        int[] orig = new int[1000017];
        Util.fillSinusoidal(orig, 3.0, 1000.0, 1.0, 0.0, 1.0);
        long start = System.currentTimeMillis();
        int[] a = null;
        for (int j = 0; j < 10; j++)
        {
            a = Arrays.copyOf(orig, orig.length);
            Arrays.sort(a);
        }
        long end = System.currentTimeMillis();
        System.out.println((a.length) + " " + ( (end - start) / 100.0) + "ms/Arrays.sort");
    }
}