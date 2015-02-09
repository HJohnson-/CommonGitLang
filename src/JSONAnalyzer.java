import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Abstract class. Override the analyseJSONObject and analyseJSONArray to return relevant strings when given that data
 * type, and display to display these strings. A function to call Execute must be made for other classes to make use of
 * a class extending this.
 */
public abstract class JSONAnalyzer {

    /**
     * @param URL the URL, that when sent a GET request, returns JSON to analyze
     */
    protected void AnalyseJSON(String URL) {
        JSONObject jObj;
        JSONArray jArr;
        String result;

        String response = readFromURL(URL);
        //Attempt to turn
        if((jObj = setJSONObject(response)) != null) {
            result = analyseJSONObject(jObj);
        } else if((jArr = setJSONArray(response)) != null) {
            result = analyseJSONArray(jArr);
        } else {
            System.out.println("Received invalid JSON object, no results.");
            return;
        }
        display(result);
    }


    /**
     * @param jArr A JSON Array, not null.
     * @return A string result from analyzing the array. Null if an array was not expected or display isn't needed.
     */
    protected abstract String analyseJSONArray(JSONArray jArr);

    protected JSONArray setJSONArray(String response) {
        //Looks like a JSON Array
        if(response.startsWith("[")) {
            //build a JSON Array
            try {
                return new JSONArray(response);
            } catch (Exception e) {
                System.out.println("Failed to build JSONArray.");
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * @param result The result of analyzing the JSON, to be displayed
     */
    protected abstract void display(String result);

    /**
     * @param jObj A non-null JSONObject
     * @return A string result of analyzing jObj. Null if object not expected or display not required.
     */
    protected abstract String analyseJSONObject(JSONObject jObj);

    /**
     * @param response the response from sending a GET to the URL
     * @return null if the response isn't a JSON object, otherwise a JSONObject representing the response.
     */
    protected JSONObject setJSONObject(String response) {
        //looks like a JSON Object
        if(response.startsWith("{")) {
            //build a JSON Object
            try {
                return new JSONObject(response);
            } catch(Exception e) {
                System.out.println("Failed to create JSONObject.");
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * @param urlString String form of the URL a GET request will be sent to.
     * @return The response from the URL, null if no response could be found or was invalid.
     */
    private String readFromURL(String urlString) {
        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL, argument invalid.");
            return null;
        }

        //read from URL
        Scanner scanner;
        try {
            scanner = new Scanner(url.openStream());
        } catch(java.io.FileNotFoundException e) {
            System.out.println("Matching URL could not be found");
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        String str = "";

        while (scanner.hasNext()) {
            str += scanner.nextLine();
        }
        scanner.close();

        return str;
    }

}
