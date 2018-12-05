package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import client.ChatBotView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import server.User;

public class SignInView extends Application {
	private String hostName = "localhost";
	private int portNumber = 4000;

	private String userName;
	private ChatServerThread clientThread;
	private Socket socket;
	private ChatBotView chatView;
	private SignUpView signUpView;

	private Stage priStage;
	private Label connectionStatus;

	public Label getConnectionStatus() {
		return connectionStatus;
	}

	public void setConnectionStatus(Label connectionStatus) {
		this.connectionStatus = connectionStatus;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		priStage = primaryStage;
		VBox signInWindow = new VBox(30);
		signInWindow.setPrefHeight(200);
		signInWindow.setPrefWidth(350);
		signInWindow.setPadding(new Insets(35, 30, 35, 45));
		signInWindow.setStyle("-fx-background-color: white");
		Scene scene = new Scene(signInWindow);

		Label welcome = new Label("\t    Welcom to the CHATBOT!");
		connectionStatus = new Label("\t\t	Offline");
		connectionStatus.setTextFill(Color.RED);

		HBox userName = new HBox();
		Label userNameLabel = new Label("USERNAME:\t");
		TextField userNameText = new TextField();
		userName.getChildren().addAll(userNameLabel, userNameText);

		HBox password = new HBox();
		Label passwordLabel = new Label("PASSWORD:\t");
		PasswordField passwordText = new PasswordField();
		password.getChildren().addAll(passwordLabel, passwordText);

		HBox button = new HBox(120);
		Button signIn = new Button("SIGN IN");
		Button signUp = new Button("SIGN UP");
		button.getChildren().addAll(signIn, signUp);

		signIn.setOnAction(MouseClicked -> {
			if (socket != null && socket.isConnected()) {
				User signingInUser = new User();
				this.userName = userNameText.getText();
				signingInUser.setPassword(passwordText.getText());
				signingInUser.setHandle(userNameText.getText());
				signingInUser.setUserCode(User.UserCode.signingInUser);
				clientThread.sendUserObject(signingInUser);
			}
		});

		signUp.setOnAction(MouseClicked -> {
			signUpView = new SignUpView(clientThread, socket);
			signUpView.show();
		});

		signInWindow.getChildren().addAll(welcome, connectionStatus, userName, password, button);

		primaryStage.setScene(scene);
		primaryStage.setTitle("SignIn");
		primaryStage.show();

		Thread connectingThread = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " is a connectingThread and is running.");

				try {
					while (true) {
						while (socket == null || !socket.isConnected() || socket.isClosed()) {
							connectServer();
							Thread.sleep(7000);
						}

					
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 

				System.out.println(Thread.currentThread().getName() + " is a connectingThread and is done.");
			}
		});

		connectingThread.start();

	}

	public SignUpView getSignUpView() {
		return this.signUpView;
	}

	public ChatBotView getChatBotView() {
		return chatView;
	}

	public ChatBotView successSignedIn() {
		System.out.println("Log on success!");
		chatView = new ChatBotView(hostName, portNumber, userName, clientThread);
		priStage.close();
		return chatView;
	}

	public void backToSignIn() {
		priStage.show();
	}

	public void signInFail() {
		new Alert(Alert.AlertType.WARNING, "user not found or wrong password").showAndWait();
		System.out.println("User not found or wrong password");
	}

	public void connectServer() {
		try {
			socket = new Socket(hostName, portNumber);
			User user = new User(userName, socket);

			clientThread = new ChatServerThread(chatView, this, signUpView, socket, user);
			Thread actualClientThread = new Thread(clientThread);
			actualClientThread.start();

			Platform.runLater(new Runnable() {
				public void run() {
					connectionStatus.setTextFill(Color.GREEN);
					connectionStatus.setText("\t\t	Online");
				}
			});
		} catch (UnknownHostException e) {
		} catch (IOException e) {
		}
	}
}
