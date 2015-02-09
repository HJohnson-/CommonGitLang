import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommonGitLang extends JSONAnalyzer {

    public static void main(String[] args)  {
        String gitName;
        switch(args.length) {
            case 0:
                //ask user for a name
                Scanner user_input = new Scanner( System.in );
                gitName = user_input.next( );
                break;
            case 1:
                gitName = args[0];
                break;
            default:
                System.out.println("Please give 1 or 0 arguments");
                return;
        }

        JSONAnalyzer analyzer = new CommonGitLang();

        analyzer.AnalyseJSON(formatURL(gitName));
    }

    /**
     * @param gitName The name of a git user
     * @return The HTTPS URL of the API page for that user's repositories.
     */
    private static String formatURL(String gitName) {
        return ("https://api.github.com/users/" + gitName + "/repos");
    }

    /**
     * @param jArr The array of git repositories, in JSON form
     * @return The most common language, as a string
     */
    @Override
    protected String analyseJSONArray(JSONArray jArr) {

        if(! (jArr.length() > 0)) {
            System.out.println("No repos found for that user.");
            return null;
        }

        Map<String, Integer> langFrequency = new HashMap<String, Integer>();
        int highestOccurrences = 0;
        String mostCommonLang = null;

        for(int repNum = 0; repNum < jArr.length(); repNum++) {
            JSONObject gitRepo = jArr.getJSONObject(repNum);
            String language;
            if(!gitRepo.isNull("language")) {
                language = gitRepo.getString("language");
                if(language == null || language.equals("")) continue;
                Integer newFreq = noNull(langFrequency.get(language))+1;
                langFrequency.put(language,newFreq);
                if(newFreq > highestOccurrences) {
                    highestOccurrences = newFreq;
                    mostCommonLang = language;
                }
            }
        }
        if(mostCommonLang == null) {
            System.out.println("No repos for that user had a language identified");
        }

        return mostCommonLang;
    }

    private Integer noNull(Integer integer) {
        return integer != null ? integer : 0;
    }

    /**
     * @param result The most common language, which will be displayed
     */
    @Override
    protected void display(String result) {
        System.out.println("The most common language was " + result);
    }

    /**
     * Since we do not expect any JSON Objects, this should return null.
     */
    @Override
    protected String analyseJSONObject(JSONObject jObj) {
        return null;
    }
}
