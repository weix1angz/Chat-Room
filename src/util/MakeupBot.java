package util;

import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MakeupBot extends Bot {
	private AbstractMap<String, String> commandsMap;
	private LipProducts lip;
	private String[] category = {"Lipstick","Liquid-Lipstick", "Lip-Stain", "Lip-Gloss","Lip-Liner",
	"Lip-Plumper", "LipBalm"};
	public MakeupBot(char characterId) {
		super();
		commandsMap = new HashMap<>();
		lip = new LipProducts();
		commandsMap.put("brand", "- the brand list for lip product.");
		commandsMap.put("cate", "- the category list for lip category.");
		commandsMap.put("price", "[brand][cate]- the price of specific product");
		commandsMap.put("rate", "[brand][cate]- check the rate in sephora of specific products");
		commandsMap.put("image", "[brand][cate][num]- show the image of specific lip products"
				+ " gave a picture that the color of the specific products.");
		commandsMap.put("num", "[brand][cate]- the number of each categories of lip products");
		this.setBotCharacterId(characterId);
	}

	@Override
	public String getBotSignature() {
		return " [MakeupBot] ";
	}

	@Override
	public String infoCommand(User user) {
		return "User: " + user.getHandle() + "\t"
				+ "Birthday: " + user.getBirthday().toString();
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
	public String getResponses(String message, User user) {
		if (message == null || user == null)
			return "Something wrong happened.";
		// Split the command into multiple string delimited by space character.
		String[] msg_tokens = message.split(" ");

		// Get the command.
		String command = msg_tokens[0].substring(1, msg_tokens[0].length());

		// Response
		String response = "";

		// Need to
		if (getDefaultCommandsList().containsKey(command)) {
			if (command.equals("help")) {
				response += helpCommand();
			} else if (command.equals("info")) {
				response += infoCommand(user);
			} else if (command.equals("date")) {
				response += dateCommand();
			} else if (command.equals("whoami")) {
				response += whoamiCommand(user);
			}

		} else {
			// TODO: If it's not a default command then find those commands in this bot's
			// command list.
			if(command.equals("brand")) {
				response += lip.getbrand();
			}
			else if(command.equals("cate")) {
				response += lip.getrate();
			}
			
		}
		return response;
	}
	

}
