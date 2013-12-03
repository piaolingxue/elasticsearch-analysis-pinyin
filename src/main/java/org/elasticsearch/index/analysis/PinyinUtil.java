/**
 * 
 */
package org.elasticsearch.index.analysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author matrix
 * 
 */
public class PinyinUtil {
    private static final String DEFAULT_PINYIN_FILE = "/pinyin_with_multi_tones.txt";


    public static String[] toPinyinStringArray(Character c) {
        return DefaultPinyinSetHolder.DEFAULT_PINYIN_SET.get(c);
    }

    private static class DefaultPinyinSetHolder {
        static final Map<Character, String[]> DEFAULT_PINYIN_SET;

        static {
            try {
                DEFAULT_PINYIN_SET = loadDefaultStopWordSet();
            }
            catch (IOException ex) {
                // default set should always be present as it is part of the
                // distribution (JAR)
                throw new RuntimeException("Unable to load default stopword set");
            }
        }


        static Map<Character, String[]> loadDefaultStopWordSet() throws IOException {
            InputStream is = PinyinUtil.class.getClass().getResourceAsStream(DEFAULT_PINYIN_FILE);
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                Map<Character, String[]> r = new HashMap<Character, String[]>();
                while (br.ready()) {
                    String line = br.readLine();
                    String[] specs = line.split(" ");
                    System.out.println(specs[0].charAt(0));
                    System.out.println(Arrays.toString(specs[1].split(",")));
                    r.put(specs[0].charAt(0), specs[1].split(","));
                }
                return r;
            }
            finally {
                if (null != is)
                    is.close();
            }
        }
    }


    public static void addAndRemoveDuplicates(List<String> source, List<String> newData) {
        for (String data : newData) {
            if (!source.contains(data))
                source.add(data);
        }
    }


    public static String[] removeDuplicates(String[] list) {
        Set<String> items = new HashSet<String>();
        for (String item : list) {
            if (!items.contains(item)) {
                items.add(item);
            }
        }
        return items.toArray(new String[items.size()]);
    }


    /**
     * 递归得到所有拼音组合
     * 
     * @param strJaggedArray
     * @return
     */
    public static List<String> exchange(String[][] strJaggedArray) {
        String[][] temp = doExchange(strJaggedArray);
        return Arrays.asList(temp[0]);
    }


    /**
     * 递归
     * 
     * @author wyh
     * @param strJaggedArray
     * @return
     */
    private static String[][] doExchange(String[][] strJaggedArray) {
        int len = strJaggedArray.length;
        if (len >= 2) {
            int len1 = strJaggedArray[0].length;
            int len2 = strJaggedArray[1].length;
            int newlen = len1 * len2;
            String[] temp = new String[newlen];
            int Index = 0;
            for (int i = 0; i < len1; i++) {
                for (int j = 0; j < len2; j++) {
                    temp[Index] = strJaggedArray[0][i] + strJaggedArray[1][j];
                    Index++;
                }
            }
            String[][] newArray = new String[len - 1][];
            for (int i = 2; i < len; i++) {
                newArray[i - 1] = strJaggedArray[i];
            }
            newArray[0] = temp;
            return doExchange(newArray);
        }
        else {
            return strJaggedArray;
        }
    }

}
