package util.sort.ints;

/**
 *
 * @author Paavo Toivanen https://github.com/pvto
 */
public class BubbleSort {

    public static void leftBubbleSort(int[] a)
    {
        System.out.println("bobble!");
        for(int i = 1; i < a.length - 1; i++)
        {
            int j = i - 1;
            while(a[j] > a[i] && j > 0) j--;
            if(a[i] > a[j])
            {
                int tmp = a[i];
                a[i] = a[j];
                a[j] = tmp;
            }
        }
    }
}
