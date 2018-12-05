package server;



/**
 * This class encapsulate the type of response/request from the chat server. 
 * 
 * @author Minh Bui
 */

import java.io.Serializable;
/**
 * This program is about the response we can transfer it and show on the client 
 * @author Minh Bui
 *
 */
public class Response implements Serializable {
	
	private static final long serialVersionUID = -7022035661052276058L;
	String message;
	String data;
	boolean greenLight;
	boolean isUrl;
	
	/**
	 * Determine if there is a url we need to use 
	 * @return isUrl which is a boolean to determine if there is url
	 */
	public boolean isUrl() {
		return isUrl;
	}
	
	/**
	 * By using the parameter to set the url
	 * @param isUrl which is the parameter we need to set
	 */
	public void setUrl(boolean isUrl) {
		this.isUrl = isUrl;
	}
	
	/**
	 * The constructor of Response program, setting each field depending the parameters
	 * @param isOk which is a boolean value
	 */
	public Response(boolean isOk) {
		greenLight = isOk;
		message = null;
		data = null;
	}
	/**
	 * The constructor of Response program, setting each field depending the parameters
	 * @param message which is a String value (String message to show on gui)
	 * @param data which is a String value (url)
	 */
	public Response(String message, String data) {
		this.message = message;
		this.data = data;
		isUrl = true;
	}
	
	/**
	 * The constructor of Response program,setting each field depending the parameters 
	 * @param data which is the String value (url)
	 * @param message which is the String value (response to show on gui)
	 * @param gl which is the boolean value (online or offline)
	 */
	public Response(String data, String message, boolean gl) {
		this.data = data;
		this.message = message;
		this.greenLight = gl;
		isUrl = true;
	}

	/**
	 * return greenlight (online or offline)
	 * @return greenLight which is a boolean value
	 */
	public boolean isOK() { return greenLight; }
	
	/**
	 * set the greenlight by using the parameter
	 * @param value which is a boolean value
	 */
	public void setVerdict(boolean value) { this.greenLight = value; }

	/**
	 * return the message
	 * @return message which is a String value
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * set the Message
	 * @param message which is the String value
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * get the data(url)
	 * @return data which is a String value
	 */
	public String getData() {
		return data;
	}

	/**
	 * set the Data (url) depending upon the given parameters
	 * @param data which is a String value
	 */
	public void setData(String data) {
		this.data = data;
	}
	
}
