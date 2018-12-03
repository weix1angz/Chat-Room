package client;


/**
 * A view for the chat client.
 * 
 * @author Mingjun Zha, Minh Bui
 */

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.User;

public class ChatBotView extends Stage {
	private int boardlength = 1000;
	private int boardwidth = 500;
	private int buttonwidth = 100;
	private Label username = null;// username text field
	private MenuButton chatroom; // chatroom menu button
	private MenuItem nba;
	private MenuItem music;
	private MenuItem makeup;
	private MenuItem moba;
	//private Button connect;
	private Button clear;
	private TextArea chatboard;
	private Button SendButton; // the send button
	private TextField message; // the message we want to sent

	private String hostName;
	private int portNumber;
	private String userName;
	private ChatServerThread server;
	private Socket socket;
	int initialX;
	int initialY;

	/**
	 * For some reasons this is never called with 
	 * 
	 * Application.launch(view.getClass(), args);
	 * 
	 * That line above always call the default constructor.
	 * We no default constructor is found, Java throws off
	 * some weird errors.
	 * 
	 * @param hostName
	 * @param portNumber
	 */
	public ChatBotView(String hostName, int portNumber, String userName) {
	
		this.hostName = hostName;
		this.portNumber = portNumber;
		this.userName = userName;
		
		//Parameters params = this.getParameters();
				//hostName = params.getRaw().get(0);
				hostName = "localhost";
				portNumber = 4000;
				//portNumber = Integer.parseInt(params.getRaw().get(1));

				this.setTitle("Chat client");
				BorderPane pane = new BorderPane();
				Scene scene = new Scene(pane, boardlength, boardwidth);
				// setting the menu bar
				/*Menu menu = new Menu();
				MenuBar menuBar = new MenuBar(menu);
				pane.setTop(menuBar);*/
				pane.setCenter(layout());
				messageEvent();
		        this.ConnectServer();
				this.setScene(scene);
				this.setResizable(true);
				
				this.show();
	}

	public ChatBotView() {
		
	}
	/*
	@Override
	public void start(Stage stage) throws Exception {
		//Parameters params = this.getParameters();
		//hostName = params.getRaw().get(0);
		hostName = "localhost";
		portNumber = 4000;
		//portNumber = Integer.parseInt(params.getRaw().get(1));

		stage.setTitle("Chat client");
		BorderPane pane = new BorderPane();
		Scene scene = new Scene(pane, boardlength, boardwidth);
		// setting the menu bar
		MenuItem item1 = new MenuItem("New Chat");
		MenuItem item2 = new MenuItem("History");
		Menu menu = new Menu("File");
		menu.getItems().addAll(item1, item2);
		MenuBar menuBar = new MenuBar(menu);
		pane.setTop(menuBar);
		pane.setCenter(layout());
		messageEvent();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();

	}
*/
	public HBox layout() {
		VBox buttons = buttonset();// setting the buttonset
		VBox chatAndSend = Initchatboard();// initialized chatboard
		HBox Layout = new HBox(buttons, chatAndSend);
		Layout.setHgrow(chatAndSend, Priority.ALWAYS);
		Layout.setPadding(new Insets(30, 30, 20, 20));
		Layout.setSpacing(20);
		return Layout;
	}

	public VBox buttonset() {
		//VBox chatroom = dropdownButton();
		VBox username = usernameBox();
		/*
		connect = new Button("Connect");
		
		connect.setOnAction(event -> {
			this.ConnectServer();
		});
		*/
		//connect.setPrefWidth(buttonwidth);
		clear = new Button("Clear");
		clear.setOnAction(event -> {
			chatboard.clear();
			//this.openURL("https://dota2.gamepedia.com/Morphling");
		});
		clear.setPrefWidth(buttonwidth);
		VBox buttonSet = new VBox(username, clear);
		buttonSet.setSpacing(50);
		return buttonSet;
	}

	/*public VBox dropdownButton() {
		VBox chatroomlabel = new VBox();
		Text chat = new Text("Chatroom");
		nba = new MenuItem("NBA");
		music = new MenuItem("Music");
		moba = new MenuItem("LOL game");
		makeup = new MenuItem("Lip products");
		// menuitem get selected
		makeup.setOnAction(event -> {
			chatroom.setText(makeup.getText());
		});
		moba.setOnAction(event -> {
			chatroom.setText(moba.getText());
		});
		music.setOnAction(event -> {
			chatroom.setText(music.getText());
		});
		nba.setOnAction(event -> {
			chatroom.setText(nba.getText());
		});
		chatroom = new MenuButton("NBA", null, nba, music, moba, makeup);
		chatroom.setPrefWidth(buttonwidth);
		chatroomlabel.getChildren().add(chat);
		chatroomlabel.getChildren().add(chatroom);
		return chatroomlabel;
	}*/

	public VBox usernameBox() {
		Text user = new Text("Username");
		username = new Label(userName);
		VBox userset = new VBox(user, username);
		return userset;
	}

	public VBox Initchatboard() {
		chatboard = new TextArea();
		chatboard.setPrefHeight(300);
		chatboard.setPrefWidth(500);
		chatboard.setEditable(false);
		message = new TextField();
		message.setPrefWidth(445);
		SendButton = new Button("Send");
		SendButton.setPrefWidth(50);
		HBox hb = new HBox(message, SendButton);
		VBox vb = new VBox(chatboard, hb);
		hb.setHgrow(message, Priority.ALWAYS);
		vb.setVgrow(chatboard, Priority.ALWAYS);
		vb.setSpacing(30);
		return vb;
	}

	public void messageEvent() {
		message.setOnAction(event -> {
			if(message.getText() != null) {
				if (socket != null && socket.isConnected()) {
					String chat = message.getText();
					String name = username.getText();
					String chatrooms = chatroom.getText();
					if (!chat.isEmpty()) {
						//chatboard.appendText(name + "@" + chatrooms + " : " + chat + "\n");
					}
	
					if (chat != null && !chat.isEmpty())
						server.addMsg(chat);
					message.setText("");
				}
			}
		});
		SendButton.setOnAction(events -> {
			if(message.getText() != null) {
				if (socket != null && socket.isConnected()) {
					String chat = message.getText();
					String name = username.getText();
					String chatrooms = chatroom.getText();
					if (!chat.isEmpty()) {
						//chatboard.appendText(name + "@" + chatrooms + " : " + chat + "\n");
					}
	
					if (chat != null && !chat.isEmpty())
						server.addMsg(chat);
					message.setText("");
				}
			}
		});
	}

	public void ConnectServer() {
		try {
			socket = new Socket(hostName, portNumber);
			User user = new User(username.getText(), socket);

			server = new ChatServerThread(this, socket, user);
			Thread serverThread = new Thread(server);
			serverThread.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void appendMessage(String msg) {
		chatboard.appendText(msg + "\n");
	}

	public void openURL(String url) {
		Stage newStage = new Stage();
		WebView webview = new WebView();
	    webview.getEngine().load(url);
	    webview.setPrefSize(640, 390);
	    newStage.setScene(new Scene (webview)); 
	    newStage.show();
	}
}