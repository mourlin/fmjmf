package regid.network;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlRootElement
public class RegId {
	@XmlAttribute
	private String regId;
	public RegId() {
		super();
	}
	public RegId(String regId) {
		super();
		this.regId = regId;
	}

	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	
}
