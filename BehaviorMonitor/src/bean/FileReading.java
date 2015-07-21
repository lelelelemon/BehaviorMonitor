package bean;

import java.util.Date;

import javax.persistence.Entity;

import base.BaseModelObject;

@Entity
public class FileReading extends BaseModelObject {
	private String filereading_id;
	private Date start_time;
	private Date end_time;
	private String path;
	private String name;

	public String getFilereading_id() {
		return filereading_id;
	}

	public void setFilereading_id(String filereading_id) {
		this.filereading_id = filereading_id;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
