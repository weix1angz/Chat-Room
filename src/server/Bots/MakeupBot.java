package server.Bots;

import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import server.Response;
import server.User;
/**
 * This is the MakeupBot program which extends the super class Bot.
 * MakeupBot totally has 12 commands, except the commands that super class have, it got
 * 6 more commands. Depending upon the commands, the bot will give the user some specific
 * repsonses that we have already defined.
 * @author Mingjun Zha
 *
 */
public class MakeupBot extends Bot {
	private AbstractMap<String, String> commandsMap;
	private LipProducts lip;
	private AbstractMap<String, AbstractMap<String, Integer>> commandsCounter;
	private String[] category = {"Lipstick","Liquid-Lipstick", "Lip-Stain", "Lip-Gloss","Lip-Liner",
	"Lip-Plumper", "LipBalm"};
	
	/**
	 * The constructor of MakeupBot, initializing each fields and put each command into
	 * the commandsMap
	 * @param characterId, through using the charcterId, it represents different bot, makeupBot is *
	 */
	public MakeupBot(char characterId) {
		super();
		commandsCounter = new HashMap<>();
		commandsMap = new HashMap<>();
		lip = new LipProducts();
		commandsMap.put("brand", "- the brand list for lip product.");
		commandsMap.put("cate", "- the category list for lip category.");
		commandsMap.put("price", "- [brand][cate]- the price of specific product");
		commandsMap.put("rate", "- [brand][cate]- check the rate in sephora of specific products");
		commandsMap.put("image", "- [brand][cate][num]- show the image of specific lip products"
				+ " gave a picture that the color of the specific products.");
		commandsMap.put("num", "- [brand][cate]- the number of each categories of lip products");
		this.setBotCharacterId(characterId);
	}
	
	/**
	 * This function is used to count the using times for each commands.
	 * @return AbstractMap<String, Integer>, the map which key is string, value is the counter
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
	
	
	@Override
	/**
	 * return MakeupBot's signature
	 */
	public String getBotSignature() {
		return " [MakeupBot] ";
	}
	
	/**
	 * return the String of each command and their functions
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
	
	/**
	 * return the String of data
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
	 * return the response object by getting the message by user
	 */
	@Override
	public Response getResponses(String message, User user, List clients) {
		if (message == null || user == null)
			return new Response("Something wrong happened.", null);
		
		// Split the command into multiple string delimited by space character.
		String[] msg_tokens = message.split(" ");

		// Get the command.
		String command = msg_tokens[0].substring(1, msg_tokens[0].length());
		
		// Response
		String response = "";
		String data = null;
		
		if (!commandsCounter.containsKey(user.getHandle())) {
			commandsCounter.put(user.getHandle(), createNewCmdsCounter());
		}


		// Need to
		if (getDefaultCommandsList().containsKey(command)) {
			if (command.equals("help")) {
				response += helpCommand();
			} else if (command.equals("info")) {
				response += infoCommand(msg_tokens[1]);
			} else if (command.equals("date")) {
				response += dateCommand();
			} else if (command.equals("whoami")) {
				response += whoamiCommand(user);
			} else if (command.equals("ttm")) {
				response = getSmartResponse(message.substring(1, message.length()), user);
			} else if (command.equals("geturl")) {
				response = message;
				if (msg_tokens.length == 2)
					data = msg_tokens[1];
			}

		} else {
			// TODO: If it's not a default command then find those commands in this bot's
			// command list.
			if(command.equals("brand")) {
				response += lip.getbrand();
				
			}
			else if(command.equals("cate")) {
				response += Arrays.toString(category);
	
			}else if(command.equals("price")) {
				
				if(msg_tokens.length>=3) {
					String brand = msg_tokens[1];
					String cate = msg_tokens[2];
					lip.geteverything(brand, cate);
					response += lip.getprice();
				}else {
					response+="Invalid command";
				}
			}else if(command.equals("rate")) {
				if(msg_tokens.length>=3) {
					String brand = msg_tokens[1];
					String cate = msg_tokens[2];
					lip.geteverything(brand, cate);
					response += lip.getrate();
				}else {
					response+="Invalid command";
				}
			}
			else if(command.equals("num")) {
				
				if(msg_tokens.length>=3) {
					String brand = msg_tokens[1];
					String cate = msg_tokens[2];
					response += lip.getNum(brand, cate);
				}else {
					response+="Invalid command";
				}
			}else if(command.equals("image")) { //showing the image of specific lipsticks
				if(msg_tokens.length>=4) {
					String brand = msg_tokens[1];
					String cate = msg_tokens[2];
					String num = msg_tokens[3];
					data = lip.getImageurl(brand, cate, num);
					if(lip.Isurl() == true) {
						response+=lip.getImageurl(brand, cate, num);
					}else {
						data = null;
						response+=lip.getImageurl(brand, cate, num);
					}
				}else {
					response+="Invalid command";
				}
			}
			else {
				response+="Invalid command";
			}
			
		}
		// Update commands counter.
				if (commandsCounter.get(user.getHandle()).containsKey(command)) {
					int oldValue = commandsCounter.get(user.getHandle()).get(command);
					commandsCounter.get(user.getHandle()).put(command, oldValue + 1);
					if ((oldValue + 1) % 5 == 0) {
						response += "\n" + this.getBotSignature() + command + " has been used the fifth time by " + user.getHandle();
					}
				}
		return new Response(response, data);
	}
	
	/**
	 * return the Stirng of user's information and ip address
	 */
	@Override
	public String whoamiCommand(User user) {
		return "User: " + user.getHandle() + "\t" + "IP address: " + user.getConnectionInfo();
	}

	
}


