package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import util.Bot;
import util.MinhsBot;

public class ChatServer {

	private static ServerSocket serverSocket;
	protected static List<ChatClientThread> clients;
	protected static List<Bot> bots;

	public static void main(String[] args) {
		// int portNumber = Integer.parseInt(args[0]);
		int portNumber = 4000;
		serverSocket = null;
		bots = new ArrayList<>();
		bots.add(new MinhsBot('!'));
		Socket clientSocket = null;
		try {
			serverSocket = new ServerSocket(portNumber);
			acceptClients(bots);
		} catch (Exception e) {

		} finally {
			try {
				if (serverSocket != null)
					serverSocket.close();
				if (clientSocket != null)
					clientSocket.close();
			} catch (Exception e) {

			}
		}
	}

	public static void acceptClients(List<Bot> botsList) {
		clients = new ArrayList<ChatClientThread>();
		
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				ChatClientThread client = new ChatClientThread(socket);
				Thread clientThread = new Thread(client);
				clientThread.start();
				clients.add(client);
			} catch (IOException e) {
				System.err.println(e.getStackTrace());
			}
		}
	}
}
