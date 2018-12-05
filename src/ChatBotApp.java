import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import server.Bots.Bot;
import server.Bots.MinhsBot;
import server.User;

/**
 * Driver class to test bots' functionalities.
 * @author ForteGS
 *
 */

public class ChatBotApp {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Bot mb1 = new MinhsBot('!');
		
		Scanner in = new Scanner(System.in);
		String input = null;
		do {
			System.out.print("> ");
			input = in.next();
			System.out.println(mb1.getResponses(input, new User("defaultUser", new Socket("localhost", 4000)), null));
		} while (input != "logout");
		in.close();
	}
}
