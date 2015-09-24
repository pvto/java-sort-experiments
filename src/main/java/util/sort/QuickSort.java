package util.sort;

public class QuickSort {

    private static final int[] INSERTION_SHORT_THRESHOLD = new int[] {
        0, 10,
        1500000, 47
    };
    

    public static int maxIsThreshold(int arrayLength)
    {
        int ret = 10;
        for(int i = 0; i < INSERTION_SHORT_THRESHOLD.length;)
            if (arrayLength > INSERTION_SHORT_THRESHOLD[i++])
                ret = INSERTION_SHORT_THRESHOLD[i++];
            else i++;
        return ret;
    }
    
    @Deprecated
    public static int quickSort(int[] arr)
    {
        long sum = 0;
        int sample = Math.min(arr.length / 2, 20);
        for(int i = 0; i < sample; i++)
        {
            sum += arr[(int) (Math.random() * sample)];
        }
        int pivot = (int) (sum / sample),
                start = 0,
                end = arr.length - 1,
                MAX_IS = 10
                ;
        if (arr.length > 1e6)
            MAX_IS = (int) (Math.pow(arr.length, 0.5) * 0.01);
        return quickSort(arr, start, end, pivot, maxIsThreshold(arr.length));
    }
    
    @Deprecated
    public static int quickSort(int[] arr, int left, int right, int medianEstimate, int MAX_INSERTION_SORT)
    {
        int length = right - left;
        if (length < 2)
            return 0;
        
        if (length < MAX_INSERTION_SORT)
        {   // falling back to insertion sort for small subarrays;
            // this is adopted from Java 8 standard library (java.util.DualPivotQuicksort);
            // copyright for this block is Oracle's and of the authors named in java.util.DualPivotQuicksort.java
            if (left == 0)
            {
                for (int i = left, j = i; i < right; j = ++i) {
                    int base = arr[i + 1];
                    while (base < arr[j])
                    {
                        arr[j + 1] = arr[j];
                        if (j-- == left)
                        {
                            break;
                        }
                    }
                    arr[j + 1] = base;
                }
            }
            else
            {
                do {
                    if (left >= right)
                    {
                        return length;
                    }
                } while (arr[++left] >= arr[left - 1]);

                for (int i = left; ++left <= right; i = ++left)
                {
                    int a1 = arr[i], a2 = arr[left];

                    if (a1 < a2)
                    {
                        a2 = a1;
                        a1 = arr[left];
                    }
                    while (a1 < arr[--i])
                        arr[i + 2] = arr[i];
                    arr[++i + 1] = a1;

                    while (a2 < arr[--i])
                        arr[i + 1] = arr[i];
                    arr[i + 1] = a2;
                }
                int last = arr[right];

                while (last < arr[--right])
                    arr[right + 1] = arr[right];
                arr[right + 1] = last;
            }
            return length;
        }

        
        // here's my actual quick sort implementation
        int 
                s = left,
                e = right
                ;
        while(s < e)
        {
            while (s < e && arr[s] <= medianEstimate)
            {
                s++;
            }
            while(arr[e] > medianEstimate && s < e)
            {
                e--;
            }
            if (arr[s] <= arr[e])
                continue;
            int tmp = arr[s];
            arr[s] = arr[e];
            arr[e] = tmp;
        }
        
        int sum = length;
        
        while (s > left && arr[s] >= medianEstimate)
        {
            s--;
        }
        if (left < s && s < right)
            sum += quickSort(arr, left, s, (arr[left] + arr[left+1] + arr[s-1] + arr[s]) >> 2, maxIsThreshold(length));
        
        while (s < right && arr[s] <= medianEstimate)
        {
            s++;
        }
        if (s < right)
            sum += quickSort(arr, s, right, (arr[s] + arr[s+1] + arr[right-1] + arr[right]) >> 2, maxIsThreshold(length));
        
        return sum;
    }
    
}
