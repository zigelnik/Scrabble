package sr2.test;

import java.util.HashSet;
import java.util.Set;

public class CacheManager {
    private final int size;
    private final CacheReplacementPolicy crp;

    private final Set<String> cache;
    public CacheManager(int size, CacheReplacementPolicy crp) {
        this.cache = new HashSet<>();
        this.size = size;
        this.crp = crp;
    }

    public boolean query (String word)
    {
       return cache.contains(word);
    }

    public void add(String word)
    {
        crp.add(word);
        cache.add(word);

        if(cache.size() > size)
            cache.remove(crp.remove());
    }

}
