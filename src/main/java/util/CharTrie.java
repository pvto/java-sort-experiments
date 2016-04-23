package util;

import java.util.ArrayList;
import java.util.List;

/** A char trie. Not thread-safe.
 *
 * @author Paavo Toivanen https://github.com/pvto
 */
public abstract class CharTrie {

    public abstract boolean match(char[] chars, int offset);
    public abstract boolean match(char c);
    public abstract void put(char[] chars, int offset);

    private int size = 0;
    protected boolean terminal = false;
    
    public boolean match(char[] chars)
    {
        return match(chars, 0);
    }
    
    public void put(char[] chars)
    {
        put(chars, 0);
        size++;
    }
    
    public int size()
    {
        return size;
    }
    
    public static CharTrie newTrie()
    {
        CharTrie root = new CharTrieRoot();
        return root;
    }
    
    public static class CharTrieRoot extends CharTrieLevel0 {

        public boolean match(char[] chars, int offset)
        {
            if (offset == chars.length && terminal)
                return true;
            char c = chars[offset];
            CharTrie child = children[c];
            if (child == null)
                return false;
            return child.match(chars, offset + 1);
        }
        
    }
    
    public static class CharTrieLevel0 extends CharTrie {
        
        public final char key;
        public CharTrie[] children = new CharTrie[0xFFFF];
        
        public CharTrieLevel0()
        {
            this.key = (char)0;
        }
        public CharTrieLevel0(char key)
        {
            this.key = key;
        }
        
        public boolean match(char c)
        {
            return key == c;
        }
        
        public boolean match(char[] chars, int offset)
        {
            if (offset == chars.length)
                return terminal;
            char c = chars[offset];
            if (offset == chars.length - 1 && terminal)
                return true;
            CharTrie child = children[c];
            if (child == null)
                return false;
            return child.match(chars, offset + 1);
        }
        
        public void put(char[] chars, int offset)
        {
            if (offset >= chars.length) {
                terminal = true;
                return;
            }
            char c = chars[offset];
            if (children[c] == null)
            {
                if (offset == 0 || offset < 2 && c < 256)
                {
                      children[c] = new CharTrieLevel0(c);
                }
                else
                {
                    children[c] = new CharTrieLevel1(c);
                }
            }
            children[c].put(chars, offset + 1);
        }
    }

    public static class CharTrieLevel1 extends CharTrie {

        public final char key;
        public List<CharTrie> children = new ArrayList<CharTrie>();
        
        public CharTrieLevel1(char key)
        {
            this.key = key;
        }
        
        public boolean match(char c)
        {
            return key == c;
        }
        
        public boolean match(char[] chars, int offset)
        {
            if (offset >= chars.length)
                return terminal;
            char c = chars[offset];
            for(CharTrie child : children)
            {
                if (child.match(chars, offset+1))
                    return true;
            }
            return false;
        }
        
        public void put(char[] chars, int offset)
        {
            if (offset >= chars.length) {
                terminal = true;
                return;
            }
            char c = chars[offset];
            for(CharTrie child : children)
            {
                if (child.match(c)) {
                    child.put(chars, offset + 1);
                    return;
                }
            }
            CharTrie child = new CharTrieLevel1(c);
            children.add(child);
            child.put(chars, offset+1);
        }
    }
    
}
