package util.sort;

import java.util.Arrays;

/**
 *
 * @author Paavo Toivanen https://github.com/pvto
 */
public class RadixSort {

    public static void radix8(int[] a)
    {
        int FULL = 256;
        int LAST = FULL - 1;
        int HALF = FULL>>1;
        int[] counts = new int[FULL];
        int[] tmp = new int[a.length];
        int[] foo;
        
        // order by count according to first byte
        for (int i = 0; i < a.length; i++) { counts[a[i] & LAST]++; }
        for (int i = 1; i < FULL; i++) { counts[i] += counts[i - 1]; }
        for (int i = tmp.length - 1; i >= 0; i--) { tmp[--counts[a[i] & LAST]] = a[i]; }
        foo = a;  a = tmp;  tmp = foo;  counts = new int[FULL];

        // second byte
        for (int i = 0; i < a.length; i++) { counts[(a[i] >> 8) & LAST]++; }
        for (int i = 1; i < FULL; i++) { counts[i] += counts[i - 1]; }
        for (int i = tmp.length - 1; i >= 0; i--) { tmp[--counts[(a[i] >> 8) & LAST]] = a[i]; }
        foo = a;  a = tmp;  tmp = foo;  counts = new int[FULL];

        // third byte
        for (int i = 0; i < a.length; i++) { counts[(a[i] >> 16) & LAST]++; }
        for (int i = 1; i < FULL; i++) { counts[i] += counts[i - 1]; }
        for (int i = tmp.length - 1; i >= 0; i--) { tmp[--counts[(a[i] >> 16) & LAST]] = a[i]; }
        foo = a;  a = tmp;  tmp = foo;  counts = new int[FULL];

        // fourth byte with special negative handling
        for (int i = 0; i < a.length; i++) { counts[(a[i] >> 24) & LAST]++; }
        for (int i = 1; i < FULL; i++) { counts[i] += counts[i - 1]; }
        int add = counts[HALF - 1];
        for(int i = HALF; i < FULL; i++) { counts[i] -= add; }
        for(int i = 0; i < HALF; i++) { counts[i] += counts[LAST]; }
        for (int i = tmp.length - 1; i >= 0; i--) { tmp[--counts[(a[i] >> 24) & LAST]] = a[i]; }
    }

    
    
    public static void radix16(int[] a)
    {
        int FULL = 256*256;
        int LAST = FULL - 1;
        int HALF = FULL>>1;
        int[] counts = new int[FULL];
        int[] tmp = new int[a.length];
        int[] foo;
        
        // order by count according to first two bytes
        for (int i = 0; i < a.length; i++) { counts[a[i] & LAST]++; }
        for (int i = 1; i < FULL; i++) { counts[i] += counts[i - 1]; }
        for (int i = tmp.length - 1; i >= 0; i--) { tmp[--counts[a[i] & LAST]] = a[i]; }
        foo = a;  a = tmp;  tmp = foo;  counts = new int[FULL];

        // third and fourth bytes
        // with special negative handling
        for (int i = 0; i < a.length; i++) { counts[(a[i] >> 16) & LAST]++; }
        for (int i = 1; i < FULL; i++) { counts[i] += counts[i - 1]; }
        int add = counts[HALF - 1];
        for(int i = HALF; i < FULL; i++) { counts[i] -= add; }
        for(int i = 0; i < HALF; i++) { counts[i] += counts[LAST]; }
        for (int i = tmp.length - 1; i >= 0; i--) { tmp[--counts[(a[i] >> 16) & LAST]] = a[i]; }
    }
    
    public static void radix11(int[] a)
    {
        int FULL = 256*8;
        int LAST = FULL - 1;
        int LAST10 = (FULL >> 1) - 1;
        int HALF = FULL>>1;
        int[] counts = new int[FULL];
        int[] counts2 = new int[FULL];
        int[] counts3 = new int[FULL];
        int[] tmp = new int[a.length];
        int[] foo;
        
        // order by count according to first (LSB) 11 bits
        for (int i = 0; i < a.length; i++) { 
            counts[a[i] & LAST]++;
            counts2[(a[i] >> 11) & LAST]++;
            counts3[(a[i] >> 22) & LAST10]++;
        }
        for (int i = 1; i < FULL; i++) { counts[i] += counts[i - 1]; }
        for (int i = tmp.length - 1; i >= 0; i--) { tmp[--counts[a[i] & LAST]] = a[i]; }
        foo = a;  a = tmp;  tmp = foo;

        // order by count according to next 11 bits
        for (int i = 1; i < FULL; i++) { counts2[i] += counts2[i - 1]; }
        for (int i = tmp.length - 1; i >= 0; i--) { tmp[--counts2[(a[i] >> 11) & LAST]] = a[i]; }
        foo = a;  a = tmp;  tmp = foo;

        
        // remaining 10 bytes
        // with special negative handling
        for (int i = 1; i < HALF; i++) { counts3[i] += counts3[i - 1]; }
        int add = counts3[(HALF>>1) - 1];
        for(int i = HALF>>1; i < HALF; i++) { counts3[i] -= add; }
        for(int i = 0; i < HALF>>1; i++) { counts3[i] += counts3[LAST10]; }
        for (int i = tmp.length - 1; i >= 0; i--) { tmp[--counts3[(a[i] >> 22) & LAST10]] = a[i]; }
        
        for(int i = 0; i < tmp.length; i++) a[i] = tmp[i];
    }    
    
    public static void main(String[] args) {
        int[] a = new int[]{Integer.MIN_VALUE, 0, 256, -256, 15, 257, 9, -1, -2};
        radix11(a);
        System.out.println(Arrays.toString(a));
    }
}
