import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChatBotView extends Application{
	private int boardlength = 700;
	private int boardwidth = 400;
	private int buttonwidth = 100;
	private TextField username;//username text field
	private MenuButton chatroom; // chatroom menu button
	private MenuItem nba;
	private MenuItem music;
	private MenuItem makeup;
	private MenuItem moba;
	private Button connect;
	private Button clear;
	private TextArea chatboard; 
	private Button SendButton; // the send button
	private TextField message; //the message we want to sent
	
	public ChatBotView() {
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("hasCode.com - Websocket chat client");
		BorderPane pane = new BorderPane();
		Scene scene = new Scene(pane, boardlength, boardwidth);
		//setting the menu bar
		MenuItem item1 = new MenuItem("New Chat");
		MenuItem item2 = new MenuItem("History");
		Menu menu = new Menu("File");
		menu.getItems().addAll(item1,item2);
		MenuBar menuBar = new MenuBar(menu);	
		pane.setTop(menuBar);
		pane.setCenter(layout());
		messageEvent();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		
	}
	public HBox layout() {
		VBox buttons = buttonset();//setting the buttonset
		VBox chatAndSend = Initchatboard();//initialized chatboard
		HBox Layout = new HBox(buttons, chatAndSend);
		Layout.setPadding(new Insets(10,10,10,10));
		Layout.setSpacing(20);
		return Layout;
	}
	
	public VBox buttonset() {
		VBox chatroom = dropdownButton();
		VBox username = usernameBox();
		connect = new Button("Connect");
		connect.setPrefWidth(buttonwidth);
		clear = new Button("Clear");
		clear.setOnAction(event->{
			chatboard.clear();
		});
		clear.setPrefWidth(buttonwidth);
		VBox buttonSet = new VBox(username, chatroom, connect,clear);
		buttonSet.setSpacing(50);
		return buttonSet;
	}
	
	public VBox dropdownButton() {
		VBox chatroomlabel = new VBox();
		Text chat = new Text("Chatroom");
		nba = new MenuItem("NBA");
		music = new MenuItem("Music");
		moba = new MenuItem("LOL game");
		makeup = new MenuItem("Lip products");
		//menuitem get selected
		makeup.setOnAction(event->{
			chatroom.setText(makeup.getText());
		});
		moba.setOnAction(event->{
			chatroom.setText(moba.getText());
		});
		music.setOnAction(event->{
			chatroom.setText(music.getText());
		});
		nba.setOnAction(event->{
			chatroom.setText(nba.getText());
		});
		chatroom = new MenuButton("NBA",null, nba, music, moba, makeup);
		chatroom.setPrefWidth(buttonwidth);
		chatroomlabel.getChildren().add(chat);
		chatroomlabel.getChildren().add(chatroom);
		return chatroomlabel;
	}
	 
	public VBox usernameBox() {
		Text user = new Text("Username");
		username = new TextField();
		VBox userset = new VBox(user, username);
		return userset;
	}
	
	public VBox Initchatboard() {
		chatboard = new TextArea();
		chatboard.setPrefHeight(300);
		chatboard.setPrefWidth(500);
		chatboard.setEditable(false);
		message = new TextField();
		message.setPrefWidth(450);
		SendButton = new Button("Send");
		SendButton.setPrefWidth(50);
		HBox hb = new HBox(message, SendButton);
		VBox vb = new VBox(chatboard,hb);
		vb.setSpacing(30);
		return vb;
	}
	public void messageEvent() {
		message.setOnAction(event->{
			//TODO
			String chat = message.getText();
			String name = username.getText();
			String chatrooms = chatroom.getText();
			if(!chat.isEmpty()) {
				chatboard.appendText(name+"@"+chatrooms+" : " + chat+"\n");
			}
			message.setText("");
		});
		SendButton.setOnAction(events->{
			//TODO
			String chat = message.getText();
			String name = username.getText();
			String chatrooms = chatroom.getText();
			if(!chat.isEmpty()) {
				chatboard.appendText(name+"@"+chatrooms+" : " + chat+"\n");
			}
			message.setText("");
		});
	}
	public void ConnectServer() {
		//TODO
	}
	 
}
