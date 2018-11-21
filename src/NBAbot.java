
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map; 

public class NBAbot {
	
	static LinkedHashMap<String, JSONObject[]> NBAresult = new LinkedHashMap<>();
	
	public NBAbot() {
		
	}
	private static String readAll(Reader reader) throws IOException {
	    StringBuilder builder = new StringBuilder();
	    int cp;
	    while ((cp = reader.read()) != -1) {
	    	builder.append((char) cp);
	    }
	    return builder.toString();
	}

	public static String readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream inputStream = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(
					new InputStreamReader(inputStream, 
							Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			// remove irrelevant chars
			return jsonText.substring(jsonText.indexOf("data\":{") + 7, 
					jsonText.indexOf(",\"version\":"));
		} finally {
			inputStream.close();
		}
	}
	
	public static void printMap(Map mp) {
	    Iterator it = mp.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println(pair.getKey() + " = ");
	        //System.out.println((JSONObject[])pair.getValue());
	        printMatches((JSONObject[])pair.getValue());
	        //it.remove();
	    }
	}
	
	private static void printMatches(JSONObject[] matches) {
		for(int i = 0; i < matches.length; i++) {
			try {
				System.out.printf("%s %s (%s) : (%s)\n", 
						matches[i].get("startTime"),
						matches[i].get("leftEnName"),
						matches[i].get("leftGoal"),
						matches[i].get("rightGoal"),//);//,
						matches[i].get("rightEnName"));
			} catch (JSONException e) {
				//System.err.println("json field not found");
				e.printStackTrace();
			}
		}
	}
	
	public static void fillMatchInfo(String jsonText) {
		// split string by dates
		String[] jsonArray = jsonText.split("\\:\\[|\\],");
		for(int i = 0; i < jsonArray.length; i += 2) {
			// in each day, split matches as string array
			String[] matches = jsonArray[i+1].replaceAll("\\},\\{", "\\}#\\{")
				   .split("#");
		   // convert matches as a json array
			JSONObject[] matchesJson = new JSONObject[matches.length];
			for(int j = 0; j <matches.length; j++) {
				try {
					matchesJson[j] = new JSONObject(matches[j]);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// date : matches(json)
			NBAresult.put(jsonArray[i].substring(1, 11), matchesJson);
		}
	}
	
	public static void main(String[] args) throws IOException, JSONException {
		
	   String jsonText = readJsonFromUrl("http://matchweb.sports.qq.com/" + 
	    		"kbs/list?from=NBA_PC&columnId=100000&" + 
	    		"startTime=2018-11-21&endTime=2018-11-27&" + 
	    		"callback=ajaxExec&_=1542776881859");
	   
	   fillMatchInfo(jsonText);
	   
	   printMap(NBAresult);

	   
	   //System.out.println(jsonText); //41876
	   ////JSONObject json = new JSONObject(jsonText);
	   //System.out.println(json.toString());
	   //System.out.println(json.get("id"));
	}
}
