package server;
/**
 * ChatClientThread.java
 * 
 * Serves 1 client thread at a time. Responsible for sending the current's serving
 * client's message and the current server thread's message to every other client.
 */

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javafx.scene.control.Alert;
import server.Bots.Bot;
import server.User;
/**
 * This is the ChatClientThread
 * @author Minh Bui
 *
 */
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
	/**
	 * This is the constructor
	 * @param socket - Socket to connect
	 */
	public ChatClientThread(Socket socket) {
		botsMap = new HashMap<String, Bot>();
		this.socket = socket;
		for (Bot b : bots) {
			botsMap.put(b.getBotCharacterId() + "", b);
		}
		userObj = null;
		logStream = System.out;
	}
	/**
	 * close the Inputstream and Outputstream
	 */
	public void close() {
		try {
			if (objIn != null)
				objIn.close();
			if (objOut != null)
				objOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * This is the recvUserObject 
	 */
	public void recvUserObject() {
		try {
			// Read user's info.
			userObj = (User) objIn.readObject();
		} catch (Exception e) {

		}

	}
	/**
	 * run method
	 */
	@Override
	public void run() {
		try {
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			objIn = new ObjectInputStream(socket.getInputStream());
			objOut = new ObjectOutputStream(socket.getOutputStream());

			while (!socket.isClosed()) {
				while (userObj == null) {
					recvUserObject();
				}

				if (userObj.getUserCode() == User.UserCode.signedInUser) {
					Response botRes = null;
					// inputLine = in.readUTF();
					Response clientRequestRes = null;
					try {
						Object dataObj = objIn.readObject();
						if (dataObj instanceof Response)
							clientRequestRes = (Response) dataObj;
					} catch (EOFException e) {
						break;
					} catch (ClassCastException e1) {
						continue;
					} catch (ClassNotFoundException e) {
						continue;
					}

					if (clientRequestRes != null && clientRequestRes.getMessage() != null) {
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
								botRes = b.getResponses(text, userObj, clients);

								outputLine += "\n" + b.getBotSignature() + "@" + userObj.getHandle() + " "
										+ botRes.getMessage();
								botRes.setMessage(outputLine);
							}else {
								for(int i = 0; i < input_arr.length; i++) {
									if(asciiMap.containsKey(input_arr[i])) 
										input_arr[i] = asciiMap.get(input_arr[i]);
								}
								text = "";								
								for (int i = 1; i < input_arr.length; i++) {
									if (i < input_arr.length - 1)
										text += input_arr[i] + " ";
									else
										text += input_arr[i];
								}
								// Normal message
								outputLine = "[" + userObj.getHandle() + "]" + " " + text;
							}
							botRes.setMessage(outputLine);
						}

						System.out.println(botRes);
						if (botRes != null) {
							// broadcastToClients(outputLine);
							broadcastToClients(botRes);
						}
						botRes = null;
						outputLine = null;
					}
				} else if (userObj.getUserCode() == User.UserCode.signingInUser) {
					// Received User object is attempting to sign in.
					// Verify the User object within the server's database and send back a response.
					boolean userIsInDB = checkUserName(userObj.getHandle(), userObj.getPassword());
					if (userIsInDB) {
						this.userObj.setUserCode(User.UserCode.signedInUser);
						this.userObj.setConnectionInfo(socket);
						objOut.writeObject(new Response(userIsInDB));
						// Broadcast to all clients that this user's client just log onto the server.
						broadcastToClients(new Response(userObj.getHandle() + " joined the channel.", null));
						logStream.println(userObj.getHandle() + " joined the channel.");
					} else {
						objOut.writeObject(new Response(userIsInDB));
						userObj = null;
					}

				} else if (userObj.getUserCode() == User.UserCode.signingUpUser) {
					// Received User object is attempting to sign up as a new user.
					// Once the database is updated with the new user or if something goes wrong,
					// send back a response.
					boolean userIsInDB = checkUserName(userObj.getHandle());
					if (!userIsInDB) {
						createUser(userObj.getHandle(), userObj.getPassword(), userObj.getFirstName(),
								userObj.getLastName(), userObj.getAge() + "", userObj.getMajor());
					}
					userObj = null;
					objOut.writeObject(new Response(userIsInDB));

				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			logStream.println(userObj.getHandle() + " left the channel.");
			clients.remove(this);
			try {
				broadcastToClients(new Response(userObj.getHandle() + " has left the channel.", null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.close();
		}
	}
	/**
	 * This is the createUser function depending upon the parameters
	 * @param userName which is the String value
	 * @param password which is the String value
	 * @param firstName which is the String value
	 * @param lastName which is the String value
	 * @param age which is the String value
	 * @param major which is the String value
	 * @return boolean 
	 */
	private boolean createUser(String userName, String password, String firstName, String lastName, String age,
			String major) {
		if (checkUserName(userName))
			return false;
		try {
			File file = new File("userInfo.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			String userInfo = userName + " " + password + " " + firstName + " " + lastName + " " + age + " " + major
					+ "\n";
			writer.append(userInfo);
			writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return true;
	}
	/**
	 * checkUserName and return boolean
	 * @param userName which is a String value
	 * @return boolean
	 */
	private boolean checkUserName(String userName) {
		Scanner sc = null;
		try {
			File file = new File(new java.io.File( "." ).getCanonicalPath() + File.separator + "userInfo.txt");
			file.createNewFile();
			sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] info = line.split(" ");
				if (userName.equals(info[0])) {
					return true;
				}
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			if (sc != null)
				sc.close();
		}

		return false;
	}
	/**
	 * checkUserName and return value
	 * @param userName which is a String value
	 * @param password which is a String value
	 * @return boolean
	 */
	private boolean checkUserName(String userName, String password) {
		Scanner sc = null;
		try {
			File file = new File(new java.io.File( "." ).getCanonicalPath() + File.separator + "userInfo.txt");
			file.createNewFile();
			sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] info = line.split(" ");
				System.out.println(info);
				if (userName.equals(info[0]) && password.equals(info[1]))
					return true;
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			if (sc != null)
				sc.close();
		}
		return false;
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

	public User getUser() {
		return this.userObj;
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
					// Only send data from Response object to the client that request it.
					if (output.getData() != null && !output.getData().isEmpty()) {
						if (clientThread.getUser().equals(this.userObj)) {
							clientThread.getOOS().writeObject(output);
						} else
							clientThread.getOOS().writeObject(new Response(output.getMessage(), null));
					} else {
						// Else just relay back the message to other clients.
						clientThread.getOOS().writeObject(new Response(output.getMessage(), null));
					}
					clientThread.getOOS().flush();
				}

			}
		}
	}

}
