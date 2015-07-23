package bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import base.BaseModelObject;

@Entity
public class SiteReading extends BaseModelObject {

	private String site_id;
	@Type(type = "text")
	private String address;
	private String host;
	private Date startTime;
	private Date endTime;

	public String getSite_id() {
		return site_id;
	}

	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getHost() {
		return this.address.split("/")[2];
	}
	public void extractHost(){
		this.host = this.address.split("/")[2];
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

}
