
package util.sort.strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import util.Ints;

public class StringTrieSortTest {

    public static String randomString(char from, char to, int minLength, int maxLength)
    {
        StringBuilder bd = new StringBuilder();
        int n = minLength + Ints.irand(maxLength - minLength);
        for(int i = 0; i < n; i++)
        {
            char ch = (char)((int)from + Ints.irand((int)to - (int)from));
            bd.append(ch);
        }
        return bd.toString();
    }
    
    @Test
    public void testSort()
    {
        List<Long> spans = new ArrayList<Long>();
        List<Long> arrSortSpans = new ArrayList<Long>();
        int SAMPLE = 10000;
        for(int n = 0; n < 100; n++)
        {
            List<String> sample = new ArrayList<String>();
            for(int i = 0; i < SAMPLE; i++)
                sample.add(randomString('a','c',1,6));
            long start = System.currentTimeMillis();
            new StringTrieSort().sort(sample);
            long end = System.currentTimeMillis();
            long span = end - start;
            start = end;
            Collections.sort(sample);
            end = System.currentTimeMillis();
            long span2 = end - start;
            spans.add(span);
            arrSortSpans.add(span2);
            
            System.out.println(span + " / " + span2);
        }
        double avg = Ints.sum(spans) / (double)spans.size();
        System.out.println(String.format("String trie sort: n %d avg %f ", spans.size(), avg));
        double avg2 = Ints.sum(arrSortSpans) / (double)arrSortSpans.size();
        System.out.println(String.format("Arrays sort: n %d avg %f ", arrSortSpans.size(), avg2));
    }

}