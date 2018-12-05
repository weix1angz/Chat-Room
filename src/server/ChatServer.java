package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import server.Bots.Bot;
import server.Bots.MakeupBot;
import server.Bots.MinhsBot;
import server.Bots.NBAbot;
import server.Bots.WeixiangBot;

public class ChatServer {

	private static ServerSocket serverSocket;
	protected static List<ChatClientThread> clients;
	protected static List<Bot> bots;
	protected static HashMap<String , String> asciiMap;
	
	public static void main(String[] args) {
		constructAsciiMap();
		int portNumber = 4000;
		serverSocket = null;
		bots = new ArrayList<>();
		bots.add(new MinhsBot('!'));
		bots.add(new WeixiangBot('%'));
		bots.add(new MakeupBot('*'));
		bots.add(new NBAbot('#'));
		try {
			System.out.println(new java.io.File( "." ).getCanonicalPath());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		try {
			serverSocket = new ServerSocket(portNumber);
			acceptClients();
		} catch (Exception e) {

		} finally {
			try {
				if (serverSocket != null)
					serverSocket.close();
			} catch (Exception e) {

			}
		}
	}
	
	private static void constructAsciiMap() {
		asciiMap = new HashMap<>();
		asciiMap.put("/acid" , "	⊂(◉‿◉)つ");
		asciiMap.put("/afraid" , "(ㆆ _ ㆆ)");
		asciiMap.put("/angel" , "☜(⌒▽⌒)☞");
		asciiMap.put("/angry" , "•`_´•");
		asciiMap.put("/happy" , "٩( ๑╹ ꇴ╹)۶");
		asciiMap.put("/mad" , "t(ಠ益ಠt)");
		asciiMap.put("/sad" , "ε(´סּ︵סּ`)з");
		asciiMap.put("/shrug" , "¯\\_(ツ)_/¯");
		asciiMap.put("/shy" , "=^_^=");
		asciiMap.put("/strong" , "ᕙ(⇀‸↼‶)ᕗ");
		asciiMap.put("/zombie" , "[¬º-°]¬");
		asciiMap.put("/yuno" , "(•̀ᴗ•́)و ̑̑");
		asciiMap.put("/no" , "→_←");
	}

	public static void acceptClients() {
		clients = new ArrayList<ChatClientThread>();
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				ChatClientThread client = new ChatClientThread(socket);
				Thread clientThread = new Thread(client);
				clientThread.start();
				clients.add(client);
				System.out.println(socket.getInetAddress() + " connected.");
			} catch (IOException e) {
				System.err.println(e.getStackTrace());
			}
		}
	}
}
