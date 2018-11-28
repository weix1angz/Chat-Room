package client;
import javafx.application.Application;

public class ChatClient {
	public static void main(String[] args) {
		String hostName = "localhost";
		int portNumber = 4000;

		ChatBotView view = new ChatBotView(hostName, portNumber);
		try {
			Application.launch(view.getClass(), args);
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}

	}

}
