package client;

/**
 * ChatServerThread.java
 * Handles I/O from client to server.
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import server.Response;

import util.User;

public class ChatServerThread implements Runnable {

	private Socket socket;
	private User user;
	//private DataInputStream in;
	//private DataOutputStream out;
	private final LinkedList<String> pendingMsgs;
	private boolean hasMsgs = false;
	//private String inputLine;
	
	private ObjectOutputStream objOut;
	private ObjectInputStream objIn;
	
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
			//inputLine = null;
		}
	}

	@Override
	public void run() {
		try {
			/*
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			*/
			objOut = new ObjectOutputStream(socket.getOutputStream());			
			objIn = new ObjectInputStream(socket.getInputStream());
			
			// Send user's info to the server.
			objOut.writeObject(user);
			if (socket.getInputStream().available() > 0) {
				//inputLine = in.readUTF();
				Response res = (Response) objIn.readObject();
				System.out.println(res.getMessage());
				view.appendMessage(res.getMessage());
			}
			
			while (!socket.isClosed()) {
				if (socket.getInputStream().available() > 0) {
					//inputLine = in.readUTF();
					Response res = (Response) objIn.readObject();
					System.out.println(res.getMessage());
					view.appendMessage(res.getMessage());
				}

				if (hasMsgs) {
					String nextMsg = "";
					synchronized (pendingMsgs) {
						nextMsg = pendingMsgs.pop();
						hasMsgs = !pendingMsgs.isEmpty();
					}

					
					String wholeMsg = "[" + user.getHandle() + "] " + nextMsg;
					//out.writeUTF(wholeMsg);
					//out.flush();
					
					objOut.writeObject(new Response(wholeMsg, null));
					objOut.flush();
				}

			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}