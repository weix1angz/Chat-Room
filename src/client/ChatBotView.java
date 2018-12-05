package client;

/**
 * A view for the chat client.
 * 
 * @author Mingjun Zha, Minh Bui
 */

import java.io.File;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;


public class ChatBotView extends Stage {
	private MusicThread msThread;
	private Thread actualMsThread;

	private int boardlength = 1150;
	private int boardwidth = 500;
	private int buttonwidth = 100;
	private Label username = null;// username text field
	
	// private Button connect;
	private Button clear;
	private TextArea chatboard;
	private Button SendButton; // the send button
	private TextField message; // the message we want to sent

	private String userName;
	private ChatServerThread server;

	private Canvas canvas;
	private GraphicsContext gc;
	int initialX;
	int initialY;
	@FXML
	private ImageView imageviewNBA;
	private ImageView imageviewMus;
	private ImageView imageviewLOL;
	private ImageView imageviewLip;
	private Image nba;
	private Image music;
	private Image makeup;
	private Image lol;

	/**
	 * For some reasons this is never called with
	 * 
	 * Application.launch(view.getClass(), args);
	 * 
	 * That line above always call the default constructor. We no default
	 * constructor is found, Java throws off some weird errors.
	 * 
	 * @param hostName
	 * @param portNumber
	 */
	public ChatBotView(String hostName, int portNumber, String userName, ChatServerThread server) {
		this.server = server;
		this.userName = userName;

		this.setTitle("Chat client");
		BorderPane pane = new BorderPane();
		Scene scene = new Scene(pane, boardlength, boardwidth);
		pane.setCenter(images());
		messageEvent();

		this.setScene(scene);
		this.setResizable(true);

		this.show();

		this.setOnCloseRequest(e -> {
			server.close();
		});

		this.setScene(scene);

	}

	public ChatBotView() {

	}

	public VBox layout() {
		HBox buttons = buttonset();// setting the buttonset
		VBox chatAndSend = Initchatboard();// initialized chatboard
		VBox Layout = new VBox(buttons, chatAndSend);
		Layout.setVgrow(chatAndSend, Priority.ALWAYS);
		Layout.setPadding(new Insets(30, 40, 20, 20));
		Layout.setSpacing(20);
		return Layout;
	}
	public HBox images() {
		this.GIF();
		canvas =  new Canvas(100,500);
		gc = canvas.getGraphicsContext2D();
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300),
			new AnimationHandler()));
		timeline.setCycleCount(timeline.INDEFINITE);
		timeline.play();
		
		VBox vb = new VBox(imageviewNBA,imageviewMus,imageviewLip,imageviewLOL);
		vb.setVgrow(imageviewNBA, Priority.ALWAYS);
		vb.setVgrow(imageviewMus, Priority.ALWAYS);
		vb.setVgrow(imageviewLip, Priority.ALWAYS);
		vb.setVgrow(imageviewLOL, Priority.ALWAYS);
		vb.setSpacing(20);
		VBox layou = layout();
		HBox hb = new HBox(canvas,layou);
		hb.setHgrow(canvas, Priority.ALWAYS);
		hb.setHgrow(layou, Priority.ALWAYS);
		hb.setPadding(new Insets(10,10,10,10));
		return hb;
	}

	private class AnimationHandler implements EventHandler<ActionEvent> {
		/*
		 * /public AnimationHandler() { System.out.println("here");
		 * gc.drawImage(nba,0,0); gc.clearRect(0, 0, 100, 100); gc.drawImage(nba,0,0);
		 * 
		 * }
		 */
		int tick = 0;

		@Override
		public void handle(ActionEvent arg0) {
			tick++;
			if (tick > 0) {
				gc.drawImage(nba, 0, 0);

			}
			if (tick > 8) {
				gc.clearRect(0, 0, 100, 100);
				gc.drawImage(lol, 0, 120);
			}
			if (tick > 16) {
				gc.clearRect(0, 120, 100, 100);
				gc.drawImage(makeup, 0, 240);
			}
			if (tick > 24) {
				gc.clearRect(0, 240, 100, 100);
				gc.drawImage(music, 0, 360);
			}
			if (tick >= 30) {
				gc.clearRect(0, 360, 100, 100);
				tick = 1;
			}

		}

	}

	public void GIF() {
		imageviewNBA = new ImageView();
		imageviewMus = new ImageView();
		imageviewLOL = new ImageView();
		imageviewLip = new ImageView();
		nba = new Image(new File("nba.gif").toURI().toString());
		lol = new Image(new File("lol.gif").toURI().toString());
		makeup = new Image(new File("makeup.gif").toURI().toString());
		music = new Image(new File("music.gif").toURI().toString());
		imageviewNBA.setImage(nba);
		imageviewMus.setImage(music);
		imageviewLOL.setImage(lol);
		imageviewLip.setImage(makeup);

	}

	public HBox buttonset() {
		clear = new Button("Clear");
		clear.setOnAction(event -> {
			chatboard.clear();

		});
		clear.setPrefWidth(buttonwidth);
		HBox user = usernameBox();
		HBox buttonSet = new HBox(user, clear);
		buttonSet.setHgrow(user, Priority.ALWAYS);
		buttonSet.setSpacing(700);
		return buttonSet;
	}

	public HBox usernameBox() {
		Text user = new Text("Username: ");
		username = new Label(userName);
		HBox userset = new HBox(user, username);
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
			if (message.getText() != null) {

				String chat = message.getText();
				if (chat != null && !chat.isEmpty())
					server.addMsg(chat);
				message.setText("");

			}
		});
		SendButton.setOnAction(events -> {
			if (message.getText() != null) {

				String chat = message.getText();
				if (chat != null && !chat.isEmpty())
					server.addMsg(chat);
				message.setText("");

			}
		});
	}

	public void appendMessage(String msg) {
		chatboard.appendText(msg + "\n");
	}

	public void openURL(String url) {
		Stage newStage = new Stage();
		WebView webview = new WebView();
		webview.getEngine().load(url);
		webview.setPrefSize(640, 390);
		newStage.setScene(new Scene(webview));
		newStage.show();
	}

	public void pauseMusic() {
		if (msThread != null)
			msThread.pause();
	}

	public void resumeMusic() {
		if (msThread != null) {
			Thread actualMsThread = new Thread(msThread);
			actualMsThread.start();
		}
	}

	public void playMusic(String name) {
		String musicFile = "./SampleMusic/" + name; // For example
		if (msThread == null) {
			msThread = new MusicThread(musicFile);
			actualMsThread = new Thread(msThread);
			actualMsThread.start();
		} else {
			try {
				if (msThread.isPlaying())
					msThread.pause();
			} catch (Exception e) {

			}
			msThread = new MusicThread(musicFile);
			actualMsThread = new Thread(msThread);
			actualMsThread.start();
		}
	}
}