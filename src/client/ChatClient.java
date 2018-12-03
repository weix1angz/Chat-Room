package client;
import javafx.application.Application;
import javafx.stage.Modality;
import client.SignInView;

public class ChatClient {
	public static void main(String[] args){		
		try {
			Application.launch(SignInView.class);
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}

	}
}
