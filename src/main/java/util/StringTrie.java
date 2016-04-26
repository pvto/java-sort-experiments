package util;

import java.util.ArrayList;
import java.util.List;

/** An ordered, counting string trie.
 * 
 * Can't hold an empty string.
 * 
 * match() operation returns the count of identical strings already stored.
 * 
 * This implementation stores a char in 8 bit slots.
 *
 * @author Paavo Toivanen https://github.com/pvto
 */
public class StringTrie {

    public static class Bits {    public Bits[] z = new Bits[256];  public int termin = 0;  }
    
    public Bits root = new Bits();
    
    public void put(String string)
    {
        put(string.toCharArray(), 0);
    }
    
    public void put(char[] chars, int offset)
    {
        _put(root, chars, offset);
    }
    
    public static void _put(Bits root, char[] chars, int offset)
    {
        while (offset < chars.length)
        {
            int x = chars[offset];
            
            // #1
            int a = x & 0xFF;
            Bits b1 = root.z[a];
            if (b1 == null)
            {
                b1 = root.z[a] = new Bits();
            }
            
            // #2
            int b = (x >> 8) & 0xFF;
            Bits b2 = b1.z[b];
            if (b2 == null)
            {
                b2 = b1.z[b] = new Bits();
            }
            
            // #3
            int c = (x >> 16) & 0xFF;
            Bits b3 = b2.z[c];
            if (b3 == null)
            {
                b3 = b2.z[c] = new Bits();
            }

            // #4
            int d = (x >> 24) & 0xFF;
            Bits b0 = b3.z[d];
            if (b0 == null)
            {
                b0 = b3.z[d] = new Bits();
            }
            if (++offset == chars.length)
            {
                b0.termin++;
                return;
            }
            root = b0;
        }
    }
    
    public int match(String string)
    {
        return match(string.toCharArray(), 0);
    }
    
    public int match(char[] chars, int offset)
    {
        return _match(root, chars, offset);
    }
    
    public int _match(Bits root, char[] chars, int offset)
    {
        if (chars.length == 0)
        {
            return 0;
        }
        int x = chars[offset];
        while(offset < chars.length)
        {
            int a = x & 0xFF;
            Bits b1 = root.z[a];
            if (b1 == null)
            {
                return 0;
            }
            
            int b = (x >> 8) & 0xFF;
            Bits b2 = b1.z[b];
            if (b2 == null)
            {
                return 0;
            }
            
            int c = (x >> 16) & 0xFF;
            Bits b3 = b2.z[c];
            if (b3 == null)
            {
                return 0;
            }
            
            int d = (x >> 24) & 0xFF;
            Bits b0 = b3.z[d];
            if (b0 == null)
            {
                b0 = b3.z[d] = new Bits();
            }
            if (++offset == chars.length)
            {
                return b0.termin;
            }

            root = b0;
        }
        return -1; // never
    }
    
    public List<String> getOrderedStrings()
    {
        return getOrderedStrings(false);
    }
    
    public List<String> getOrderedStringsWithDuplicates()
    {
        return getOrderedStrings(true);
    }
    
    public List<String> getOrderedStrings(boolean duplicates)
    {
        List<String> res = new ArrayList<>();
        
        String cur = "";
        Bits b0 = root;
        _getOrderedStrings(res, cur, b0, duplicates);
        return res;
    }
    
    public void _getOrderedStrings(List<String> res, String cur, Bits b0, boolean duplicates)
    {
        for(int i = 0; i < 256; i++)
        {
            Bits b1 = b0.z[i];
            if (b1 != null)
            {
                for(int j = 0; j < 256; j++)
                {
                    Bits b2 = b1.z[j];
                    if (b2 != null)
                    {
                        for(int k = 0; k < 256; k++)
                        {
                            Bits b3 = b2.z[k];
                            if (b3 != null)
                            {
                                for(int m = 0; m < 256; m++)
                                {
                                    Bits b02 = b3.z[m];
                                    if (b02 != null)
                                    {
                                        char c = (char)(i | (j << 8) | (k << 16) | (m << 24));
                                        String d = cur + c;
                                        if (b02.termin > 0)
                                        {
                                            int n = (duplicates ? b02.termin : 1);
                                            for(int x = 0; x < n; x++)
                                                res.add(d);
                                        }
                                        _getOrderedStrings(res, d, b02, duplicates);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
