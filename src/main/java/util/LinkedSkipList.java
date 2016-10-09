package util;

import java.util.Arrays;

/**
 *
 * @author Paavo Toivanen https://github.com/pvto
 * @param <T> element contained by this list
 */
public class LinkedSkipList<T extends Comparable> {
    
    public final int BASE;

    public LinkedSkipList(int base) { this.BASE = base; init(); }
    public LinkedSkipList() { this.BASE = 10; init(); }
    
    
    public void init()
    {
    }
    
    public static class LisNode<T extends Comparable> {
        LisNode[] next;
        T value;
        public LisNode(int level)
        {
            next = new LisNode[level];
        }
        public void expand(int delta)
        {
            next = Arrays.copyOf(next, next.length + delta);
        }
    }
    
    public LisNode first = new LisNode(3);
    
    public void add(T t)
    {
        LisNode node = new LisNode(1);
        node.value = t;
        if (first == null)
        {
            first = node;
            return;
        }
        LisNode cursor = first;
        it: for(;;)
        {
            for(int i = cursor.next.length - 1; i >= 0; i--)
            {
                if (cursor.next[i] != null && cursor.next[i].value.compareTo(t) <= 0)
                {
                    cursor = cursor.next[i];
                    break;
                }
                break it;
            }
        }

    }
}
