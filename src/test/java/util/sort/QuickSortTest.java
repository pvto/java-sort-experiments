package util.sort;

import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

public class QuickSortTest {
    

    @Test
    public void testBasic()
    {
        int[][] foo = new int[][] {
                {3,2,4,1,6,0, -1,7,8,-2,10,15,14,12,13},
                {1,2,3,4,5,6,7,8,9,10,12,11,13,14,15,16},
                {19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,0}
        }
        ;
        for(int x = 0; x < foo.length; x++)
        {
            int n = QuickSort.quickSort(foo[x]);
            for (int i = 1; i < foo.length; i++) {
                assertTrue(foo[x][i] <= foo[x][i+1]);
            }
        }
    }
    
    @Test
    public void testQS1e6U()
    {
        int[][] foo = new int[2][1000000];
        
        for(int x = 0; x < foo.length; x++)
            for(int i = 0; i < foo[x].length; i++)
                foo[x][i] = (int) (Math.random() * foo[x].length);
        
        long[] totals = new long[]{0,0};
        String[] DESCR = {"Arrays.sort", "my.QuickSort"};
        for (int x = 0; x < foo.length; x++)
        {
            long s = System.currentTimeMillis();
            switch(x)
            {
                case 0: Arrays.sort(foo[x]);  break;
                case 1: QuickSort.quickSort(foo[x]);  break;
            }
            
            totals[x] += System.currentTimeMillis() - s;
            System.out.println(String.format("1e6-U [%s] %dms", DESCR[x], totals[x]));
            if (x > 0)
            {   // sanity check: a large enough array in U; QuickSort is never twice slower than Arrays.sort
                assertTrue(totals[x] < totals[x - 1] * 2);
            }
        }

    }
}
