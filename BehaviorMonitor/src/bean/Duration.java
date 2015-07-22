package bean;

import java.util.Date;

import javax.persistence.Entity;

import base.BaseModelObject;

@Entity
public class Duration extends BaseModelObject {
	private Date start;
	private Date end;

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

}
