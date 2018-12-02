package server.Bots;

import java.io.File;
import java.util.AbstractMap;
import java.util.HashMap;


import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;

import server.Response;

import server.User;

/**
 * This bot class implements basic functionality of the chat bot. 
 * Note that this class should never be instantiated. Whenever we need to create a chat bot,
 * instantiate a new bot object that extends this class, for example:
 * 
 * Bot newBot = new MinhsBot();
 * 
 * @author Minh Bui
 */

public abstract class Bot {
	
	// A map of commands with the command names as keys and commands' parameters as values.
	private AbstractMap<String, String> defaultCommandsList;
	
	// A character that identify the 
	private char botCharacterId;
	
	private static final boolean TRACE_MODE = false;
	static String botName = "super";
	
	public Bot() {
		botCharacterId = '!';
		defaultCommandsList = new HashMap<>();
		defaultCommandsList.put("help", " - list out the available commands for the current bot. ");
		defaultCommandsList.put("info", "[USER] - prints out the information of User.");
		defaultCommandsList.put("date", " - prints out the current date.");
		defaultCommandsList.put("whoami", " - prints out the user's client info such as IP addresses, ...");
		defaultCommandsList.put("ttm", " - abbreviation for \"talk to me\".");
	}
	
	/**
	 * 
	 * @return A string containing a list of available commands and their usage.
	 */
	public String helpCommand() {
		String helpCommandStr = "List of available commands: \n";
		for (String command : defaultCommandsList.keySet()) {
			helpCommandStr += botCharacterId + command + defaultCommandsList.get(command);
		}
		
		return helpCommandStr;
	}
	
	/**
	 * @return The bot's handle in the server.
	 */
	public abstract String getBotSignature();
	
	public abstract String infoCommand(User user);
	
	/**
	 * @return A string representing the current's date.
	 */
	public abstract String dateCommand();
		
	/**
	 * @return Information about the user such as IP address and connecting client..
	 */
	public abstract String whoamiCommand(User user);
	
	/**
	 * Every Bot's subclass needs to overwrite this method.
	 * 
	 * @param message
	 * @param user
	 * @return
	 */
	public abstract Response getResponses(String message, User user);
	

	/**
	 * @return Return a character that identifies this bot. 
	 */
	public char getBotCharacterId() {
		return botCharacterId;
	}

	/**
	 * Set the character that identifies this bot to botCharacterId.
	 * @param botCharacterId A character that identifies this bot.
	 */
	public void setBotCharacterId(char botCharacterId) {
		this.botCharacterId = botCharacterId;
	}
	
	/**
	 * @return An abstract map of default commands as keys and their parameters as values.
	 */
	public AbstractMap<String, String> getDefaultCommandsList() {
		return this.defaultCommandsList;
	}
	
	/**
	 * Helper method for getSmartResponse.
	 * 
	 * @return A string path containing the resources for the aiml bot environment.
	 */
	private static String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        path += File.separator + "src";
        path += File.separator + "server";
        path += File.separator + "Bots";
        path += File.separator + "resources";
        return path;
    }
	
	/**
	 * 
	 * @param message
	 * @param user
	 * @return
	 */
	public String getSmartResponse(String message, User user) {
		String response = "Bot is running";
		System.out.println(message);
		String resourcesPath = getResourcesPath();
		System.out.println(resourcesPath);
		MagicBooleans.trace_mode = TRACE_MODE;
		org.alicebot.ab.Bot bot = new org.alicebot.ab.Bot("super", resourcesPath);
		Chat chatSession = new Chat(bot);
		bot.brain.nodeStats();
		response = chatSession.multisentenceRespond(message);
		System.out.println("Response: " + response);
		return response;
	}
}
