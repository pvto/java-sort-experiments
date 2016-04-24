package util;


import org.junit.Test;
import static org.junit.Assert.*;
import util.CharTrie.CharTrieLevel1;


public class CharTrieTest {

    @Test
    public void testBasic() {
        CharTrie c = CharTrie.newTrie();
        c.put("abc".toCharArray());
        c.put("abba".toCharArray());
        c.put("aäð".toCharArray());
        assertEquals(3, c.size());
        assertTrue(c.match("abc".toCharArray()));
        assertFalse(c.match("abcd".toCharArray()));
        assertTrue(c.match("aäð".toCharArray()));
        assertFalse(c.match("aäð-".toCharArray()));
        assertFalse(c.match("ab".toCharArray()));
    }


    @Test
    public void testOrdered() {
        CharTrie c = CharTrie.newOrderedTrie();
        c.put("abac".toCharArray());
        c.put("ababa".toCharArray());
        c.put("aäð".toCharArray());
        assertEquals(3, c.size());
        assertTrue(c.match("abac".toCharArray()));
        assertFalse(c.match("abcd".toCharArray()));
        assertTrue(c.match("aäð".toCharArray()));
        assertFalse(c.match("aäð-".toCharArray()));
        assertFalse(c.match("ab".toCharArray()));
        assertNotNull(c.getChild('a').getChild('b').getChild('a'));
        assertEquals('c', ((CharTrieLevel1)(c.getChild('a').getChild('b').getChild('a'))).children.get(1).key);
    }
}