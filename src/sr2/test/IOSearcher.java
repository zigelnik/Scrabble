package sr2.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IOSearcher {

    private static StringBuilder builder;
    private static String str;

    public static boolean search(String word, String ...args) {


        for (int i = 0; i < args.length; i++) {
            builder = new StringBuilder();

            try (BufferedReader buffer = new BufferedReader(
                    new FileReader(args[i]))) {

                // holding true upto that the while loop runs
                while ((str = buffer.readLine()) != null)
                    builder.append(str).append("\n");

            }

            catch (IOException e) {
                e.printStackTrace();
            }
            String[] newWords = builder.toString().split("\\W");

            for(int j=0;j< newWords.length;j++)
                if(newWords[j].equals(word))
                    return true;

        }
        return false;
    }
}
