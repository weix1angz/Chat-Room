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

import javafx.application.Platform;
import javafx.scene.paint.Color;
import server.Response;

import server.User;

public class ChatServerThread implements Runnable {

	private Socket socket;
	private User user;
	private final LinkedList<String> pendingMsgs;
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
		pendingMsgs = new LinkedList<String>();
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

				Platform.runLater(new Runnable() {
					public void run() {
						signInView.getConnectionStatus().setTextFill(Color.RED);
						signInView.getConnectionStatus().setText("\t\t	Offline");
					}
				});

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Push a pending message onto the stack. The reason behind this is we want to
	 * save every message sent by this client.
	 * 
	 * @param msg
	 */
	public void addMsg(String msg) {
		synchronized (pendingMsgs) {
			hasMsgs = true;
			pendingMsgs.push(msg);
			// inputLine = null;
		}
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
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objIn = new ObjectInputStream(socket.getInputStream());

			while (!socket.isClosed()) {
				if (socket.getInputStream().available() > 0) {
					Response res = recvResponse();
					// If this client's user object is already signed in,
					// then response from the server is a broadcasted message.
					// Allow user to send/receive message from the server.
					if (user.getUserCode() == User.UserCode.signedInUser) {
						sendUserObject(user);
						if (socket.getInputStream().available() > 0) {
							if (res.getMessage() == null) break;
							System.out.println(res.getMessage());
							view.appendMessage(res.getMessage());

							// URL pulling feature.
							if (res.getData() != null && !res.getData().isEmpty()) {
								String data = res.getData();
								Platform.runLater(new Runnable() {
									public void run() {
										view.openURL(data);
									}
								});

							}
						}

						if (hasMsgs) {
							String nextMsg = "";
							synchronized (pendingMsgs) {
								nextMsg = pendingMsgs.pop();
								hasMsgs = !pendingMsgs.isEmpty();
							}

							String wholeMsg = "[" + user.getHandle() + "] " + nextMsg;
							// out.writeUTF(wholeMsg);
							// out.flush();

							objOut.writeObject(new Response(wholeMsg, null));
							objOut.flush();
						}

						// This means this thread is sending an User object to the server
						// for verification with the database. If the credential match,
						// change the user's status of signing in to signed in and update the GUI.
					} else if (user.getUserCode() == User.UserCode.signingInUser) {
						if (res.isOK()) {
							this.user.setUserCode(User.UserCode.signedInUser);
							Platform.runLater(new Runnable() {
								public void run() {
									signInView.successSignedIn();
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
					} else {
						// This means this thread is trying to create a new User within the server
						// database.
						// If res.isOk() returns false, it means the server can't find the user. If so,
						// proceed with updating
						// the GUI. Else make the GUI pop up an error message.
						if (!res.isOK()) {
							// If this user's not in the database.
							this.user.setUserCode(User.UserCode.signingInUser);
							Platform.runLater(new Runnable() {
								public void run() {
									signUpView.close();
								}
							});
						} else {
							// The user's already in the database. Notify the sign up view.
							Platform.runLater(new Runnable() {
								public void run() {
									signInView.signInFail();
								}
							});
						}
					}

				}
			}
			this.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}