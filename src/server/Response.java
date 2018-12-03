package server;



/**
 * This class encapsulate the type of response/request from the chat server. 
 * 
 * @author Minh Bui
 */

import java.io.Serializable;

public class Response implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7022035661052276058L;
	String message;
	String data;
	
	public Response(String message, String data) {
		this.message = message;
		this.data = data;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getData() {
		return data;
	}


	public void setData(String data) {
		this.data = data;
	}
	
	
}
