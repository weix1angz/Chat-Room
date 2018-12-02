package server;
/**
 * ChatClientThread.java
 * 
 * Serves 1 client thread at a time. Responsible for sending the current's serving
 * client's message and the current server thread's message to every other client.
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;

import util.Bot;
import util.User;

public class ChatClientThread extends ChatServer implements Runnable {

	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	String outputLine = null;
	String inputLine = null;
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	private AbstractMap<String, Bot> botsMap;
	private User userObj;
	private PrintStream logStream;

	public ChatClientThread(Socket socket) {
		botsMap = new HashMap<String, Bot>();
		this.socket = socket;
		for (Bot b : bots) {
			botsMap.put(b.getBotCharacterId() + "", b);
		}
		userObj = null;
		logStream = System.out;
	}

	@Override
	public void run() {
		try {
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			objIn = new ObjectInputStream(socket.getInputStream());
			objOut = new ObjectOutputStream(socket.getOutputStream());
			
			// Read user's info.
			userObj = (User) objIn.readObject();
			
			// Broadcast to all clients that this user's client just log onto the server.
			broadcastToClients(new Response(userObj.getHandle() + " joined the channel.", null));
			logStream.println(userObj.getHandle() + " joined the channel.");

			Response botRes = null;
			while (!socket.isClosed()) {
				// inputLine = in.readUTF();
				Response clientRequestRes = ((Response) objIn.readObject());
				if (clientRequestRes != null) {
					inputLine = clientRequestRes.getMessage();
					
					/**
					 * inputLine normally has the form of:
					 * 
					 * [user's handle] [message]
					 */
					if (inputLine != null) {
						logStream.println(inputLine);
						String[] input_arr = inputLine.split(" ");
						
						// text is the actually message.
						String text = "";
						
						for (int i = 1; i < input_arr.length; i++) {
							if (i < input_arr.length - 1)
								text += input_arr[i] + " ";
							else
								text += input_arr[i];
						}

						// Normal message
						outputLine = "[" + userObj.getHandle() + "]" + " " + text;
						botRes = new Response(outputLine, null);

						// Message direct to the bot.
						if (botsMap.containsKey(text.charAt(0) + "")) {
							Bot b = botsMap.get(text.charAt(0) + "");
							botRes = b.getResponses(text, userObj);
							outputLine += "\n" + b.getBotSignature() + "@" + userObj.getHandle() + " " + botRes.getMessage();
							botRes.setMessage(outputLine);
						}
					}

					System.out.println(botRes);
					if (botRes != null) {
						// broadcastToClients(outputLine);
						broadcastToClients(botRes);
					}
					botRes = null;
					outputLine = null;
				}
			}

			logStream.println(userObj.getHandle() + " left the channel.");
			broadcastToClients(new Response(userObj.getHandle() + " has left the channel.", null));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * A getter method to get this instance's DataOutputStream object.
	 * 
	 * @return a DataOutputStream object.
	 */
	public DataOutputStream getDOS() {
		return out;
	}

	public ObjectOutputStream getOOS() {
		return objOut;
	}

	/**
	 * Sends outputLine to every client in the clients list.
	 * 
	 * @param outputLine A string representing message.
	 * @throws IOException
	 */
	public void broadcastToClients(String outputLine) throws IOException {
		if (outputLine != null) {
			for (ChatClientThread clientThread : clients) {
				if (clientThread.getDOS() != null) {
					clientThread.getDOS().writeUTF(outputLine + "\r\n");
					clientThread.getDOS().flush();
				}
			}
		}
	}

	/**
	 * Sends output response to every client in the clients list.
	 * 
	 * @param output A Response object.
	 * @throws IOException
	 */
	public void broadcastToClients(Response output) throws IOException {
		if (output != null) {
			for (ChatClientThread clientThread : clients) {
				if (clientThread.getDOS() != null) {
					clientThread.getOOS().writeObject(output);
					clientThread.getOOS().flush();
				}
			}
		}
	}

}
