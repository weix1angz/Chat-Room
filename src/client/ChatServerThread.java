package client;

/**
 * ChatServerThread.java
 * Handles I/O from client to server.
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import server.Response;

import server.User;

public class ChatServerThread implements Runnable {

	private Socket socket;
	private User user;
	// private final LinkedList<String> pendingMsgs;
	// private Queue<String> viewPendingMsgs;
	private String msg;
	private boolean hasMsgs = false;

	private ObjectOutputStream objOut;
	private ObjectInputStream objIn;

	// Reference to update the GUI once there's response from server.
	private ChatBotView view;
	private SignInView signInView;
	private SignUpView signUpView;

	public ChatServerThread(ChatBotView view, SignInView siView, SignUpView suView, Socket socket, User user) {
		this.view = view;
		signInView = siView;
		signUpView = suView;
		this.socket = socket;
		// pendingMsgs = new LinkedList<String>();
		// viewPendingMsgs = new LinkedList<String>();
		this.user = user;
	}

	/**
	 * Called when closing connection.
	 */
	public void close() {
		try {
			if (objOut != null)
				objOut.close();
			if (objIn != null)
				objIn.close();
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block

		}
	}

	/**
	 * Push a pending message onto the stack. The reason behind this is we want to
	 * save every message sent by this client.
	 * 
	 * @param msg
	 */
	public void addMsg(String msg) {
		hasMsgs = true;
		this.msg = msg;
	}

	/**
	 * Prompts this thread to send user object to the server.
	 * 
	 * @param user
	 */
	public void sendUserObject(User user) {
		synchronized (user) {
			this.user = user;
			try {
				objOut.writeObject(user);
				objOut.flush();
			} catch (IOException e) {

			}
		}
	}

	public Response recvResponse() {
		Response res = null;
		// Keep reading Response from the server until it's not null.
		while (res == null) {
			try {
				res = (Response) objIn.readObject();
				return res;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void run() {
		try {
			System.out.println(Thread.currentThread().getName() + " is a ChatServerThread and is running.");
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objIn = new ObjectInputStream(socket.getInputStream());
			Response oldRes = null;
			while (!socket.isClosed()) {
				if (socket.getInputStream().available() > 0) {
					Response res = recvResponse();
					// If this client's user object is already signed in,
					// then response from the server is a broadcasted message.
					// Allow user to send/receive message from the server.
					if (user.getUserCode() == User.UserCode.signedInUser && view != null) {
						// sendUserObject(user);

						view.appendMessage(res.getMessage());

						// URL/music pulling feature.
						if (res.getData() != null && !res.getData().isEmpty()) {
							String data = res.getData();
							String msg = res.getMessage();
							boolean isUrl = res.isUrl();
							Platform.runLater(new Runnable() {
								public void run() {
									if (!isUrl) { 
										if (data.equals("pause")) 
											view.pauseMusic();
										else if (data.equals("resume"))
											view.resumeMusic();
										else {
											view.playMusic(data);
										}
									} else { 
										view.openURL(data);
									}
								}
							});
						}
						// This means this thread is sending an User object to the server
						// for verification with the database. If the credential match,
						// change the user's status of signing in to signed in and update the GUI.
					} else if (user.getUserCode() == User.UserCode.signingInUser && !res.equals(oldRes)) {
						if (res.isOK()) {
							this.user.setUserCode(User.UserCode.signedInUser);
							Platform.runLater(new Runnable() {

								public void run() {
									view = signInView.successSignedIn();
								}
							});
						} else {
							// Shoot a message dialog to the signInView saying that
							// the server doesn't recognize the user's credential.

							Platform.runLater(new Runnable() {
								public void run() {
									signInView.signInFail();
								}
							});
						}
						oldRes = res;
					} else if (this.user.getUserCode() == User.UserCode.signingUpUser && !res.equals(oldRes)) {
						// This means this thread is trying to create a new User within the server
						// database.
						// If res.isOk() returns false, it means the server can't find the user. If so,
						// proceed with updating
						// the GUI. Else make the GUI pop up an error message.
						if (!res.isOK()) {
							// If this user's not in the database.
							// this.user.setUserCode(User.UserCode.signingInUser);
							Platform.runLater(new Runnable() {

								public void run() {
									signUpView = signInView.getSignUpView();
									signUpView.signUpSuccess();
									signUpView.close();
								}
							});
						} else {
							// The user's already in the database. Notify the sign up view.
							Platform.runLater(new Runnable() {
								public void run() {
									signUpView = signInView.getSignUpView();
									signUpView.signUpFail();
								}
							});
						}
						oldRes = res;
					}
				}

				if (hasMsgs) {
					String nextMsg = msg;
					hasMsgs = !hasMsgs;

					String wholeMsg = "[" + user.getHandle() + "] " + nextMsg;
					// out.writeUTF(wholeMsg);
					// out.flush();

					objOut.writeObject(new Response(wholeMsg, null));
					objOut.flush();
				}
			}
			this.close();
		} catch (IOException e) {
			System.out.println("You have been disconnected from server.");
			this.close();
			
			Platform.runLater(new Runnable() {
				public void run() {
					new Alert(Alert.AlertType.WARNING, "Disconnected from server.").showAndWait();
				}
			});
			
		}
	}
}