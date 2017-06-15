package regid.network;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlRootElement
public class RegId {
	@XmlAttribute(required=true)
	private String regId;
	@XmlAttribute(required=true)
	public Date date;
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
	@Override
	public String toString() {
		return "RegId [regId=" + regId + ", date=" + date + "]";
	}
	
}
