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
/**
 * This is the ChatServer
 * @author Minh Bui
 *
 */
public class ChatServer {

	private static ServerSocket serverSocket;
	protected static List<ChatClientThread> clients;
	protected static List<Bot> bots;
	protected static HashMap<String , String> asciiMap;
	/**
	 * This main method to load the chat Server
	 * @param args
	 */
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
	/**
	 * constructAsciiMap for moji
	 */
	private static void constructAsciiMap() {
		asciiMap = new HashMap<>();
		asciiMap.put("/acid" , "	鈯�(鈼夆�库棄)銇�");
		asciiMap.put("/afraid" , "(銌� _ 銌�)");
		asciiMap.put("/angel" , "鈽�(鈱掆柦鈱�)鈽�");
		asciiMap.put("/angry" , "鈥_麓鈥�");
		asciiMap.put("/happy" , "侃( 喙戔暪 陣粹暪)鄱");
		asciiMap.put("/mad" , "t(嗖犵泭嗖爐)");
		asciiMap.put("/sad" , "蔚(麓锃侊傅锃乣)蟹");
		asciiMap.put("/shrug" , "炉\\_(銉�)_/炉");
		asciiMap.put("/shy" , "=^_^=");
		asciiMap.put("/strong" , "釙�(鈬�鈥糕喖鈥�)釙�");
		asciiMap.put("/zombie" , "[卢潞-掳]卢");
		asciiMap.put("/yuno" , "(鈥⑻�岽椻�⑻�)賵 虘虘");
		asciiMap.put("/no" , "鈫抇鈫�");
	}
	/**
	 * This function is using for accpeting clients
	 */
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
