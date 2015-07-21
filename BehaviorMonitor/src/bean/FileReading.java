package bean;

import java.util.Date;

import javax.persistence.Entity;

import base.BaseModelObject;

@Entity
public class FileReading extends BaseModelObject {
	private String filereading_id;
	private Date startTime;
	private Date endTime;
	private String path;
	private String name;

	public String getFilereading_id() {
		return filereading_id;
	}

	public void setFilereading_id(String filereading_id) {
		this.filereading_id = filereading_id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
