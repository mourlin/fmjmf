package regid.network;

import java.util.Date;

public class RegId {
	private String regId;
	private Date date;
	
	public RegId() {
		super();
	}
	public RegId(String regId, Date date) {
		super();
		this.regId = regId;
		this.date = date;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
