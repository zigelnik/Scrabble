package sr3.test;

import java.util.*;

public class LFU implements CacheReplacementPolicy{

        private static final int FIRST_APPEARANCE = 1;
    private final Map<String, Integer> lhm = new LinkedHashMap<>();

    @Override
    public void add(String word) {
                if(!lhm.containsKey(word))
                    lhm.put(word, FIRST_APPEARANCE);
                else   {
                    Integer wordCounter = lhm.get(word);
                    lhm.put(word, ++wordCounter);
                }
    }

    @Override
    public String remove() {
        if(lhm.isEmpty())
            return null;

        Integer smallestValue = Integer.MAX_VALUE;
        String leastFreq = "";

        for (HashMap.Entry<String, Integer> entry : lhm.entrySet())
            if (entry.getValue() < smallestValue)
                leastFreq = entry.getKey();

        lhm.remove(leastFreq);
        return leastFreq;
    }
}
