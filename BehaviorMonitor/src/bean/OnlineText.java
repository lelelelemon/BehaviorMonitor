package bean;

import java.util.Date;

import javax.persistence.Entity;

import org.hibernate.annotations.Type;

import base.BaseModelObject;

@Entity
public class OnlineText extends BaseModelObject {
	private String onlinetext_id;
	private String title;
	private String author = null;
	@Type(type="text")  
	private String context;
	private Date start_time;
	private Date end_time;

	public String getOnlinetext_id() {
		return onlinetext_id;
	}

	public void setOnlinetext_id(String onlinetext_id) {
		this.onlinetext_id = onlinetext_id;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
