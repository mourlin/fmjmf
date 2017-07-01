package regid.persistance;

public class RegId {
	private Long id;
	private String regId;
	
	public RegId(String regId) {
		super();
		this.regId = regId;
		// FIXME the id is unknown, id has to be set.
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
	
}
