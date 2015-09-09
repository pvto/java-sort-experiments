package util;


/** A thin wrapper around Object[].  Works the same way as java.util.ArrayList
 * does, expanding by the factor of two until put(index) fits in.
 * 
 * put() expands automatically, failing only with negative indices and on
 * OutOfMemoryError.
 * 
 * get() returns null for indices gte array length, failing only for negative
 * indices.
 *
 * @author Paavo Toivanen https://github.com/pvto
 */
public class ExpandingArray<T> {
        
    public Object[] items;
    public ExpandingArray(int n)
    {
        items = new Object[n];
    }
    public void put(int ind, Object o)
    {
        if (items.length > ind)
        {
            items[ind] = o;
            return;
        }
        int size = items.length << 1;
        while(size <= ind) 
        {
            size <<= 1;
        }
        Object[] newItems = new Object[size];
        System.arraycopy(items, 0, newItems, 0, items.length);
        items = newItems;
        items[ind] = o;
    }
    public T get(int ind)
    {
        if (ind >= items.length)
            return null;
        return (T)items[ind];
    }
    public int size()
    {
        return items.length;
    }
    
    
    /** A thin wrapper around int[].  Works the same way as java.util.ArrayList
     * does, expanding by the factor of two until put(index) fits in.
     * 
     * put() expands automatically, failing only with negative indices, and on
     * OutOfMemoryError.
     * 
     * get() returns DEFAULT_VALUE (=0 if not specified in constructor) 
     * for indices gte array length.  Fails only for negative indices.
     *
     * @author Paavo Toivanen https://github.com/pvto
     */
    public static class ExpandingIntArray {
        
        public int[] items;
        public final int DEFAULT_VALUE;
        public ExpandingIntArray(int n)
        {
            items = new int[n];
            DEFAULT_VALUE = 0;
        }
        public ExpandingIntArray(int n, int DEFVALUE)
        {
            items = new int[n];
            DEFAULT_VALUE = DEFVALUE;
        }
        public void put(int ind, int o)
        {
            if (items.length > ind)
            {
                items[ind] = o;
                return;
            }
            int size = items.length << 1;
            while(size <= ind) 
            {
                size <<= 1;
            }
            int[] newItems = new int[size];
            System.arraycopy(items, 0, newItems, 0, items.length);
            if (DEFAULT_VALUE != 0)
            {
                for(int i = items.length; i < ind; i++)
                {
                    newItems[i] = DEFAULT_VALUE;
                }
            }
            items = newItems;
            items[ind] = o;
        }
        public int get(int ind)
        {
            if (ind >= items.length)
                return 0;
            return items[ind];
        }
        public int size()
        {
            return items.length;
        }
        public void increment(int ind)
        {
            if (ind >= items.length)
            {
                put(ind, 1);
                return;
            }
            items[ind]++;
        }
        public void add(int ind, int val)
        {
            if (ind >= items.length)
            {
                put(ind, val);
                return;
            }
            items[ind] += val;
        }
        
    }
    
    // public static class ExpandingFloatArray . . .
}
    
