package util;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;

public class MakeupBot extends Bot {
	private AbstractMap<String, String> commandsMap;
	private String[] category = {"Lipstick","Liquid-Lipstick", "Lip-Stain", "Lip-Gloss","Lip-Liner",
	"Lip-Plumper", "LipBalm"};
	public MakeupBot() {
		super();
		commandsMap = new HashMap<>();
		commandsMap.put("brand", "- the brand list for lip product.");
		commandsMap.put("cate", "- the category list for lip category.");
		
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
