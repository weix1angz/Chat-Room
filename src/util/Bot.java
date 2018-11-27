package util;

import java.util.AbstractMap;
import java.util.HashMap;

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
	
	public Bot() {
		botCharacterId = '!';
		defaultCommandsList = new HashMap<>();
		defaultCommandsList.put("help", " - list out the available commands for the current bot. ");
		defaultCommandsList.put("info", "[USER] - prints out the information of User.");
		defaultCommandsList.put("date", " - prints out the current date.");
		defaultCommandsList.put("whoami", " - prints out the user's client info such as IP addresses, ...");
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
	public abstract String getResponses(String message, User user);
	

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
}