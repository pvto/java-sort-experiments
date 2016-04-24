package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** A char trie. Not thread-safe.
 *
 * @author Paavo Toivanen https://github.com/pvto
 */
public abstract class CharTrie implements Comparable<CharTrie> {

    public abstract boolean match(char[] chars, int offset);
    public abstract boolean match(char c);
    public abstract void put(char[] chars, int offset);
    public abstract CharTrie getChild(char c);

    public final char key;
    private int size = 0;
    protected boolean terminal = false;
    
        
    public CharTrie()
    {
        this.key = (char)0;
    }
    public CharTrie(char key)
    {
        this.key = key;
    }

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
    
    public static CharTrie newOrderedTrie()
    {
        CharTrie root = new OrderedCharTrieRoot();
        return root;
    }
    
    @Override
    public int compareTo(CharTrie x)
    {
        if (x.key > this.key) return -1;
        else if (x.key < this.key) return 1;
        return 0;
    }
    
    public static class DummyCharTrie extends CharTrie {
        
        public DummyCharTrie(char key)
        {
            super(key);
        }

        @Override
        public boolean match(char[] chars, int offset) {
            throw new UnsupportedOperationException("Dummy.");
        }

        @Override
        public boolean match(char c) {
            throw new UnsupportedOperationException("Dummy.");
        }

        @Override
        public void put(char[] chars, int offset) {
            throw new UnsupportedOperationException("Dummy.");
        }

        @Override
        public CharTrie getChild(char c) {
            throw new UnsupportedOperationException("Dummy.");
        }
    }
    
    public static class CharTrieRoot extends CharTrieLevel0 {

        @Override
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
    
    public static class OrderedCharTrieRoot extends OrderedCharTrieLevel0 {

        @Override
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
        
        public CharTrie[] children = new CharTrie[0xFFFF];
        private int maxChild = 0; // optimization
        
        public CharTrieLevel0()
        {
            super();
        }
        public CharTrieLevel0(char key)
        {
            super(key);
        }
        
        @Override
        public boolean match(char c)
        {
            return key == c;
        }
        
        @Override
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
        
        @Override
        public void put(char[] chars, int offset)
        {
            if (offset >= chars.length) {
                terminal = true;
                return;
            }
            char c = chars[offset];
            if (children[c] == null)
            {
                children[c] = createNewChild(offset, c);
                maxChild = Math.max(maxChild, (int)c);
            }
            children[c].put(chars, offset + 1);
        }
        
        public CharTrie createNewChild(int offset, char c)
        {
            if (offset == 0 || offset < 2 && c < 256)
            {
                  return new CharTrieLevel0(c);
            }
            return new CharTrieLevel1(c);
        }

        @Override
        public CharTrie getChild(char c)
        {
            return children[c];
        }
    }
    
    public static class OrderedCharTrieLevel0 extends CharTrieLevel0 {
        
        public OrderedCharTrieLevel0()
        {
            super();
        }
        
        public OrderedCharTrieLevel0(char key)
        {
            super(key);
        }
        
        @Override
        public CharTrie createNewChild(int offset, char c)
        {
            if (offset == 0 || offset < 2 && c < 256)
            {
                  return new OrderedCharTrieLevel0(c);
            }
            return new OrderedCharTrieLevel1(c);
        }
    }

    public static class CharTrieLevel1 extends CharTrie {

        public List<CharTrie> children = new ArrayList<CharTrie>();
        
        public CharTrieLevel1(char key)
        {
            super(key);
        }
        
        @Override
        public boolean match(char c)
        {
            return key == c;
        }
        
        @Override
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
        
        @Override
        public void put(char[] chars, int offset)
        {
            if (offset >= chars.length)
            {
                terminal = true;
                return;
            }
            char c = chars[offset];
            for(CharTrie child : children)
            {
                if (child.match(c))
                {
                    child.put(chars, offset + 1);
                    return;
                }
            }
            CharTrie child = new CharTrieLevel1(c);
            children.add(child);
            child.put(chars, offset+1);
        }
        
        @Override
        public CharTrie getChild(char c)
        {
            for(CharTrie child : children)
            {
                if (child.match(c))
                {
                    return child;
                }
            }
            return null;
        }
    }
    
    public static class OrderedCharTrieLevel1 extends CharTrieLevel1 {
        
        public OrderedCharTrieLevel1(char key)
        {
            super(key);
        }
        
        public boolean match(char[] chars, int offset)
        {
            if (offset >= chars.length)
                return terminal;
            char c = chars[offset];
            int index = Collections.binarySearch(children, new DummyCharTrie(c));
            if (index < 0)
                return false;
            CharTrie child = children.get(index);
            boolean ret = child.match(chars, offset+1);
            return ret;
        }
        
        public void put(char[] chars, int offset)
        {
            if (offset >= chars.length) {
                terminal = true;
                return;
            }
            char c = chars[offset];
            CharTrie child = new CharTrieLevel1(c);
            int index = Collections.binarySearch(children, new DummyCharTrie(c));
            if (index >= 0)
            {
                children.set(index, child);
            }
            else
            {
                children.add(-index - 1, child);
            }
            child.put(chars, offset + 1);
        }
        @Override
        public CharTrie getChild(char c)
        {
            return children.get(c);
        }
    }
}
