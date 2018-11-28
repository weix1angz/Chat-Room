package server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignUpView extends Stage{
	Button ok;
	Button cancel;
	File file = new File("userInfo.txt");
	public SignUpView() {
		super();
		
		VBox signInWindow = new VBox(30);
		
		signInWindow.setPadding(new Insets(35,30,35,45));
		signInWindow.setStyle("-fx-background-color: white");
		Scene scene = new Scene(signInWindow);
		
		HBox userName = new HBox();
		Label userNameLabel = new Label("USERNAME:\t");
		TextField userNameText = new TextField();
		userName.getChildren().addAll(userNameLabel, userNameText);
		
		
		HBox password = new HBox();
		Label passwordLabel = new Label("PASSWORD:\t");
		TextField passwordText = new TextField();
		password.getChildren().addAll(passwordLabel, passwordText);
		
		
		HBox name = new HBox();
		Label firstName = new Label("First Name:\t");
		TextField firstNameText = new TextField();
		Label LastName = new Label( "Last  Name:\t");
		TextField lastNameText = new TextField();
		name.getChildren().addAll(firstName, firstNameText, LastName, lastNameText);
		
		HBox age = new HBox();
		Label userAge = new Label( "AGE:        \t");
		TextField userAgeText = new TextField();
		age.getChildren().addAll(userAge, userAgeText);
		
		HBox major = new HBox();
		Label userMajor = new Label( "MAJOR:     \t");
		TextField userMajorText = new TextField();
		major.getChildren().addAll(userMajor, userMajorText);
		
		HBox button = new HBox(120);
		ok = new Button("OK");
		ok.setOnAction(MouseClicked ->{okAction(userNameText.getText(), passwordText.getText(),
				firstNameText.getText(), lastNameText.getText(), userAgeText.getText(),
				userMajorText.getText());});
		cancel = new Button("Cancel");
		cancel.setOnAction(MouseClicked ->{cancelAction();});
		button.getChildren().addAll(ok, cancel);
		
		signInWindow.getChildren().addAll(userName, 
				password, name, age, major, button);
		
		this.setScene(scene);
		this.setTitle("SignUp");	
	}
	
	public void okAction(String userName, String password,
			String firstName, String lastName, String age, String major) {	
		if(createUser(userName, password, firstName, lastName, age, major)) this.close();
		
	}
	
	public void cancelAction() {
		this.close();
	}
	
	private boolean createUser(String userName, String password,
			String firstName, String lastName, String age, String major) {
		if(checkUserName(userName)) return false;
		try {
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			String userInfo = userName + " " + password + " " + firstName + " "
					+ lastName + " " + age + " " + major + "\n";
			writer.append(userInfo);
			writer.close();
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
		return true;
	}
	
	private boolean checkUserName(String userName) {
		Scanner sc = null;
		try {
			file = new File("userInfo.txt");
			sc = new Scanner(file);
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] info = line.split(" ");
				if(userName.equals(info[0])) {
					new Alert(Alert.AlertType.INFORMATION, "user name has been used").showAndWait();
					sc.close();
					return true;
				}
			}
			
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
		if(sc != null) sc.close();
		return false;
	}

}
