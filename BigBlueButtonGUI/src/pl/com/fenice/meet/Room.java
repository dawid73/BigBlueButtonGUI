package pl.com.fenice.meet;

public class Room {

	String meetingID;
	String meetingName;
	String attendeePW;
	String moderatorPW;
	String autor;
	
	
	public Room(String meetingID, String meetingName, String moderatorPW, String attendeePW, String autor) {
		super();
		this.meetingID = meetingID;
		this.meetingName = meetingName;
		this.attendeePW = attendeePW;
		this.moderatorPW = moderatorPW;
		this.autor = autor;
	}
	
	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getMeetingID() {
		return meetingID;
	}
	public void setMeetingID(String meetingID) {
		this.meetingID = meetingID;
	}
	public String getMeetingName() {
		return meetingName;
	}
	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}
	public String getAttendeePW() {
		return attendeePW;
	}
	public void setAttendeePW(String attendeePW) {
		this.attendeePW = attendeePW;
	}
	public String getModeratorPW() {
		return moderatorPW;
	}
	public void setModeratorPW(String moderatorPW) {
		this.moderatorPW = moderatorPW;
	}
	



}
