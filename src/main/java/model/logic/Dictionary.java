package model.logic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class Dictionary {

    private final int WORDS_EXIST_SIZE = 400;
    private final int WORDS_DONT_EXIST_SIZE = 100;
    private final int BF_SIZE = 128036;
    private final String HASH_FUNC1 = "MD5";
    private final String HASH_FUNC2 = "SHA1";
    private final CacheManager wordsExist;
    private final CacheManager wordsDONTExist;
    private final BloomFilter bf;
    private Set<String> wordsSet = new HashSet<>();
    private final String[] files;

    public Dictionary(String... args) {

        files = args;
        wordsExist = new CacheManager(WORDS_EXIST_SIZE, new LRU());
        wordsDONTExist = new CacheManager(WORDS_DONT_EXIST_SIZE, new LFU());
        bf = new BloomFilter(BF_SIZE, HASH_FUNC1, HASH_FUNC2);



        for (String file : files)
            updateWordSet(file);

        for (String word : wordsSet)
            bf.add(word);

        System.out.println("set size:"+wordsSet.size());

    }

    private void updateWordSet(String file) {


        try (Scanner scanner = new Scanner(
                new FileReader("src\\main\\resources\\search_folder\\" +file))) {

            scanner.useDelimiter("[^a-zA-Z]+");

            while (scanner.hasNext()) {
                String word = scanner.next().toUpperCase();
                if(word.length()==1 && !word.equals("A")) {continue;}
                wordsSet.add(word);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public boolean query(String word) {
        if (wordsExist.query(word))
            return true;

        if (wordsDONTExist.query(word))
            return false;

        if (bf.contains(word)) {
            wordsExist.add(word);
            return true;
        } else {
            wordsDONTExist.add(word);
            return false;
        }

    }

    public boolean challenge(String word) {
        boolean flag = false;
        try {
            flag = IOSearcher.search(word, files);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }
}