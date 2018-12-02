package server;
import java.io.Serializable;
import java.net.Socket;

public class ConnectionInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4469213468568829508L;
	private String ipAddress;
	
	public ConnectionInfo(Socket sock) {
		ipAddress = sock.getInetAddress().toString();
	}
	
	@Override
	public String toString() {
		return ipAddress;
	}
}
