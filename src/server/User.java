package server;

import java.io.Serializable;
import java.net.Socket;
import java.util.Date;

public class User implements Serializable {

	/**
	 * Real info field.
	 */
	private String firstName;
	private String lastName;
	private String handle;
	private String password;
	private String major;
	private String age;



	/**
	 * Meta data
	 */
	public enum UserCode {
		signingInUser, signedInUser, signingUpUser
	};

	private boolean isSystemCommand;
	private ConnectionInfo connectionSocket;
	private UserCode userCode;
	private static final long serialVersionUID = -3846948113481859015L;
	

	/**
	 * Constructor
	 */
	public User() {

	}

	public User(String userName, String password, String firstName, String lastName, String age, String major) {
		this.handle = userName;
		this.firstName = firstName;
		this.password = password;
		this.lastName = lastName;
		this.age = age;
		this.major = major;
	}
	
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getMajor() {
		return major;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserCode getUserCode() {
		return userCode;
	}

	public void setUserCode(UserCode userCode) {
		this.userCode = userCode;
	}

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

	public void setConnectionInfo(Socket connectionSocket) {
		this.connectionSocket = new ConnectionInfo(connectionSocket);
	}

	public String getConnectionInfo() {
		return this.connectionSocket.toString();
	}


}
