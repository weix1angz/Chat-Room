package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import client.ChatBotView;
import javafx.application.Application;
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

public class SignInView extends Application{
	boolean userFound = false;
	private String hostName = "localhost";
	private int portNumber = 4000;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox signInWindow = new VBox(30);
		signInWindow.setPrefHeight(200);
		signInWindow.setPrefWidth(350);
		signInWindow.setPadding(new Insets(35,30,35,45));
		signInWindow.setStyle("-fx-background-color: white");
		Scene scene = new Scene(signInWindow);
		
		
		Label welcome = new Label("\t    Welcom to the CHATBOT!");
		
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
			if(checkUserName(userNameText.getText(), passwordText.getText())) {
				System.out.println("user found");
				setuserFound();
			}else {
				new Alert(Alert.AlertType.WARNING, "user not found or wrong password").showAndWait();
				System.out.println("user not found or wrong password");
				
			}
			if(userFound) {
				ChatBotView view = new ChatBotView(hostName, portNumber, userNameText.getText());
				primaryStage.close();}
		});
		
		signUp.setOnAction(MouseClicked -> {
			SignUpView signup = new SignUpView();
			signup.show();
		});
		
		signInWindow.getChildren().addAll(welcome, userName, password, button);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("SignIn");
		primaryStage.show();
	}
	
	
	
	private boolean checkUserName(String userName, String password) {
		try {
			File file = new File("userInfo.txt");
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] info = line.split(" ");
				System.out.println(info);
				if(userName.equals(info[0]) && password.equals(info[1])) return true;
			}
			
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	private void setuserFound() {
		userFound = true;
	}
	
	public boolean getuserFound() {
		return userFound;
	}




}
