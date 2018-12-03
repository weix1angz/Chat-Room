package server.Bots;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import server.Response;
import server.User;

public class WeixiangBot extends Bot{
	private HashMap<String, String> commandsMap;
	private HashMap<String, ArrayList<String>> responseMap; 
	public WeixiangBot(char characterId) {
		
		commandsMap = new HashMap<>();
		commandsMap.put("whatisit" , " - the description of the game");
		commandsMap.put("midlaner ", " - the player in the mid lane");
		commandsMap.put("jungler" , " - the player in the jungle");
		commandsMap.put("toplaner" , " - the playter in the top lane");
		commandsMap.put("adc", " - one of the players in the bottom lane");
		commandsMap.put("suport" , " - one of the players in the bottom lane");
		commandsMap.put("show", "[Champion's Name] - display the image of the "
				+ "champion with valid name like \"Lux\" instead of \"lux\"");
		constructResponse();
		this.setBotCharacterId(characterId);
	}

	
	private void constructResponse() {
		responseMap = new HashMap<String, ArrayList<String>>();
		try {
			Scanner in = new Scanner(new File("commands.txt"));
			String command = "";
			while(in.hasNextLine()) {
				String line = in.nextLine();
				if(line.equals("\n")) {
					System.out.println(line);
					if(line.charAt(0) == '%') {
						command = line.substring(1, line.length());
						ArrayList<String> responseList = new ArrayList<String>();
						responseMap.put(command, responseList);
						System.out.println(responseMap);
						continue;
					}else {
						responseMap.get(command).add(line);
					}
				}
			}	
		}catch(FileNotFoundException e) {
			System.out.println(e.getStackTrace());
		}
		
		
	}
	@Override
	public String getBotSignature() {
		return "[WXBOT]";
	}

	@Override
	public String infoCommand(User user) {
		// TODO Auto-generated method stub
		return null;
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
	public String whoamiCommand(User user) {
		return "User: " + user.getHandle() + "\t" 
			+ "IP address: " + user.getConnectionInfo();
	}

	@Override
	public Response getResponses(String message, User user) {
		Response response = null;
		if (message == null || user == null)
			return response;
		// Split the command into multiple string delimited by space character.
		String[] msg_tokens = message.split(" ");

		// Get the command.
		String command = msg_tokens[0].substring(1, msg_tokens[0].length());

		String data = null;
		// Response
		String responseText = "";

		// Need to
		if (getDefaultCommandsList().containsKey(command) ) {
			if (command.equals("help")) {
				responseText += helpCommand();
			} else if (command.equals("info")) {
				responseText += infoCommand(user);
			} else if (command.equals("date")) {
				responseText += dateCommand();
			} else if (command.equals("whoami")) {
				responseText += whoamiCommand(user);
			}

		} else if (this.commandsMap.containsKey(command)){
			Random rndGen = new Random();
			if(!command.equals("show"))
			responseText += this.responseMap.get(command).
					get(rndGen.nextInt(this.responseMap.get(command).size()));
			else {
				responseText += "loading image";
				data = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/" + msg_tokens[1] + "_0.jpg";
			} 
		} else {
			responseText += "Invalid Command";
		} 
		response = new Response(responseText, data);
		return response;
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
