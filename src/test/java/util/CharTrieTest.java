package util;


import util.CharTrie;
import org.junit.Test;
import static org.junit.Assert.*;


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


}