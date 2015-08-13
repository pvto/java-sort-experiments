package util.sort;

import org.junit.Test;

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
    }

    @Test
    public void testBS1e6_010()
    {
        int[] t = new int[10000000];
        for(int i = 0; i < t.length; i++)
            t[i] = i;
        for(int i = 0; i < t.length / 10; i++)
        {
            int x = (int)Math.random() * t.length;
        }

        long start = System.currentTimeMillis();
        BleedSort.bleedSort(t);
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("elapsed " + elapsed);
    }

}
