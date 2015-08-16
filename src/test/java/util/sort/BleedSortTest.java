package util.sort;

import org.junit.Ignore;
import org.junit.Test;
import util.Prob;

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

    @Test
    public void testBS1e4bin1e3()
    {
        int[] t = new int[10000];
        for(int i = 0; i < t.length; i++)
            t[i] = Prob.binmRnd(100, 0.5, Math.random());
        long start = System.currentTimeMillis();
        BleedSort.bleedSort(t);
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("bin1e4 elapsed " + elapsed);
    }
    
    @Ignore
    @Test
    public void testBS1e5bin1e5()
    {
        int[] t = new int[100000];
        for(int i = 0; i < t.length; i++)
            t[i] = Prob.binmRnd(100000, 0.5, Math.random());
        long start = System.currentTimeMillis();
        BleedSort.bleedSort(t);
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("bin1e5 elapsed " + elapsed);
    }
}
