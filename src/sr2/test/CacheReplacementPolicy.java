package sr2.test;

public interface CacheReplacementPolicy{
    void add(String word);
    String remove();
}
