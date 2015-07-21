package bean;

import java.util.Date;

import javax.persistence.Entity;

import org.hibernate.annotations.Type;

import base.BaseModelObject;

@Entity
public class OnlineCodeSnippet extends BaseModelObject {
	private String onlinecodesnippet_id_id;

	@Type(type="text")  
	private String code;
	private Date start_time;
	private Date end_time;

	public String getOnlinecodesnippet_id_id() {
		return onlinecodesnippet_id_id;
	}

	public void setOnlinecodesnippet_id_id(String onlinecodesnippet_id_id) {
		this.onlinecodesnippet_id_id = onlinecodesnippet_id_id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

}
