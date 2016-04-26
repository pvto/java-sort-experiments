
package util;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class StringTrieTest {

    @Test
    public void testMatch()
    {
        StringTrie trie = new StringTrie();
        trie.put("a");
        trie.put("b");
        trie.put("c");
        trie.put("bb");
        trie.put("ba");
        trie.put("b");
        trie.put("");
        trie.put("");
        assertEquals(1, trie.match("bb"));
        assertEquals(1, trie.match("ba"));
        assertEquals(2, trie.match("b"));
        assertEquals(0, trie.match(""));
        assertEquals(1, trie.match("a"));
        assertEquals(1, trie.match("c"));
        assertEquals(0, trie.match("d"));
        assertEquals(0, trie.match("ä"));
    }
    
    @Test
    public void testGetOrderedStrings()
    {
        StringTrie trie = new StringTrie();
        trie.put("a");
        trie.put("b");
        trie.put("c");
        trie.put("bb");
        trie.put("ba");
        trie.put("b");
        List<String> res = trie.getOrderedStrings();
        assertEquals(5, res.size());
        assertEquals("a", res.get(0));
        assertEquals("b", res.get(1));
        assertEquals("ba", res.get(2));
        assertEquals("bb", res.get(3));
        assertEquals("c", res.get(4));
    }

}