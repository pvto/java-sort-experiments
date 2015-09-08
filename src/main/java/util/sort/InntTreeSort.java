
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
        int last = a[0];
        int ind = 0;
        InntTree it = new InntTree();
        for(int i = 1; i < a.length; i++)
        {
            int t = a[i];
            if (t == last)
                continue;
            it.put(last, i - ind);
            ind = i;
            last = t;
        }
        it.put(last, a.length - ind);
     
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
        int last = a[0];
        int ind = 0;
        SmallRangeInntTree it = new SmallRangeInntTree();
        for(int i = 1; i < a.length; i++)
        {
            int t = a[i];
            if (t == last)
                continue;
            it.put(last, i - ind);
            ind = i;
            last = t;
        }
        it.put(last, a.length - ind);
     
        pasteIntoArray(it, a);
    }
    
    private static void pasteIntoArray(SmallRangeInntTree it, int[] a)
    {
        int place = 0;
        for(Object o : it.root.negativeChildren.items) if (o != null)
        {
            SmallRangeInntTree.Node1 n1 = (SmallRangeInntTree.Node1)o;
            for(int i = 0; i < n1.children.length; i++) 
            {
                int val = n1.children[i];
                for(int x = 0; x < n1.counts[i]; x++)
                {
                    a[place++] = val;
                }
            }
        }
        for(Object o : it.root.children.items) if (o != null)
        {
            SmallRangeInntTree.Node1 n1 = (SmallRangeInntTree.Node1)o;
            for(int i = 0; i < n1.children.length; i++) 
            {
                int val = n1.children[i];
                for(int x = 0; x < n1.counts[i]; x++)
                {
                    a[place++] = val;
                }
            }
        }
    }
}
