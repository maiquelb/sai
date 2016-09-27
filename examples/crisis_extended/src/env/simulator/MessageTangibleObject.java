/**
  A tangible to simulate exchanging of messages
**/

package simulator;

import javax.swing.ImageIcon;

public class MessageTangibleObject extends TangibleObject {	
	private String from;
	private String to;
	private String message;
	
	public MessageTangibleObject(){
		super("message_tangible_object", new ImageIcon(TangibleTableArt.class.getResource("/simulator/images/mail.png")));
	}


	public String getFrom(){
		return this.from;
	}
	
	public void setFrom(String from){
		this.from = from;
	}
	
	
	public String getTo(){
		return this.to;
	}
	
	public void setTo(String to){
		this.to = to;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
}
