package util;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;

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
		commandsMap.put("price-brand-cate", "- the price of specific product");
		commandsMap.put("rate-brand-cate", "- check the rate in sephora of specific products");
		this.setBotCharacterId(characterId);
	}

	@Override
	public String getBotSignature() {
		// TODO Auto-generated method stub
		return null;
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
	public String getResponses(String message, User user) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
