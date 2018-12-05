package client;

import java.net.Socket;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import server.User;

public class SignUpView extends Stage {
	Button ok;
	Button cancel;
	private ChatServerThread clientThread;
	private Socket socket;

	public SignUpView(ChatServerThread clientThread, Socket socket) {
		super();
		this.clientThread = clientThread;
		this.socket = socket;
		VBox signInWindow = new VBox(30);

		signInWindow.setPadding(new Insets(35, 30, 35, 45));
		signInWindow.setStyle("-fx-background-color: white");
		Scene scene = new Scene(signInWindow);

		HBox userName = new HBox();
		Label userNameLabel = new Label("USERNAME*:\t");
		TextField userNameText = new TextField();
		userName.getChildren().addAll(userNameLabel, userNameText);

		HBox password = new HBox();
		Label passwordLabel = new Label("PASSWORD*:\t");
		TextField passwordText = new TextField();
		password.getChildren().addAll(passwordLabel, passwordText);

		HBox name = new HBox();
		Label firstName = new Label("First Name:\t");
		TextField firstNameText = new TextField();
		Label LastName = new Label("Last  Name:\t");
		TextField lastNameText = new TextField();
		name.getChildren().addAll(firstName, firstNameText, LastName, lastNameText);

		HBox age = new HBox();
		Label userAge = new Label("AGE:        \t");
		TextField userAgeText = new TextField();
		age.getChildren().addAll(userAge, userAgeText);

		HBox major = new HBox();
		Label userMajor = new Label("MAJOR:     \t");
		TextField userMajorText = new TextField();
		major.getChildren().addAll(userMajor, userMajorText);

		HBox button = new HBox(120);
		ok = new Button("OK");
		ok.setOnAction(MouseClicked -> {
			if (this.socket != null && this.socket.isConnected()) {
				if ((userNameText.getText() == null || userNameText.getText().isEmpty()) ||
						(passwordText.getText() == null || passwordText.getText() == null) ||
						(userAgeText.getText() == null || userAgeText.getText() == null) ||
						(firstNameText.getText() == null || lastNameText.getText() == null) ||
						(userAgeText.getText() == null || userAgeText.getText() == null) ||
						(userMajorText.getText() == null || userMajorText.getText() == null))
					new Alert(Alert.AlertType.ERROR, "Please fill out all fields.").showAndWait();
				else 
					okAction(userNameText.getText(), passwordText.getText(), firstNameText.getText(),
						lastNameText.getText(), userAgeText.getText(), userMajorText.getText());
			}
		});
		cancel = new Button("Cancel");
		cancel.setOnAction(MouseClicked -> {
			this.close();
		});
		button.getChildren().addAll(ok, cancel);

		signInWindow.getChildren().addAll(userName, password, name, age, major, button);

		this.setScene(scene);
		this.setTitle("SignUp");
	}

	/**
	 * Prompts the client to send new user information to the server.
	 * 
	 * @param userName
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param age
	 * @param major
	 */
	public void okAction(String userName, String password, String firstName, String lastName, String age,
			String major) {
		User newUser = new User(userName, password, firstName, lastName, age, major);
		newUser.setUserCode(User.UserCode.signingUpUser);
		clientThread.sendUserObject(newUser);
	}

	/**
	 * Called when signing up fails.
	 */
	public void signUpFail() {
		new Alert(Alert.AlertType.INFORMATION, "User name has been used").showAndWait();
		System.out.println("Sign up failed.");
	}
	
	public void signUpSuccess() {
		new Alert(Alert.AlertType.INFORMATION, "Sign up successfull. Please log in with the new account.").showAndWait();
		System.out.println("Sign up successfull.");
	}

}