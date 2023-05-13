package model.logic;

public interface CacheReplacementPolicy{
    void add(String word);
    String remove();
}
