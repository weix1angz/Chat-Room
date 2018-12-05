package server.Bots;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import server.ChatClientThread;
import server.Response;
import server.User;

public class WeixiangBot extends Bot {
	private HashMap<String, String> commandsMap;
	private HashMap<String, ArrayList<String>> responseMap;
	private AbstractMap<String, AbstractMap<String, Integer>> commandsCounter;
	
	/**
	 * This method is to construct a hashmap to count amount of how many times 
	 * the command being called 
	 * @return A HashMap to count each commands that have been used
	 */
	public AbstractMap<String, Integer> createNewCmdsCounter() {
		AbstractMap<String, Integer> newCmdsCounter = new HashMap<>();
		for (String cmd : getDefaultCommandsList().keySet()) {
			newCmdsCounter.put(cmd, 0);
		}
		
		for (String cmd : this.commandsMap.keySet()) {
			newCmdsCounter.put(cmd, 0);
		}
		
		return newCmdsCounter;
	}
	/**
	 * This is constructor that add all the responses to the map.
	 * @param characterId The special character for each ChatBot.
	 */
	public WeixiangBot(char characterId) {
		commandsMap = new HashMap<>();
		commandsCounter = new HashMap<>();
		commandsMap.put("help", " - list out the available commands for the current bot. ");
		commandsMap.put("info", "[USER] - prints out the information of User.");
		commandsMap.put("date", " - prints out the current date.");
		commandsMap.put("whoami", " - prints out the user's client info such as IP addresses, ...");
		commandsMap.put("ttm", " - abbreviation for \"talk to me\".");
		commandsMap.put("whoishere", " - list all the users who are online");

		commandsMap.put("whatisit", " - the description of the game");
		commandsMap.put("midlaner ", " - the player in the mid lane");
		commandsMap.put("jungler", " - the player in the jungle");
		commandsMap.put("stat", " - how many champions are there in the game");
		commandsMap.put("show", " [Champion's Name] - display the image of the "
				+ "champion with valid name like \"Lux\" instead of \"lux\"");
		constructResponse();
		this.setBotCharacterId(characterId);
	}

	/**
	 * This method is to read in a file that store all commands/responses and 
	 * 
	 */
	private void constructResponse() {
		responseMap = new HashMap<String, ArrayList<String>>();
		try {
			Scanner in = new Scanner(new File("./src/server/Bots/commands.txt"));
			String command = "";
			while (in.hasNextLine()) {
				String line = in.nextLine();
				if (!line.isEmpty()) {
					if (line.charAt(0) == '%') {
						command = line.substring(1, line.length());
						ArrayList<String> responseList = new ArrayList<String>();
						responseMap.put(command, responseList);

						continue;
					} else {
						responseMap.get(command).add(line);
					}
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getStackTrace());
		}
		

	}
	
	/**
	 * @return the string representing the signature of the ChatBot 
	 */
	@Override
	public String getBotSignature() {
		return "[WXBOT]";
	}

	/**
	 * This method is to get the date and return it
	 * @return the string representing the date
	 */
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

	/**
	 * @param user the User object 
	 * @return the string that has a name and IP address
	 */
	@Override
	public String whoamiCommand(User user) {
		return "User: " + user.getHandle() + "\t" + "IP address: " + user.getConnectionInfo();
	}

	/**
	 * @param message the message from user
	 * @param user the User object
	 * @param clients the list contains the all ChatClientThread who is online
	 * @return A Response that has two fields, one is the response message, the other is the URL if needed
	 */
	@Override
	public Response getResponses(String message, User user, List<ChatClientThread> clients) {
		if (!commandsCounter.containsKey(user.getHandle())) {
			commandsCounter.put(user.getHandle(), createNewCmdsCounter());
		}
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
		if (this.commandsMap.containsKey(command)) {
			if (command.equals("help")) {
				responseText += helpCommand();
			} else if (command.equals("info")) {
				responseText += infoCommand(msg_tokens[1]);
			} else if (command.equals("date")) {
				responseText += dateCommand();
			} else if (command.equals("whoami")) {
				responseText += whoamiCommand(user);
			} else if (command.equals("ttm")) {
				responseText += super.getSmartResponse(message, user);
			} else if (command.equals("whoishere")) {
				responseText += getOnlineUsers(clients);
			} else if (this.responseMap.containsKey(command)) {
				Random rndGen = new Random();
				// if it is a show command 
				if (!command.equals("show")) {
					System.out.println(command);
					System.out.println(this.responseMap.get(command).size());
					responseText += this.responseMap.get(command)
							.get(rndGen.nextInt(this.responseMap.get(command).size()));
				} else {
					if(msg_tokens.length > 1) {
						responseText += "loading image";
						data = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/" + msg_tokens[1] + "_0.jpg";
					}else {
						responseText += "Invalid Command";
					}
				}
			} else {
				responseText += "Invalid Command";
			}
		}
		if (commandsCounter.get(user.getHandle()).containsKey(command)) {
			int oldValue = commandsCounter.get(user.getHandle()).get(command);
			commandsCounter.get(user.getHandle()).put(command, oldValue + 1);
			if ((oldValue + 1) % 5 == 0) {
				responseText += "\n" + this.getBotSignature() + command + " has been used the fifth time by " + user.getHandle();
			}
		}
		response = new Response(responseText, data);
		return response;
	}

	/**
	 * 
	 * @param clients List of ChatClientThread
	 * @return String that list out who is online
	 */
	private String getOnlineUsers(List<ChatClientThread> clients) {
		String usersName = "Listing out the online users : \n";
		for (ChatClientThread client : clients) {
			usersName += client.getUser().getHandle() + " is online\n";
		}
		return usersName;
	}

	/**
	 * @return The string listing out all available commands 
	 */
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
