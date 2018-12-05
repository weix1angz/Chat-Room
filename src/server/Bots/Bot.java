package server.Bots;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;

import server.ChatClientThread;
import server.Response;

import server.User;

/**
 * This bot class implements basic functionality of the chat bot. Note that this
 * class should never be instantiated. Whenever we need to create a chat bot,
 * instantiate a new bot object that extends this class, for example:
 * 
 * Bot newBot = new MinhsBot();
 * 
 * @author Minh Bui
 */

public abstract class Bot {

	// A map of commands with the command names as keys and commands' parameters as
	// values.
	private static AbstractMap<String, String> defaultCommandsList;

	// A character that identify the
	private char botCharacterId;

	private static final boolean TRACE_MODE = false;
	static String botName = "super";

	private static org.alicebot.ab.Bot bot;

	private static Chat chatSession;
	/**
	 * constructor
	 */
	public Bot() {

		String resourcesPath = getResourcesPath();
		MagicBooleans.trace_mode = TRACE_MODE;
		bot = new org.alicebot.ab.Bot("super", resourcesPath);
		// bot.writeAIMLFiles();
		chatSession = new Chat(bot);

		botCharacterId = '!';
		defaultCommandsList = new HashMap<>();
		defaultCommandsList.put("help", " - list out the available commands for the current bot. ");
		defaultCommandsList.put("info", "[USER] - prints out the information of User.");
		defaultCommandsList.put("date", " - prints out the current date.");
		defaultCommandsList.put("whoami", " - prints out the user's client info such as IP addresses, ...");
		defaultCommandsList.put("ttm", " - abbreviation for \"talk to me\".");
		defaultCommandsList.put("geturl", "[URL] - pull web resource through url.");
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

	public String infoCommand(String user) {
		User userObj = getUserFromDB(user);
		String userInfo = "User doesn't exist.";
		if (userObj != null) {
			userInfo = "\nUser: " + userObj.getHandle();
			userInfo += "\nName: " + userObj.getFirstName() + " " + userObj.getLastName();
			userInfo += "\nAge: " + userObj.getAge();
			userInfo += "\nMajor: " + userObj.getMajor();
		}
		return userInfo;
	}
	/**
	 * get the user object by using username
	 * @param userName which is the String value
	 * @return user
	 */
	private User getUserFromDB(String userName) {
		Scanner sc = null;
		User user = null;
		try {
			File file = new File(new java.io.File(".").getCanonicalPath() + File.separator + "userInfo.txt");
			file.createNewFile();
			sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] info = line.split(" ");
				if (userName.equals(info[0])) {
					user = new User(info[0], info[1], info[2], info[3], info[4], info[5]);
				}
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			if (sc != null)
				sc.close();
		}

		return user;
	}

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
	public abstract Response getResponses(String message, User user, List<ChatClientThread> clients);

	/**
	 * @return Return a character that identifies this bot.
	 */
	public char getBotCharacterId() {
		return botCharacterId;
	}

	/**
	 * Set the character that identifies this bot to botCharacterId.
	 * 
	 * @param botCharacterId A character that identifies this bot.
	 */
	public void setBotCharacterId(char botCharacterId) {
		this.botCharacterId = botCharacterId;
	}

	/**
	 * @return An abstract map of default commands as keys and their parameters as
	 *         values.
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
		bot.brain.nodeStats();
		String response = chatSession.multisentenceRespond(message);

		return response;
	}
}
