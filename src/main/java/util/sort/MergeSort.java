package util.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

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
        int size = 7;
        int i = 0;
        while(i <= b.length - size)
        {
            //int c=b[i],d=b[i+1],e=b[i+2],f=b[i+3],g=b[i+4],h=b[i+5],m=b[i+6];
            int c=b[i++],d=b[i++],e=b[i++],f=b[i++],g=b[i++],h=b[i++],m=b[i++];
            if(d>e){int t=d;d=e;e=t;}  if(f>g){int t=f;f=g;g=t;};  if(h>m){int t=h;h=m;m=t;};
            if(c>e){int t=c;c=e;e=t;}  if(f>h){int t=f;f=h;h=t;};  if(g>m){int t=g;g=m;m=t;};
            if(c>d){int t=c;c=d;d=t;}  if(g>h){int t=g;g=h;h=t;};  if(e>m){int t=e;e=m;m=t;};
            if(c>g){int t=c;c=g;g=t;}  if(d>h){int t=d;d=h;h=t;};
            if(c>f){int t=c;c=f;f=t;}  if(e>h){int t=e;e=h;h=t;};
            if(d>f){int t=d;d=f;f=t;}  if(e>g){int t=e;e=g;g=t;};
            if(e>f){int t=e;e=f;f=t;}
            //b[i]=c;b[i+1]=d;b[i+2]=e;b[i+3]=f;b[i+4]=g;b[i+5]=h;b[i+6]=m; i += size;
            i-=7;b[i++]=c;b[i++]=d;b[i++]=e;b[i++]=f;b[i++]=g;b[i++]=h;b[i++]=m;
//            int e = i + size;
//            for(int m = i + 1; m < e; m++)
//            {
//                int x = b[m];
//                if (x >= b[m - 1])
//                    continue;
//                int j = i;
//                while(b[j] <= x) 
//                    j++;
//                for(int k = m; k > j; k--)
//                    b[k] = b[k-1];
//                b[j] = x;
//            }
//            insertionSort(b, i, i + size);
        }
        if (i < b.length - 1)
        {
            insertionSort(b, i, b.length);
        }

        
        while(size <= b.length >>> 1)
        {
            int xee = b.length - size;
            int ind = 0;
            int x = 0;
            for(; x < xee; x += size)
            {
                int y = x + size;
                int xe = y;
                int ye = y + size; if (ye > b.length) ye = b.length;
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
            b = a; 
        }
        if (orig != b)
        {
            System.arraycopy(b, 0, orig, 0, orig.length);
        }
    }
    
    public static void insertionSort(int[] a, int i, int e)
    {
        for(int m = i + 1; m < e; m++)
        {
            int x = a[m];
            if (x >= a[m - 1])
                continue;
            int j = i;
            while(a[j] <= x) 
                j++;
            for(int k = m; k > j; k--)
                a[k] = a[k-1];
            a[j] = x;
        }
    }
    
    public static void swap2(int[]a, int i, int j)
    {
        if(a[j] < a[i])
        {
            int tmp = a[i];  a[i] = a[j];  a[j] = tmp;
        }
    }
    public static void swap7(int[] a, int s)
    {
        swap2(a,s+1,s+2); swap2(a,s+3,s+4);  swap2(a,s+5,s+6);
        swap2(a,s,s+2); swap2(a,s+3,s+5);  swap2(a,s+4,s+6);
        swap2(a,s,s+1); swap2(a,s+4,s+5);  swap2(a,s+2,s+6);
        swap2(a,s,s+4); swap2(a,s+1,s+5);
        swap2(a,s,s+3); swap2(a,s+2,s+5);
        swap2(a,s+1,s+3); swap2(a,s+2,s+4);
        swap2(a,s+2,s+3);
    }
    

    
    
    
    
    public static void naiveMonotonousMergeSort(int[] a)
    {
        ArrayList<int[]> mono = getMonotonousSegmentsNaive(a);
        // temporary space for merge sort
        int[] b = new int[a.length];
        
        while(mono.size() > 1)
        {
            // we now have a list of monotonous sequences; order it by (length, min_value)
            Collections.sort(mono, monoSegComparator);
            // on each iteration, we get a new halved list of mergeable segments... it will become mono
            ArrayList<int[]> monoTmp = new ArrayList<int[]>((mono.size()>>>1)+1);
            // now proceed to merge segments
            int mergeInd = 0;
            int ind = 0;
            while(mergeInd < mono.size() - 1)
            {
                int xind = ind;
                int[] A = mono.get(mergeInd++);
                int[] B = mono.get(mergeInd++);
                if (A[5] == 1 && B[5] == 1) {   // both segments are increasing
                    int i = A[3], ie = A[4], j = B[3], je = B[4];
                    for(;;) {
                        if (a[j] < a[i]) { b[ind++] = a[j++];
                            if (j == je) { do { b[ind++] = a[i++]; } while(i < ie);  break; }
                        } else { b[ind++] = a[i++];
                            if (i == ie) { do { b[ind++] = a[j++]; } while(j < je);  break; }
                        }
                    }
                } else if (A[5] == 1 && B[5] == -1) {   // inc,dec
                    int i = A[3], ie = A[4], j = B[4], je = B[3];
                    for(;;) {
                        if (a[j] < a[i]) { b[ind++] = a[j--];
                            if (j == je) { do { b[ind++] = a[i++]; } while(i < ie);  break; }
                        } else { b[ind++] = a[i++];
                            if (i == ie) { do { b[ind++] = a[j--]; } while(j > je);  break; }
                        }
                    }                    
                } else if (A[5] == -1 && B[5] == 1) {   // dec,inc
                    int i = A[4], ie = A[3], j = B[3], je = B[4];
                    for(;;) {
                        if (a[j] < a[i]) { b[ind++] = a[j++];
                            if (j == je) { do { b[ind++] = a[i--]; } while(i > ie);  break; }
                        } else { b[ind++] = a[i--];
                            if (i == ie) { do { b[ind++] = a[j++]; } while(j < je);  break; }
                        }
                    }
                } else {    // dec,dec
                    int i = A[4], ie = A[3], j = B[4], je = B[3];
                    for(;;) {
                        if (a[j] < a[i]) { b[ind++] = a[j--];
                            if (j == je) { do { b[ind++] = a[i--]; } while(i > ie);  break; }
                        } else { b[ind++] = a[i--];
                            if (i == ie) { do { b[ind++] = a[j--]; } while(j > je);  break; }
                        }
                    }
                }
                monoTmp.add(new int[]{b[xind], b[ind-1], ind-xind, xind, ind, 1});
            }
            if (mergeInd < mono.size())
            {
                int[] C = mono.get(mergeInd);
                if (C[3] == ind)
                {
                    monoTmp.add(C);
                }
                else
                {
                    System.arraycopy(a, C[3], a, ind, C[2]);
                    monoTmp.add(new int[]{C[0], C[1], C[2], C[5]==1?ind:ind-1, C[5]==1?a.length:a.length-1, C[5]});
                }
            }
            System.arraycopy(b, 0, a, 0, ind);
            mono = monoTmp;
        }
    }

    public static ArrayList<int[]> getMonotonousSegmentsNaive(int[] a)
    {
        ArrayList<int[]> mono = new ArrayList<>();
        int start = 0;
        int x = a[start];
        int increasing = 0;
        for(int i = 1; i < a.length; i++)
        {
            int y = a[i];
            if (increasing == 1)
            {
                if (y >= x)
                {
                    x = y;
                    continue;
                }
                mono.add(new int[]{a[start], a[i-1], i-start, start, i, 1});
                start = i;
                increasing = 0;
            }
            else if (increasing == -1)
            {
                if (y <= x)
                {
                    x = y; 
                    continue;
                }
                mono.add(new int[]{a[i-1], a[start], i-start, start-1, i-1, -1});
                start = i;
                increasing = 0;
            }
            else
            {
                increasing = (y < x ? -1 : (y > x ? 1 : 0));
            }
            x = y;
        }
        if (increasing >= 0)
        {
            mono.add(new int[]{a[start], a[a.length - 1], a.length - start, start, a.length, 1});
        }
        else
        {
            mono.add(new int[]{a[a.length - 1], a[start], a.length - start, start-1, a.length-1, -1});
        }
        return mono;
    }
    
    
    public static Comparator<int[]> monoSegComparator = new Comparator<int[]>() {

        @Override
        public int compare(int[] o1, int[] o2)
        {
            if (o1[2] < o2[2]) return -1;
            if (o1[2] > o2[2]) return 1;
            if (o1[0] < o2[0]) return -1;
            if (o1[0] > o2[0]) return 1;
            return 0;
        }
        
    };
    
    
    
    public static void main(String[] args)
    {
                             // 0           6       10              18    21
        int[] monos = new int[]{1,2,3,4,4,4,3,2,1,0,1,2,3,4,4,4,4,5,4,5,6,3};
        //1 4 6 0 6 1
        //0 3 4 5 9 -1
        //1 5 8 10 18 1
        //4 6 3 18 21 1
        //3 3 1 21 22 1
        ArrayList<int[]> segs = MergeSort.getMonotonousSegmentsNaive(monos);
        for(int[] seg : segs)
            System.out.println(Arrays.toString(seg));
        System.out.println(Arrays.toString(monos));
        MergeSort.naiveMonotonousMergeSort(monos);
        System.out.println(Arrays.toString(monos));
    }
    
}
