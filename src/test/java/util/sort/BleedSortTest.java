package util.sort;

import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

public class BleedSortTest {
    
    @Test
    public void testBS1e6()
    {
        int[] t = new int[10000000];
        for(int i = 0; i < t.length; i++)
            t[i] = (int) (Math.random() * t.length);

        long start = System.currentTimeMillis();
        BleedSort.bleedSort(t);
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("elapsed " + elapsed);
        if (t.length < 1000)
            System.out.println(Arrays.toString(t));
        System.out.println(String.format(
                "actual min %d max %d",
                t[0], t[t.length - 1]
        ));
    }
    
}
