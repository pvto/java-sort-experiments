
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
}
