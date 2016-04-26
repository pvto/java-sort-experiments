package util.sort.strings;

import java.util.List;
import util.StringTrie;

/**
 *
 * @author Paavo Toivanen https://github.com/pvto
 */
public class StringTrieSort {

    public List<String> sort(List<String> strings)
    {
        StringTrie trie = new StringTrie();
        for(String string : strings)
        {
            trie.put(string);
        }
        return trie.getOrderedStringsWithDuplicates();
    }
}
