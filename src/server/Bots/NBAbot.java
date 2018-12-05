package server.Bots;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import server.ChatClientThread;
import server.Response;
import server.User;

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

import server.Response;
import server.User;

public class NBAbot extends Bot{
	
	LinkedHashMap<String, JSONObject[]> NBAresult = new LinkedHashMap<>();
	LinkedHashMap<String, ArrayList<JSONObject>> localData = new LinkedHashMap<>();
	private AbstractMap<String, String> commandsMap;
	private int timeDiff = 15;
	
	public NBAbot(char id) {
		super();
		fetchMatchInfo();
		commandsMap = new HashMap<>();
		commandsMap.put("today", " - show all NBA matches that play today.");
		commandsMap.put("schedule", "[yyyy-mm-dd] - A day schedule of NBA matches.");
		commandsMap.put("team", "[TEAM NAME] - all recent matches of a team (see valid team name by %teamlist).");
		commandsMap.put("live", " - show all matches that are playing right now.");
		commandsMap.put("dayrange", " - show the range of date that I can remember.");
		commandsMap.put("teamlist", " - show the team list in NBA.");
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
	/**
	 * Iterate though a map and edit its values (JSONObject[] matches)
	 * @param mp
	 */
	private void convertMap(Map mp) {
	    Iterator it = mp.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        importMatches((JSONObject[])pair.getValue());
	        it.remove();
	    }
	}
	/**
	 * Re-organize a list of matches into the localData map
	 * @param matches
	 */
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

	/**
	 * Convert a date string in term yyyy-mm-dd hh:mm;ss to another time zone
	 * @param dateStr
	 * @return the converted date
	 */
	private String convertTimeZone(String dateStr) {
		
		String[] date = dateStr.split(" ")[0].split("-");
		String[] time = dateStr.split(" ")[1].split(":");
		// calculate the hour.month/day
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
		return "[NBAbot]";
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
	public Response getResponses(String message, User user, List<ChatClientThread> clients) {
		
		if (message == null || user == null)
			return new Response("Something wrong happened.", null);
		// Split the command into multiple string delimited by space character.
		String[] msg_tokens = message.split(" ", 2);

		// Get the command.
		String command = msg_tokens[0].substring(1, msg_tokens[0].length());

		// Response
		String response = "";
		
		// if valid command
		switch(command) {
			case ("ttm"):
				response += super.getSmartResponse(message, user);
				break;
			case ("help"):
				response += helpCommand();
				break;
			case ("info"):
				response += infoCommand(msg_tokens[1]);
				break;
			case ("date"):
				response += dateCommand();
				break;
			case ("whoami"):
				response += whoamiCommand(user);
				break;
			case ("schedule"):
				if(msg_tokens.length > 1) {
					response += "\n" + searchData("schedule", msg_tokens[1]);
					if(response.length() <= 2) response += "Invalid Date";
				} else {
					response += "Give me the date you want to know";
				}
				break;
			case("team"):
				if(msg_tokens.length > 1) {
					response += "\n" + searchData("team", msg_tokens[1]);
					if(response.length() <= 2) response += "Invalid Team";
				} else {
					response += "Give me the team you want to know";
				}
				break;
			case("live"):
				response += "\n" + searchData("live", "");
				if(response.length() <= 2) response += "No match is undergoing";
				break;
			case("dayrange"):
				response += "I can remember NBA Match info\nFrom: 2018-11-25\nTo: 2018-12-15";
				break;
			case("teamlist"):
				response += "\n" + NBAteamList();
				break;
			case("today"):
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				response += "\n" + searchData("schedule", dateFormat.format(date));
				if(response.length() <= 2) response += "No match today";
				break;
			default:
				response += "invalid";
		}
		//return response;
		return new Response(response, null);
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
						// if one of the two teams are what user want
						if(matches.get(i).get("leftEnName").equals(param) ||
								matches.get(i).get("rightEnName").equals(param)) {
							validResult = true;
						}
					} else if (command.equals("live")) {
						String quarterTime = (String) matches.get(i).get("quarterTime");
						// if the game is not playing
						if(!quarterTime.equals("") && !quarterTime.equals("00:00")) {
							validResult = true;
						}
					}
					// append the match info to result
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

	private String NBAteamList() {
		return  "Eastern Conference\r\n\n" + 
				"Atlantic Division\r\n" + 
				"\t(Boston) Celtics\r\n" + 
				"\t(Brooklyn) Nets\r\n" + 
				"\t(New York) Knicks\r\n" + 
				"\t(Philadelphia) 76ers\r\n" + 
				"\t(Toronto) Raptors\r\n" + 
				"Central Division\r\n" + 
				"\t(Chicago) Bulls\r\n" + 
				"\t(Cleveland) Cavaliers\r\n" + 
				"\t(Detroit) Pistons\r\n" + 
				"\t(Indiana) Pacers\r\n" + 
				"\t(Milwaukee) Bucks\r\n" + 
				"Southeast Division\r\n" + 
				"\t(Atlanta) Hawks\r\n" + 
				"\t(Charlotte) Hornets\r\n" + 
				"\t(Miami) Heat\r\n" + 
				"\t(Orlando) Magic\r\n" + 
				"\t(Washington) Wizards\r\n" + 
				"\nWestern Conference\r\n\n" + 
				"Southwest Division\r\n" + 
				"\t(Dallas) Mavericks\r\n" + 
				"\t(Houston) Rockets\r\n" + 
				"\t(Memphis) Grizzlies\r\n" + 
				"\t(New Orleans) Pelicans\r\n" + 
				"\t(San Antonio) Spurs\r\n" + 
				"Northwest Division\r\n" + 
				"\t(Denver) Nuggets\r\n" + 
				"\t(Minnesota) Timberwolves\r\n" + 
				"\t(Oklahoma City) Thunder\r\n" + 
				"\t(Portland) Trail Blazers\r\n" + 
				"\t(Utah) Jazz\r\n" + 
				"Pacific Division\r\n" + 
				"\t(Golden State) Warriors\r\n" + 
				"\t(LA Clippers)Los Angeles Clippers\r\n" + 
				"\t(L.A. Lakers)Los Angeles Lakers\r\n" + 
				"\t(Phoenix) Suns\r\n" + 
				"\t(Sacramento) Kings\n\n" + 
				"* When searching, please use the name in the (parentheses)";
	}


	@Override
	public String whoamiCommand(User user) {
		return "User: " + user.getHandle() + "\t" + "IP address: " + user.getConnectionInfo();
	}
	
	@Override
	public String helpCommand() {
		String helpCommandStr = "List of available commands: \n";
		for (String command : getDefaultCommandsList().keySet()) {
			helpCommandStr += "\t" + this.getBotCharacterId() + command + getDefaultCommandsList().get(command) + "\n";
		}

		for (String command : commandsMap.keySet()) {
			helpCommandStr += "\t" + this.getBotCharacterId() + command + commandsMap.get(command) + "\n";
		}

		return helpCommandStr;
	}
}
