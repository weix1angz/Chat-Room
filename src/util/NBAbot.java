package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TimeZone; 

public class NBAbot extends Bot{
	
	LinkedHashMap<String, JSONObject[]> NBAresult = new LinkedHashMap<>();
	LinkedHashMap<String, ArrayList<JSONObject>> localData = new LinkedHashMap<>();
	private AbstractMap<String, String> commandsMap;
	private int timeDiff = 15;
	
	public NBAbot(char id) {
		super();
		fetchMatchInfo();
		commandsMap = new HashMap<>();
		commandsMap.put("schedule", "[yyyy-mm-dd] A day schedule of NBA matches.");
		commandsMap.put("team", "[TEAM NAME] all recent matches of a given team.");
		commandsMap.put("live", "show all matches that are undergoing right now.");
		commandsMap.put("dayrange", "show the range of date that data contains.");
		commandsMap.put("teamlist", "show a team list in NBA.");
		//TODO weekly schedule
		this.setBotCharacterId(id);
		//printData();
	}
 
	private void fetchMatchInfo() {
		try {
			
			fillMatchInfo(readJsonFromUrl("http://matchweb.sports.qq.com/" + 
					"kbs/list?from=NBA_PC&columnId=100000&" + 
					"startTime=2018-12-09&endTime=2018-12-15&" + 
					"callback=ajaxExec&_=1543705200120"));
			
			fillMatchInfo(readJsonFromUrl("http://matchweb.sports.qq.com/" + 
					"kbs/list?from=NBA_PC&columnId=100000&" + 
					"startTime=2018-11-25&endTime=2018-12-01&" + 
					"callback=ajaxExec&_=1543705200118"));
					
			fillMatchInfo(readJsonFromUrl("http://matchweb.sports.qq.com/" + 
					"kbs/list?from=NBA_PC&columnId=100000&" + 
					"startTime=2018-12-02&endTime=2018-12-08&" + 
					"callback=ajaxExec&_=1543705200117"));
					
			convertMap(NBAresult);
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String readAll(Reader reader) throws IOException {
	    StringBuilder builder = new StringBuilder();
	    int cp;
	    while ((cp = reader.read()) != -1) {
	    	builder.append((char) cp);
	    }
	    return builder.toString();
	}

	private String readJsonFromUrl(String url) throws IOException, JSONException {
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
	
	private void convertMap(Map mp) {
	    Iterator it = mp.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        importMatches((JSONObject[])pair.getValue());
	        it.remove();
	    }
	}
	
	private void importMatches(JSONObject[] matches) {
		for(int i = 0; i < matches.length; i++) {
			try {
				if(matches[i].get("matchType").equals("2")) {
					// convert the time into local time, overwrite old data
					String convertedDate = convertTimeZone((String)matches[i].get("startTime"));
					matches[i].put("startTime", convertedDate);
					String dataStr = convertedDate.split(" ")[0];
					// organize and put into local map
					if(!localData.containsKey(dataStr)) {
						localData.put(dataStr, 
								new ArrayList<JSONObject>(1));
					}
					localData.get(dataStr).add(matches[i]);
				}
				
			} catch (JSONException e) {
				//System.err.println("json field not found");
				e.printStackTrace();
			}
		}
	}
	
	private String convertTimeZone(String dateStr) {
		String[] date = dateStr.split(" ")[0].split("-");
		String[] time = dateStr.split(" ")[1].split(":");
		
		int hour = Integer.parseInt(time[0]) - timeDiff;
		int month = Integer.parseInt(date[1]);
		int day = Integer.parseInt(date[2]);
		
		if(hour < 0) {
			hour += 24;
			day -= 1;
		}
		
		if(day <= 0) {
			month -= 1;
			day = 30;
		}
		
		String hourStr = "";
		String monthStr = "";
		String dayStr = "";
		
		if(hour < 10) {
			hourStr = "0" + Integer.toString(hour);
		} else {
			hourStr = Integer.toString(hour);
		}
		if(day < 10) {
			dayStr = "0" + Integer.toString(day);
		} else {
			dayStr = Integer.toString(day);
		}
		if(month < 10) {
			monthStr = "0" + Integer.toString(month);
		} else {
			monthStr = Integer.toString(month);
		}
		
		String finalDate = date[0] + "-" + monthStr + "-" + dayStr
				 + " " + hourStr + ":" + time[1] + ":" + time[2];
		return finalDate;
	    
	}
	
	private void fillMatchInfo(String jsonText) {
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

	@Override
	public String getBotSignature() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String infoCommand(User user) {
		return "User: " + user.getHandle() + "\t"
				+ "Birthday: " + user.getBirthday().toString();
	}

	@Override
	public String whoamiCommand(User user) {
		return "User: " + user.getHandle() + "\t" 
			+ "IP address: " + user.getConnectionInfo();
	}
	
	@Override
	public String dateCommand() {
		Date today = new Date();
		List<String> DateResponses = new ArrayList<String>();

		// Default type of responses from toString().
		DateResponses.add(today.toString());

		// Another format:
		SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd");
		DateResponses.add("Today is " + ft.format(today));

		Random rndGen = new Random();
		return DateResponses.get(rndGen.nextInt(DateResponses.size()));
	}

	@Override
	public String getResponses(String message, User user) {
		
		//if (message == null || user == null)
			//return "Something wrong happened.";
		// Split the command into multiple string delimited by space character.
		String[] msg_tokens = message.split(" ", 2);

		// Get the command.
		String command = msg_tokens[0].substring(1, msg_tokens[0].length());

		// Response
		String response = "";
		
		// if valid command
		switch(command) {
			case ("help"):
				response += helpCommand();
				break;
			case ("info"):
				response += infoCommand(user);
				break;
			case ("date"):
				response += dateCommand();
				break;
			case ("whoami"):
				response += whoamiCommand(user);
				break;
			case ("schedule"):
				if(msg_tokens.length > 1) {
					response += searchData("schedule", msg_tokens[1]);
				} else {
					System.out.println("no time");
				}
				break;
			case("team"):
				if(msg_tokens.length > 1) {
					response += searchData("team", msg_tokens[1]);
				} else {
					System.out.println("no team");
				}
				break;
			case("live"):
				response += searchData("live", "");
				break;
			case("dayrange"):
				
				break;
			case("teamlist"):
				
				break;
			default:
				System.out.println("invalid");
		}
		return response;
	}
	private String searchData(String command, String param){
		String searchResult = "";
		// ------ schedule ----- //
		if(command.equals("schedule")) {
			ArrayList<JSONObject> matches = localData.get(param);
			if(matches == null) return "invalid date";
		}
		// ---- team / live ---- //
		boolean validResult = false;
		for (Entry<String, ArrayList<JSONObject>> entry : localData.entrySet()) {
		    String date = entry.getKey();
		    ArrayList<JSONObject> matches = entry.getValue();
		    for (int i=0; i<matches.size(); i++) {
				try { 
					if(command.equals("schedule")) {
						// if the current match is on the day we want
						if(((String)matches.get(i).get("startTime"))
								.split(" ")[0].equals(param)) {
							validResult = true;
						}
					} else if(command.equals("team")) {
						if(matches.get(i).get("leftEnName").equals(param) ||
								matches.get(i).get("rightEnName").equals(param)) {
							validResult = true;
						}
					} else if (command.equals("live")) {
						String quarterTime = (String) matches.get(i).get("quarterTime");
						if(!quarterTime.equals("") && !quarterTime.equals("00:00")) {
							validResult = true;
						}
					}
					if(validResult) {
				    	searchResult += String.format("%s %s (%s) : (%s) %s\n", 
								matches.get(i).get("startTime"),
								matches.get(i).get("leftEnName"),
								matches.get(i).get("leftGoal"),
								matches.get(i).get("rightGoal"),
								matches.get(i).get("rightEnName"));
				    	validResult = false;
				    }
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return searchResult;
	}
}
