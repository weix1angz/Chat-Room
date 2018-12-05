package server;

import java.io.Serializable;
import java.net.Socket;
import java.util.Date;
/**
 * The User program to store the user's information depending upon the sign up
 * @author Minh Bui
 *
 */
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
	/**
	 * Constructor of User
	 * @param userName which is the username of user
	 * @param password which is the password of user
	 * @param firstName which is the firstname of user
	 * @param lastName which is the lastname of user
	 * @param age which is the String age
	 * @param major which is the String major
	 */
	public User(String userName, String password, String firstName, String lastName, String age, String major) {
		this.handle = userName;
		this.firstName = firstName;
		this.password = password;
		this.lastName = lastName;
		this.age = age;
		this.major = major;
	}
	/**
	 * get the age
	 * @return age which is the string value
	 */
	public String getAge() {
		return age;
	}
	/**
	 * set the age
	 * @param age which is the string value
	 */
	public void setAge(String age) {
		this.age = age;
	}
	
	/**
	 * get the firstname string
	 * @return firstName which is String value
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * set the firstName depending upon the parameters
	 * @param firstName which is the string value
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Set the major depending upon the parameters
	 * @param major which is a string value
	 */
	public void setMajor(String major) {
		this.major = major;
	}
	/**
	 * get the user's major
	 * @return major which is the major of user
	 */
	public String getMajor() {
		return major;
	}
	/**
	 * get the user's lastName
	 * @return lastName which is the String value
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * set the last name by getting the user information
	 * @param lastName which is String value
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * Get the password of user 
	 * @return password which is the string value
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * set the password of user
	 * @param password which is the String value
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * get the usercode
	 * @return userCode
	 */
	public UserCode getUserCode() {
		return userCode;
	}
	/**
	 * set the usercode
	 * @param userCode which is the userCode object
	 */
	public void setUserCode(UserCode userCode) {
		this.userCode = userCode;
	}
	/**
	 * get the boolean value of isSystemCommand
	 * @return isSystemCommand which is the boolean value to determine if it's a system command
	 */
	public boolean isSystemCommand() {
		return isSystemCommand;
	}
	/**
	 * set the Systemcommand by using the parameter
	 * @param isSystemCommand which is the boolean value
	 */
	public void setSystemCommand(boolean isSystemCommand) {
		this.isSystemCommand = isSystemCommand;
	}
	/**
	 * constuctor for user
	 * @param handle which is the String
	 * @param sock which is the Socket
	 */
	public User(String handle, Socket sock) {
		this.setConnectionInfo(sock);
		this.handle = handle;
	}
	/**
	 * get the handle - a String value
	 * @return handle - a String value
	 */
	public String getHandle() {
		return handle;
	}
	/**
	 * set the handle
	 * @param handle which is the string value
	 */
	public void setHandle(String handle) {
		this.handle = handle;
	}
	/**
	 * set the ConnectionInfo by using the connectionSocket
	 * @param connectionSocket which is the Socket value
	 */
	public void setConnectionInfo(Socket connectionSocket) {
		this.connectionSocket = new ConnectionInfo(connectionSocket);
	}
	/**
	 * get the connectionInfo
	 * @return connectionSocket string
	 */
	public String getConnectionInfo() {
		return this.connectionSocket.toString();
	}


}
