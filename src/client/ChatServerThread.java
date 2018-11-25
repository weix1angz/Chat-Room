package client;

/**
 * ChatServerThread.java
 * Handles I/O from client to server.
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

import util.User;

public class ChatServerThread implements Runnable {

	private Socket socket;
	private User user;
	private DataInputStream in;
	private DataOutputStream out;
	private final LinkedList<String> pendingMsgs;
	private boolean hasMsgs = false;
	private String inputLine;
	private ObjectOutputStream objOut;
	
	private ChatBotView view;

	public ChatServerThread(ChatBotView view, Socket socket, User user) {
		this.view = view;
		this.socket = socket;
		pendingMsgs = new LinkedList<String>();
		this.user = user;
	}

	/**
	 * Push a pending message onto the stack. The reason behind this is
	 * we want to save every message sent by this client.
	 * 
	 * @param msg
	 */
	public void addMsg(String msg) {
		synchronized (pendingMsgs) {
			hasMsgs = true;
			pendingMsgs.push(msg);
			inputLine = null;
		}
	}

	@Override
	public void run() {
		try {
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objOut.writeObject(user);
			while (!socket.isClosed()) {
				if (socket.getInputStream().available() > 0) {
					inputLine = in.readUTF();
					System.out.println(inputLine);
					view.appendMessage(inputLine);
				}

				if (hasMsgs) {
					String nextMsg = "";
					synchronized (pendingMsgs) {
						nextMsg = pendingMsgs.pop();
						hasMsgs = !pendingMsgs.isEmpty();
					}

					out.writeUTF("[" + user.getHandle() + "] " + nextMsg);
					out.flush();
				}

			}

		} catch (IOException e) {

		}
	}
}