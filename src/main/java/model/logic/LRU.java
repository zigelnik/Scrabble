package model.logic;


import java.util.LinkedHashSet;
import java.util.Set;
import java.lang.*;

public class LRU implements CacheReplacementPolicy {

    private final Set<String> lhs = new LinkedHashSet<>();

    @Override
    public void add(String word) {
        if (lhs.contains(word)) {
            lhs.remove(word);
            lhs.add(word);
        }
        lhs.add(word);
    }

    @Override
    public String remove() {

        if (lhs.isEmpty())
            return null;

        String[] arr = new String[lhs.size()];
        arr = lhs.toArray(arr);
        String tempWord = arr[0];
        lhs.remove(arr[0]);
        return tempWord;
    }
}
