import javafx.application.Application;

public class ChatBotMain {

	public static void main(String[] args) {
		ChatBotView view =  new ChatBotView();
		try {
			Application.launch(view.getClass(), args);
		} catch (Exception e) {
			System.err.println("The GUI system explodes!");
			return;
		}

	}

}
