package model.logic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DictionaryManager {

    private static DictionaryManager dm = null;

    public static DictionaryManager get() {
        if (dm == null)
            dm = new DictionaryManager();
        return dm;
    }

    Map<String, model.logic.Dictionary> map;

    public DictionaryManager() {
        map = new HashMap<>();
    }

    public boolean query(String... args) {
        // separate the last index from rest of the String[] because the last is the search word
        //String keyword = args[args.length-1];

        String keyword = args[args.length - 1].replaceAll("\\s", "");
        args = Arrays.copyOf(args, args.length - 1);
//        keyword = keyword.replaceAll("[^a-zA-Z0-9]", "");

        if (dm.map.containsKey(keyword))
            return true;

        model.logic.Dictionary dic = new model.logic.Dictionary(args);
        if (dic.query(keyword))
            return true;

        dm.map.put(keyword, dic);
        return false;
    }

    public boolean challenge(String... args) {
        // separate the last index from rest of the String[] because the last is the search word

        String keyword = args[args.length - 1].replaceAll("\\s", "");
        args = Arrays.copyOf(args, args.length - 1);

//        keyword = keyword.replaceAll("[^a-zA-Z0-9]", "");

        model.logic.Dictionary dic = new model.logic.Dictionary(args);
        if (dic.challenge(keyword)) {
            dm.map.put(keyword, dic);
            return true;
        }

        return false;
    }


    public int getSize() {
        return map.size();
    }
}
