package server.Bots;

import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import server.Response;
import server.User;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String dateCommand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String whoamiCommand(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getResponses(String message, User user) {
		// TODO Auto-generated method stub
		return null;
	}
}


