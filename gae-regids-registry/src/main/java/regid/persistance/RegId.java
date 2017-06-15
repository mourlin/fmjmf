package regid.persistance;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class RegId {
	@Id
	private Long id;
	@Index
	private String regId;
	@Index	
	private Date date;
	
	public RegId(String regId, Date date) {
		super();
		this.regId = regId;
		this.date = date;
	}
	public RegId() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
