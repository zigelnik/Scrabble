package sr3.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Dictionary {

    private final int WORDS_EXIST_SIZE = 400;
    private final int WORDS_DONT_EXIST_SIZE = 100;
    private final int BF_SIZE = 256;
    private final String HASH_FUNC1 = "MD5";
    private final String HASH_FUNC2 = "SHA1";
    private final CacheManager wordsExist;
    private final CacheManager wordsDONTExist;
    private final BloomFilter bf;
    private String[] fileWords;
    private final String[] files;

    public Dictionary(String... args)  {

       files = args;
        wordsExist = new CacheManager(WORDS_EXIST_SIZE,new LRU());
        wordsDONTExist = new CacheManager(WORDS_DONT_EXIST_SIZE, new LFU());
        bf = new BloomFilter(BF_SIZE,HASH_FUNC1,HASH_FUNC2);


        for(String file: files)
        {
            fileWords = getWordsFromFile(file);

            for(String words:fileWords)
                bf.add(words);
        }
    }

    public String[] getWordsFromFile(String file)
    {

        StringBuilder builder = new StringBuilder();
        String[] newWords;

            try (BufferedReader buffer = new BufferedReader(
                    new FileReader(file))) {

                String str;

                // holding true upto that the while loop runs
                while ((str = buffer.readLine()) != null)
                    builder.append(str).append("\n");

            }

            catch (IOException e) {
                e.printStackTrace();
            }
             newWords = builder.toString().split("\\W");



        return newWords;
    }

    public boolean query(String word)
    {
        if(wordsExist.query(word))
            return true;

         if(wordsDONTExist.query(word))
            return false;

         if(bf.contains(word))
        {
            wordsExist.add(word);
            return true;
        }
        else {
            wordsDONTExist.add(word);
            return  false;
        }

    }

    public boolean challenge(String word)
    {
        boolean flag = false;
        try
        {
            flag = IOSearcher.search(word, files);
        }
        catch(Exception e)
        {
                e.printStackTrace();
        }

        return flag;
    }
}

