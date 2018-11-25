package util;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.github.axet.vget.VGet;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;

/**
 * A music chat bot by Minh.
 * 
 * @author Minh Bui
 *
 */

public class MinhsBot extends Bot {
	private AbstractMap<String, String> commandsMap;
	private List<Music> playList;
	private boolean repeat;
	private boolean shuffle;

	public MinhsBot(char characterId) {
		super();

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
		commandsMap.put("", "[response] - comments on the reponse of an user.");
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
			if (command.equals("play")) {
				this.play(Integer.parseInt(msg_tokens[1]));
			}

			return "";
		}
		return response;
	}

	@Override
	public String infoCommand(User user) {
		return "User: " + user.getHandle() + "\t"
				+ "Birthday: " + user.getBirthday().toString();
	}

	@Override
	public String whoamiCommand(User user) {
		return "User: " + user.getHandle() + "\t" 
			+ "IP address: " + user.getConnectionInfo();
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
