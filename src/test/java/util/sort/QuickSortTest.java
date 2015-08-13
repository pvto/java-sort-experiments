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
            {   // sanity check: QuickSort is never twice slower than Arrays.sort, with a large enough array in U
                assertTrue(totals[x] < totals[x - 1] * 2);
            }
        }

    }

    
/*
#n=1e6
> a = c(111,169,171,201,129,191,127,156,153,254)
> sum(a)/length(a)
[1] 166.2
> b = c(136,134,139,143,151,135,163,136,172,171)
> sum(b)/length(b)
[1] 148
    
#n=1e7
> a2 = c(980,949,1002,947,1004,1005,992,1065,1021,971)
> mean(a2)
[1] 993.6
> b2 = c(1295,1347,1262,1266,1299,1412,1322,1309,1314,1300)
> mean(b2)
[1] 1312.6
# implement insertion sort for n < 47
> b2b = c(1083,1103,1067,1089,1088,1155,1121,1077,1070,1100)
> mean(b2b)
[1] 1095.3
# narrow insertion sort threshold to 10
> bb = c(145,152,150,153,143,145,123,154,120,123)
> mean(bb)
[1] 140.8
*/

}
