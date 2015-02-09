import junit.framework.TestCase;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

/**
 * Created by harry on 09/02/15.
 */
public class CommonGitLangTest extends TestCase {

    @Test
    public void testAnalyseJSONArray() throws Exception {
        JSONAnalyzer testJSONAnalyzer = new CommonGitLang();

        JSONArray example1 = new JSONArray("[]");
        String result1 = testJSONAnalyzer.analyseJSONArray(example1);
        assertEquals("Empty array returns null", null, result1);

        JSONArray example2 = new JSONArray("[{\"language\":\"\"}]");
        String result2 = testJSONAnalyzer.analyseJSONArray(example2);
        assertEquals("No repos with languages returns null", null, result2);

        JSONArray example3 = new JSONArray("[{\"language\": \"Java\"}]");
        String result3 = testJSONAnalyzer.analyseJSONArray(example3);
        assertEquals("Single repo returns its language", "Java", result3);

        JSONArray example4 = new JSONArray("[{\"language\": \"C\"}, {\"language\": \"Java\"}," +
                " {\"language\": \"Java\"}, {\"language\": \"C\"}])");
        String result4 = testJSONAnalyzer.analyseJSONArray(example4);
        assertEquals("If a tie, older repos are ignored first", "Java", result4);

        JSONArray example5 = new JSONArray("[{\"language\": \"C\"}, {\"language\": \"Java\"}, {\"language\": \"Java\"}]");
        String result5 = testJSONAnalyzer.analyseJSONArray(example5);
        assertEquals("The majority wins in normal situations", "Java", result5);

        JSONArray example6 = new JSONArray("[{\"junk\": \"text\", \"othertext\": \"ignorethis\", \"language\": \"Java\", " +
                "\"finaljunk\": \"finaltext\"}]");
        String result6 = testJSONAnalyzer.analyseJSONArray(example6);
        assertEquals("Fields except 'language' are ignored", "Java", result6);
    }
}
