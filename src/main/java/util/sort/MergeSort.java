package util.sort;

import java.util.Arrays;

/**
 * @author Paavo Toivanen https://github.com/pvto
 */
public class MergeSort {
    // 16 15 0F  05 0a F  
    // 17 16 10  00 10 T
    public static int log2(int val)
    {
        int rounds = -1, sz = val;
        while(sz > 0) { rounds++; sz >>>= 1; }
        return rounds;
    }
    
    public static void mergeSort(int[] a)
    {
        int[] orig = a;
        int[] b = Arrays.copyOf(a, a.length);
        int size = 8;
        int i = 0;
        for(; i <= b.length - size; i += size)
        {
            insertionSort(b, i, i + size);
        }
        if (i < b.length - 1)
        {
            insertionSort(b, i, b.length);
        }

        
        
        while(size <= b.length >>> 1)
        {
            int ind = 0;
            int x = 0;
            for(; x < b.length - (size); x += size)
            {
                int y = x + size;
                int xe = y;
                int ye = Math.min(b.length, y + size);
                for(;;)
                {
                    if (b[x] <= b[y])
                    {
                        a[ind++] = b[x++];
                        if (x == xe)
                        {
                            while(y < ye) a[ind++] = b[y++];
                            break;
                        }
                    }
                    else
                    {
                        a[ind++] = b[y++];
                        if (y == ye)
                        {
                            while(x < xe) a[ind++] = b[x++];
                            break;
                        }
                    }
                }
            }
            while (ind < b.length)
            {
                a[ind++] = b[x++];
            }
            size <<= 1;
            int[] tmp = b;  b = a;  a = tmp;
        }

        if (size < b.length)
        {
            int x = 0;
            int y = x + size;
            int xe = y;
            int ye = b.length;
            int ind = 0;
            for(;;)
            {
                if (b[x] <= b[y])
                {
                    a[ind++] = b[x++];
                    if (x == xe)
                    {
                        while(y < ye) a[ind++] = b[y++];
                        break;
                    }
                }
                else
                {
                    a[ind++] = b[y++];
                    if (y == ye)
                    {
                        while(x < xe)
                        {
                            a[ind++] = b[x++];
                        }
                        break;
                    }
                }
            }
//            System.out.println("F " + Arrays.toString(a));
            b = a; 
        }
        if (orig != b)
        {
            System.arraycopy(b, 0, orig, 0, orig.length);
        }
    }
    
    public static void insertionSort(int[] a, int s, int e)
    {
        for(int i = s + 1; i < e; i++)
        {
            int x = a[i];
            if (x >= a[i - 1])
                continue;
            int j = s;
            while(a[j] <= x) 
                j++;
            for(int k = i; k > j; k--)
                a[k] = a[k-1];
            a[j] = x;
        }
    }
    
    
    public static void main(String[] args) {
        System.out.println(MergeSort.log2(15));
        System.out.println(MergeSort.log2(16));
        System.out.println(MergeSort.log2(17));
        System.out.println(MergeSort.log2(31));
        System.out.println(MergeSort.log2(32));
        System.out.println(MergeSort.log2(33));
    }
            
    
}
