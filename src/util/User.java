package util;
import java.io.Serializable;
import java.net.Socket;
import java.util.Date;
/**
 * 
 * @author Minh Bui
 *
 */
public class User implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -3846948113481859015L;
	private String name;
	private String handle;
	private String password;
	private boolean isSystemCommand;
	private ConnectionInfo connectionSocket;
	
	public boolean isSystemCommand() {
		return isSystemCommand;
	}
	public void setSystemCommand(boolean isSystemCommand) {
		this.isSystemCommand = isSystemCommand;
	}
	public User(String handle, Socket sock) {
		this.setConnectionInfo(sock);
		this.handle = handle;
	}
	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	private Date birthday;
	
	public User(String name, String birthday) {
		this.name = name;
		birthday = null;
	}

	public void setConnectionInfo(Socket connectionSocket) {
		this.connectionSocket = new ConnectionInfo(connectionSocket);
	}
	
	public String getConnectionInfo() {
		return this.connectionSocket.toString();
	}
}