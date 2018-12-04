package server.Bots;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.github.axet.vget.VGet;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;

import server.ChatClientThread;
import server.Response;
import server.User;


/**
 * A music chat bot by Minh.
 * 
 * @author Minh Bui
 *
 */

public class MinhsBot extends server.Bots.Bot {
	private AbstractMap<String, String> commandsMap;
	private AbstractMap<String, AbstractMap<String, Integer>> commandsCounter;
	private List<Music> playList;
	private boolean repeat;
	private boolean shuffle;
	
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

	public MinhsBot(char characterId) {
		super();
		commandsCounter = new HashMap<>();
		commandsMap = new HashMap<>();
		commandsMap.put("import", "[URL] - given a Youtube URL, import the URL to the play list.");
		commandsMap.put("remove", "[ID]/[Name] - given an ID or name, remove the music from the play list.");
		commandsMap.put("list", " - lists out the current songs in the playlist.");
		commandsMap.put("play",
				"[ID]/[Name] - given a name or an ID, play the song in the list. If nothing is given, resume playing the current song.\n"
						+ "");
		commandsMap.put("current", " - displays the current playing song.");
		commandsMap.put("pause", " - pause the current song");
		commandsMap.put("repeat", " - toggle repeat mode\n" + "");
		commandsMap.put("shuffle", " - toggle shuffle mode");
		commandsMap.put("rate",
				"[User]/[MusicID]/[MusicName] - give a random rate of a music or an user from 0 to 10.");
		
		
		playList = new ArrayList<>();
		repeat = false;
		this.setBotCharacterId(characterId);
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
	public Response getResponses(String message, User user, List<ChatClientThread> clients) {
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
				response += infoCommand(user);
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
			if (command.equals("play")) {
				//this.play(Integer.parseInt(msg_tokens[1]));
				response = "play";
				if (msg_tokens.length == 2)
					data = msg_tokens[1];
				Response resP = new Response(response, data);
				resP.setUrl(false);
				return resP;
				
			}else if (command.equals("rate")) {
				response += this.rate(msg_tokens[1]);
			}  else if (command.equals("systemcmd_kick")) {
				response = message;
				if (msg_tokens.length == 2) {
					Iterator<ChatClientThread> iter = clients.iterator();
					while (iter.hasNext()) {
					    ChatClientThread client = iter.next();
					    if (client.getUser().getHandle().equals(msg_tokens[1])) {
					        iter.remove();
					        client.close();
					    }
					}
				}
			}else {
				response = getRandRes();
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
	 * Return a random rating for a subject.
	 * 
	 * @param subject Could be an user or any kind of topic.
	 * @return A random rating for a subject.
	 */
	public String rate(String subject) {
		List<String> responses = new ArrayList<>();
		Random rndGen = new Random();
		int score = rndGen.nextInt(10) + 1;
		if (score >= 8) {
			responses.add(score + "/10. Superb.");
			responses.add("I like it! :^)");
			responses.add(" <3 ");
			responses.add(subject + " is the best!");
		} else if (score <= 8 && score >= 6) {
			responses.add(subject + " is okay.");
			responses.add("I give " + subject + " a score of " + score + "/10.");
		} else {
			responses.add(subject + " is meh.");
			responses.add(subject + " sucks!");
			responses.add(subject + " is trash. :) Fight me. :)");
			responses.add("In the trash bin. :^)");
		}
		return responses.get(rndGen.nextInt(responses.size()));
	}
	
	/**
	 * 
	 * @return
	 */
	public String getRandRes() {
		List<String> responses = new ArrayList<>();
		Random rndGen = new Random();
		responses.add("Is this heaven? ༼ つ ◕_◕ ༽つ ");
		responses.add("Campers! Campers! Come and get me campers!");
		responses.add("???? :^)");
		responses.add("( ͡° ͜ʖ ͡°)");
		responses.add("\\╭☞ \\╭☞");
		responses.add("OMG.");
		responses.add("┬┴┬┴┤ ͜ʖ ͡°) ├┬┴┬┴");
		responses.add("............................................________ \n" + 
				"....................................,.-'\"...................``~., \n" + 
				".............................,.-\"...................................\"-., \n" + 
				".........................,/...............................................\":, \n" + 
				".....................,?......................................................, \n" + 
				".................../...........................................................,} \n" + 
				"................./......................................................,:`^`..} \n" + 
				".............../...................................................,:\"........./ \n" + 
				"..............?.....__.........................................:`.........../ \n" + 
				"............./__.(.....\"~-,_..............................,:`........../ \n" + 
				".........../(_....\"~,_........\"~,_....................,:`........_/ \n" + 
				"..........{.._$;_......\"=,_.......\"-,_.......,.-~-,},.~\";/....} \n" + 
				"...........((.....*~_.......\"=-._......\";,,./`..../\"............../ \n" + 
				"...,,,___.`~,......\"~.,....................`.....}............../ \n" + 
				"............(....`=-,,.......`........................(......;_,,-\" \n" + 
				"............/.`~,......`-...................................../ \n" + 
				".............`~.*-,.....................................|,./.....,__ \n" + 
				",,_..........}.>-._...................................|..............`=~-, \n" + 
				".....`=~-,__......`,................................. \n" + 
				"...................`=~-,,.,............................... \n" + 
				"................................`:,,...........................`..............__ \n" + 
				".....................................`=-,...................,%`>--==`` \n" + 
				"........................................_..........._,-%.......`");
		return responses.get(rndGen.nextInt(responses.size()));
	}

	@Override
	public String infoCommand(User user) {
		return "User: " + user.getHandle() + "\t" + "Birthday: " + user.getAge();
	}

	@Override
	public String whoamiCommand(User user) {
		return "User: " + user.getHandle() + "\t" + "IP address: " + user.getConnectionInfo();
	}

	/**
	 * @return The bot's handle in the server.
	 */
	public String getBotSignature() {
		return "[MBOT] ";
	}

	/**
	 * the Minh's Bot specific fields.
	 * 
	 * @author Minh Bui
	 *
	 */

	private class Music {
		private String name;
		private String url;

		public Music(String url) {
			this.url = url;
		}
	}

	/**
	 * This method is called when an ``import`` command is issued.
	 * 
	 * @param url The url to the Youtube video.
	 */
	public void addMusic(String url) {
		playList.add(new Music(url));
		try {
			String path = "./";
			VGet v = new VGet(new URL(url), new File(path));
			v.download();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Turning repeat on or off.
	 */
	public void toggleRepeat() {
		this.repeat = !this.repeat;
	}

	public void toggleShuffle() {
		this.shuffle = !this.shuffle;
	}

	public void play(String name) {
	}

	public void play(int id) {

	}
}
