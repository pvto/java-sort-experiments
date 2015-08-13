package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author pvto https://github.com/pvto
 */
public class Int {

    static public int sign(int x)
    {
        if (x < 0)
            return -1;
        return 1;
    }

    static public List<Integer> range(int start, int end)
    {
        List<Integer> res = new ArrayList<>(end - start);
        for(int i = start; i < end; i++) { res.add(i); }
        return res;
    }

    static public int min(int a, int b, int c)
    {
        if (a < b)
        {
            return java.lang.Math.min(a, c);
        }
        return java.lang.Math.min(b, c);
    };

    static public int min(int[] A)
    {
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < A.length; i++)
        {
            if (A[i] < min) { min = A[i]; }
        }
        return min;
    }

    static public int max(int[] A)
    {
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < A.length; i++)
        {
            if (A[i] > max) { max = A[i]; }
        }
        return max;
    }
    
    static public int count(int[] A, int val)
    {
        int count = 0;
        for(int i = 0; i < A.length; i++)
        {
            if (A[i] == val) { count++; }
        }
        return count;
    }
    
    static public List<Integer> uniq(int[] list)
    {
        int[] ordered = Arrays.copyOf(list, list.length);
        Arrays.sort(ordered);
        List<Integer> res = new ArrayList<Integer>();
        int next;
        res.add(next = ordered[0]);
        for(int i = 0; i < ordered.length; i++)
        {
            if (ordered[i] != next)
            {
                res.add(next = ordered[i]);
            }
        }
        return res;
    }
    
    static public int irand(int width)
    {
        double rnd = Math.random();
        return (int)(rnd * width);
    }
    
    static public int[] fill(int arraySize, int val)
    {
        int[] tmp = new int[arraySize];
        Arrays.fill(tmp, val);
        return tmp;
    }
}
