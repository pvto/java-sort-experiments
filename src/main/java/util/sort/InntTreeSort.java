
package util.sort;

import util.sort.InntTree.Node1;
import util.sort.InntTree.Node2;

/**
 *
 * @author Paavo Toivanen https://github.com/pvto
 */
public class InntTreeSort {

    public static void inntTreeSort(int[] a)
    {
        InntTree it = new InntTree();
        for(int i : a)
            it.put(i);
        
        pasteIntoArray(it, a);
    }
    
    
    public static void inntTreeHungrySort(int[] a)
    {
        int prev = a[0];
        int ind = 0;
        InntTree it = new InntTree();
        for(int i = 1; i < a.length; i++)
        {
            int t = a[i];
            if (t == prev)
                continue;
            it.put(prev, i - ind);
            ind = i;
            prev = t;
        }
        it.put(prev, a.length - ind);
     
        pasteIntoArray(it, a);
    }
    
    
    private static void pasteIntoArray(InntTree it, int[] a)
    {
        int place = 0;
        for(Node1 n1 : it.root.children) if (n1 != null)
            for(Node2 n2 : n1.children) if (n2 != null)
                for(int i = 0; i < n2.children.length; i++) 
                {
                    int val = n2.children[i];
                    for(int x = 0; x < n2.counts[i]; x++)
                    {
                        a[place++] = val;
                    }
                }
    }
    
    
    
    
    public static void smallRangeInntTreeSort(int[] a)
    {
        SmallRangeInntTree it = new SmallRangeInntTree();
        for(int i : a)
            it.put(i);
        
        pasteIntoArray(it, a);
    }
    
    
    public static void smallRangeInntTreeHungrySort(int[] a)
    {
        int prev = a[0];
        int ind = 0;
        SmallRangeInntTree it = new SmallRangeInntTree();
        for(int i = 1; i < a.length; i++)
        {
            int t = a[i];
            if (t == prev)
                continue;
            it.put(prev, i - ind);
            ind = i;
            prev = t;
        }
        it.put(prev, a.length - ind);
     
        pasteIntoArray(it, a);
    }
    
    private static void pasteIntoArray(SmallRangeInntTree it, int[] a)
    {
        int place = 0;
        Object[] oo = it.root.negativeChildren.items;
        for(int j = oo.length - 1; j >= 0; j--) 
        {
            Object o = oo[j];
            if (o == null)
                continue;
            SmallRangeInntTree.Node1 n1 = (SmallRangeInntTree.Node1)o;
            for(int i = n1.children.size() - 1; i >= 0; i--) 
            {
                int val = n1.children.get(i);
                for(int x = 0; x < n1.counts.get(i); x++)
                {
                    a[place++] = val;
                }
            }
        }
        for(Object o : it.root.children.items) if (o != null)
        {
            SmallRangeInntTree.Node1 n1 = (SmallRangeInntTree.Node1)o;
            for(int i = 0; i < n1.children.size(); i++) 
            {
                int val = n1.children.get(i);
                for(int x = 0; x < n1.counts.get(i); x++)
                {
                    a[place++] = val;
                }
            }
        }
    }
}
