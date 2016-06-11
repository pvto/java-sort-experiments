
package util;

import util.sort.ints.SmallRangeInntTree;
import org.junit.Test;
import util.sort.ints.SmallRangeInntTree;
import static org.junit.Assert.*;

/**
 *
 * @author paavoto
 */
public class SmallRangeInntTreeTest {

    @Test
    public void testNegatives()
    {
        SmallRangeInntTree sit = new SmallRangeInntTree();
        int[] data = new int[] { 1, 2, -1, -3, -65535, -65536, 65536 };
        for (int i = 0; i < data.length; i++) {
            sit.put(data[i]);
        }
        int prev = Integer.MIN_VALUE;
        Object[] oo = sit.root.negativeChildren.items;
        for(int j = oo.length - 1; j >= 0; j--) 
        {
            Object o = oo[j];
            if (o == null)
                continue;
            SmallRangeInntTree.Node1 n1 = (SmallRangeInntTree.Node1)o;
            for(int i = n1.counts.size() - 1; i >= 0; i--)
            {
                if (n1.counts.get(i) > 0)
                {
                    int x = n1.children.get(i);
                    System.out.println(x);
                    assertTrue(prev + " " + x, prev < x);
                    prev = x;
                }
            }
        }
        for(Object o : sit.root.children.items)
            if (o != null)
            {
                SmallRangeInntTree.Node1 n1 = (SmallRangeInntTree.Node1)o;
                for(int i = 0; i < n1.counts.size(); i++)
                {
                    if (n1.counts.get(i) > 0)
                    {
                        int x = n1.children.get(i);
                        System.out.println(x);
                        assertTrue(prev + " " + x, prev < x);
                        prev = x;
                    }
                }
            }

        System.out.println(sit.estimateSizeInBytes());
    }

}