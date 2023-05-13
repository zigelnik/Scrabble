package model.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IOSearcher {

    private static StringBuilder builder;
    private static String str;

    public static boolean search(String word, String ...args) {


        for (String arg : args) {
            builder = new StringBuilder();

            try (BufferedReader buffer = new BufferedReader(
                    new FileReader(arg))) {

                // holding true upto that the while loop runs
                while ((str = buffer.readLine()) != null)
                    builder.append(str).append("\n");

            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] newWords = builder.toString().split("\\W");

            for (String newWord : newWords)
                if (newWord.equals(word))
                    return true;

        }
        return false;
    }
}
