package server;
import java.io.Serializable;
import java.net.Socket;
/**
 * This is the ConnectionInfo
 * @author Minh Bui
 *
 */
public class ConnectionInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4469213468568829508L;
	private String ipAddress;
	
	/**
	 * The constructor (connecting to the server socket)
	 * @param sock which is the Socket value
	 */
	public ConnectionInfo(Socket sock) {
		ipAddress = sock.getInetAddress().toString();
	}
	
	/**
	 * return the ipaddress
	 */
	@Override
	public String toString() {
		return ipAddress;
	}
}
