package org.elasticsearch.index.analysis;

import java.util.List;

import junit.framework.TestCase;

import org.testng.annotations.Test;


public class PinyinUtilTest extends TestCase {

    @Test
    public void testEnglish() {
        String[][] tmpFull = new String[11][];
        tmpFull[0] = new String[] { "h" };
        tmpFull[1] = new String[] { "e" };
        tmpFull[2] = new String[] { "l" };
        tmpFull[3] = new String[] { "l" };
        tmpFull[4] = new String[] { "o" };
        tmpFull[5] = new String[] { " " };
        tmpFull[6] = new String[] { "w" };
        tmpFull[7] = new String[] { "o" };
        tmpFull[8] = new String[] { "r" };
        tmpFull[9] = new String[] { "l" };
        tmpFull[10] = new String[] { "d" };
        List<String> results = PinyinUtil.exchange(tmpFull);
        assertEquals(1, results.size());
        assertEquals("hello world", results.get(0));
    }
}
