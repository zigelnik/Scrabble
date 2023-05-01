package sr3.test;

public interface CacheReplacementPolicy{
    void add(String word);
    String remove();
}
